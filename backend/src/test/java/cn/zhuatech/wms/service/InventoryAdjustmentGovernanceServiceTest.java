/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class InventoryAdjustmentGovernanceServiceTest {
    private final InventoryAdjustmentGovernanceService service = new InventoryAdjustmentGovernanceService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void postsControlledInventoryAdjustment() {
        var result = service.assess(request(true, true, true));
        assertEquals(InventoryAdjustmentGovernanceService.Decision.POST, result.decision());
        assertTrue(result.blockers().isEmpty());
        assertTrue(result.actions().isEmpty());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void reviewsAdjustmentWithFollowUpActions() {
        var result = service.assess(request(false, false, false));
        assertEquals(InventoryAdjustmentGovernanceService.Decision.REVIEW, result.decision());
        assertEquals(3, result.actions().size());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void blocksUncontrolledInventoryAdjustment() {
        var result = service.assess(new InventoryAdjustmentGovernanceService.Request("ADJ-003", "SKU-100", 30,
                false, false, false, false, false, false, false, false, false, false, false, false,
                true, true, true));
        assertEquals(InventoryAdjustmentGovernanceService.Decision.BLOCKED, result.decision());
        assertEquals(12, result.blockers().size());
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    private InventoryAdjustmentGovernanceService.Request request(boolean rootCause, boolean cyclePolicy, boolean reservations) {
        return new InventoryAdjustmentGovernanceService.Request("ADJ-001", "SKU-100", 30,
                true, true, true, true, true, true, true, true, true, true, true, true,
                rootCause, cyclePolicy, reservations);
    }
}
