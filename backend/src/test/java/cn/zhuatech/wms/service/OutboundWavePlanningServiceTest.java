/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class OutboundWavePlanningServiceTest {
    private final OutboundWavePlanningService service = new OutboundWavePlanningService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void selectsUrgentHighPriorityOrdersWithinCapacity() {
        var result = service.plan(request(20, false, List.of(
                order("O-LOW", 20, 6, 120, false, OutboundWavePlanningService.TemperatureZone.AMBIENT),
                order("O-HIGH", 90, 8, 30, false, OutboundWavePlanningService.TemperatureZone.AMBIENT))));
        assertThat(result.decision()).isEqualTo(OutboundWavePlanningService.Decision.READY);
        assertThat(result.selectedOrders()).extracting(OutboundWavePlanningService.SelectedOrder::orderNo)
                .containsExactly("O-HIGH", "O-LOW");
        assertThat(result.capacityUtilizationPercent()).isEqualByComparingTo("70.0");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void defersTemperatureAndCapacityConflicts() {
        var result = service.plan(request(10, false, List.of(
                order("O-CHILL", 90, 8, 30, false, OutboundWavePlanningService.TemperatureZone.CHILLED),
                order("O-FROZEN", 80, 3, 20, false, OutboundWavePlanningService.TemperatureZone.FROZEN),
                order("O-AMBIENT", 70, 5, 10, false, OutboundWavePlanningService.TemperatureZone.CHILLED))));
        assertThat(result.decision()).isEqualTo(OutboundWavePlanningService.Decision.PARTIAL);
        assertThat(result.selectedOrderCount()).isEqualTo(1);
        assertThat(result.deferredOrders()).extracting(OutboundWavePlanningService.DeferredOrder::reason)
                .anyMatch(reason -> reason.contains("温区"))
                .anyMatch(reason -> reason.contains("容量"));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void blocksDuplicateOrderIdentity() {
        var duplicate = order("O-1", 50, 2, 60, false, OutboundWavePlanningService.TemperatureZone.AMBIENT);
        var result = service.plan(request(20, true, List.of(duplicate, duplicate)));
        assertThat(result.decision()).isEqualTo(OutboundWavePlanningService.Decision.BLOCKED);
        assertThat(result.deferredOrders().getFirst().reason()).contains("编号重复");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private OutboundWavePlanningService.Request request(int capacity, boolean mixTemperature,
                                                         List<OutboundWavePlanningService.Order> orders) {
        return new OutboundWavePlanningService.Request("WAVE-100", "WH-01", capacity, 10,
                mixTemperature, false, orders);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private OutboundWavePlanningService.Order order(String number, int priority, int pieces,
                                                      int cutoff, boolean hazardous,
                                                      OutboundWavePlanningService.TemperatureZone zone) {
        return new OutboundWavePlanningService.Order(number, priority, pieces, "PICK-A", zone,
                cutoff, hazardous, true, true, true);
    }
}
