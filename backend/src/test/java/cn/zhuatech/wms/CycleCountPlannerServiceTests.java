/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms;

import cn.zhuatech.wms.service.CycleCountPlannerService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class CycleCountPlannerServiceTests {
    private final CycleCountPlannerService service = new CycleCountPlannerService();

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void schedulesHighValueFastMoverImmediately() {
        var result = service.plan(new CycleCountPlannerService.Request("SKU-A100", "A", 6, 80, 75, new BigDecimal("280000")));
        assertThat(result.priority()).isEqualTo("URGENT");
        assertThat(result.dueInDays()).isEqualTo(1);
        assertThat(result.actions()).hasSizeGreaterThanOrEqualTo(3);
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void keepsStableCItemOnRoutinePlan() {
        var result = service.plan(new CycleCountPlannerService.Request("SKU-C020", "C", 0, 5, 10, new BigDecimal("8000")));
        assertThat(result.priority()).isEqualTo("ROUTINE");
    }
}
