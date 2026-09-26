/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
class DockAppointmentGovernanceServiceTest {
    private final DockAppointmentGovernanceService service = new DockAppointmentGovernanceService();

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void schedulesQualifiedAppointment() {
        var result = service.assess(request(20, 40, 60, 0, true, true));
        assertThat(result.decision()).isEqualTo(DockAppointmentGovernanceService.Decision.SCHEDULE);
        assertThat(result.requiredMinutes()).isEqualTo(30);
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void blocksOverloadedUnqualifiedAppointment() {
        var result = service.assess(request(80, 40, 60, 2, false, false));
        assertThat(result.decision()).isEqualTo(DockAppointmentGovernanceService.Decision.BLOCKED);
        assertThat(result.blockers()).hasSizeGreaterThanOrEqualTo(4);
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @Test
    void reviewsAppointmentMissingYardPreparation() {
        var result = service.assess(request(20, 40, 60, 0, true, false));
        assertThat(result.decision()).isEqualTo(DockAppointmentGovernanceService.Decision.REVIEW);
        assertThat(result.actions()).hasSize(3);
    }

    private DockAppointmentGovernanceService.Request request(int pallets, int throughput,
                                                               int minutes, int overlaps,
                                                               boolean carrier, boolean prepared) {
        return new DockAppointmentGovernanceService.Request("DOCK-100", "D-01",
                DockAppointmentGovernanceService.Direction.INBOUND, pallets, throughput, minutes,
                overlaps, 2, carrier, carrier, false, true, false, true,
                prepared, prepared, prepared);
    }
}
