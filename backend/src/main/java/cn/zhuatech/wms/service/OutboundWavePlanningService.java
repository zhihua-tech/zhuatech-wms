/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 根据截单时间、业务优先级、库存、温区、危化属性和仓内容量编排出库波次。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class OutboundWavePlanningService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result plan(Request request) {
        Set<String> identities = new HashSet<>();
        for (Order order : request.orders()) {
            if (!identities.add(order.orderNo())) {
                return new Result(request.waveNo(), Decision.BLOCKED, 0, 0, BigDecimal.ZERO,
                        List.of(), List.of(new DeferredOrder(order.orderNo(), "出库订单编号重复")));
            }
        }
        List<Order> queue = request.orders().stream()
                .sorted(Comparator.comparingInt(Order::priority).reversed()
                        .thenComparingInt(Order::minutesToCarrierCutoff)
                        .thenComparing(Order::orderNo)).toList();
        List<SelectedOrder> selected = new ArrayList<>();
        List<DeferredOrder> deferred = new ArrayList<>();
        TemperatureZone selectedZone = null;
        int plannedPieces = 0;
        for (Order order : queue) {
            String reason = eligibilityReason(request, order, selectedZone, selected.size(), plannedPieces);
            if (reason != null) {
                deferred.add(new DeferredOrder(order.orderNo(), reason));
                continue;
            }
            if (selectedZone == null) selectedZone = order.temperatureZone();
            plannedPieces += order.pieces();
            selected.add(new SelectedOrder(order.orderNo(), order.priority(), order.pieces(),
                    order.zone(), order.temperatureZone(), order.minutesToCarrierCutoff()));
        }
        BigDecimal utilization = BigDecimal.valueOf(plannedPieces).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(request.pieceCapacity()), 1, RoundingMode.HALF_UP);
        Decision decision = selected.isEmpty() ? Decision.BLOCKED
                : deferred.isEmpty() ? Decision.READY : Decision.PARTIAL;
        return new Result(request.waveNo(), decision, selected.size(), plannedPieces, utilization,
                List.copyOf(selected), List.copyOf(deferred));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private String eligibilityReason(Request request, Order order, TemperatureZone selectedZone,
                                     int selectedCount, int plannedPieces) {
        if (!order.inventoryAllocated()) return "库存尚未完成预分配";
        if (!order.paymentReleased()) return "付款或信用审核尚未放行";
        if (!order.addressValidated()) return "收货地址未通过校验";
        if (order.hazardousMaterial() != request.hazardousWave()) return "危化属性与波次类型不一致";
        if (!request.allowMixedTemperature() && selectedZone != null && selectedZone != order.temperatureZone()) {
            return "温区与当前波次不一致";
        }
        if (selectedCount >= request.maximumOrders()) return "达到波次最大订单数";
        if (plannedPieces + order.pieces() > request.pieceCapacity()) return "超过波次件数容量";
        return null;
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(@NotBlank String waveNo, @NotBlank String warehouseCode,
                          @Min(1) int pieceCapacity, @Min(1) int maximumOrders,
                          boolean allowMixedTemperature, boolean hazardousWave,
                          @NotEmpty List<@Valid Order> orders) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Order(@NotBlank String orderNo, @Min(1) @Max(100) int priority,
                        @Min(1) int pieces, @NotBlank String zone,
                        @NotNull TemperatureZone temperatureZone,
                        @Min(0) int minutesToCarrierCutoff, boolean hazardousMaterial,
                        boolean inventoryAllocated, boolean paymentReleased,
                        boolean addressValidated) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record SelectedOrder(String orderNo, int priority, int pieces, String zone,
                                TemperatureZone temperatureZone, int minutesToCarrierCutoff) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record DeferredOrder(String orderNo, String reason) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(String waveNo, Decision decision, int selectedOrderCount,
                         int plannedPieces, BigDecimal capacityUtilizationPercent,
                         List<SelectedOrder> selectedOrders, List<DeferredOrder> deferredOrders) {}

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum TemperatureZone { AMBIENT, CHILLED, FROZEN }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Decision { READY, PARTIAL, BLOCKED }
}
