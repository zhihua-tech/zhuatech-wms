/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class ShipmentReleaseGovernanceServiceTest {
    private final ShipmentReleaseGovernanceService service = new ShipmentReleaseGovernanceService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void releasesReadyShipment() {
        var result = service.assess(new ShipmentReleaseGovernanceService.Request(
                "SHP-001", true, true, true, true, false, false, false, false, 0, 120));
        assertThat(result.decision()).isEqualTo(ShipmentReleaseGovernanceService.Decision.RELEASE);
        assertThat(result.blockers()).isEmpty();
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void holdsUnsafeControlledShipment() {
        var result = service.assess(new ShipmentReleaseGovernanceService.Request(
                "SHP-002", false, false, false, false, true, false, true, false, 2, 10));
        assertThat(result.decision()).isEqualTo(ShipmentReleaseGovernanceService.Decision.HOLD);
        assertThat(result.blockers()).hasSize(6);
        assertThat(result.actions()).hasSize(2);
    }
}
