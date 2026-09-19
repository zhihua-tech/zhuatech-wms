/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class FefoLotAllocationServiceTest {
    private final FefoLotAllocationService service = new FefoLotAllocationService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void allocatesEarliestExpiringEligibleLotsFirst() {
        var result = service.allocate(request("12", 3, List.of(
                lot("LATE", "10", "0", 60, true, FefoLotAllocationService.LotStatus.AVAILABLE),
                lot("EARLY", "8", "0", 20, true, FefoLotAllocationService.LotStatus.AVAILABLE))));
        assertThat(result.decision()).isEqualTo(FefoLotAllocationService.Decision.ALLOCATED);
        assertThat(result.allocations()).extracting(FefoLotAllocationService.AllocationLine::lotNo)
                .containsExactly("EARLY", "LATE");
        assertThat(result.allocations().get(1).allocatedQuantity()).isEqualByComparingTo("4");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void excludesQuarantineAndInsufficientShelfLife() {
        var result = service.allocate(request("5", 3, List.of(
                lot("SHORT", "10", "0", 5, true, FefoLotAllocationService.LotStatus.AVAILABLE),
                lot("QA", "10", "0", 40, false, FefoLotAllocationService.LotStatus.QUARANTINE))));
        assertThat(result.decision()).isEqualTo(FefoLotAllocationService.Decision.BLOCKED);
        assertThat(result.rejectedLots()).hasSize(2);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void returnsPartialWhenSplitLimitPreventsFulfilment() {
        var result = service.allocate(request("12", 1, List.of(
                lot("L1", "7", "0", 20, true, FefoLotAllocationService.LotStatus.AVAILABLE),
                lot("L2", "7", "0", 30, true, FefoLotAllocationService.LotStatus.AVAILABLE))));
        assertThat(result.decision()).isEqualTo(FefoLotAllocationService.Decision.PARTIAL);
        assertThat(result.shortageQuantity()).isEqualByComparingTo("5");
        assertThat(result.rejectedLots()).anyMatch(item -> item.reason().contains("最大拆分批次数"));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test
    void blocksDuplicateLotIdentity() {
        var duplicate = lot("L1", "7", "0", 20, true, FefoLotAllocationService.LotStatus.AVAILABLE);
        var result = service.allocate(request("5", 2, List.of(duplicate, duplicate)));
        assertThat(result.decision()).isEqualTo(FefoLotAllocationService.Decision.BLOCKED);
        assertThat(result.rejectedLots().getFirst().reason()).isEqualTo("批次编号重复");
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private FefoLotAllocationService.Request request(String quantity, int maxLots,
                                                       List<FefoLotAllocationService.Lot> lots) {
        return new FefoLotAllocationService.Request("ALLOC-1", "OWNER-1", "SKU-1",
                new BigDecimal(quantity), 10, maxLots, lots);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private FefoLotAllocationService.Lot lot(String number, String onHand, String reserved,
                                              int days, boolean released,
                                              FefoLotAllocationService.LotStatus status) {
        return new FefoLotAllocationService.Lot(number, "OWNER-1", new BigDecimal(onHand),
                new BigDecimal(reserved), days, released, status);
    }
}
