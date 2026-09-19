/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms;
import cn.zhuatech.wms.ai.OpenAiCompatibleGateway;
import cn.zhuatech.wms.service.AiLaborForecastService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
class AiLaborForecastServiceTests {
    private final AiLaborForecastService service = new AiLaborForecastService(
        new OpenAiCompatibleGateway("local", "https://api.deepseek.com", "deepseek-chat", ""));
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @Test void findsWorkerShortage() {
        var result = service.forecast(new AiLaborForecastService.Request(300, 2400, 5000, 60,
            new BigDecimal("35"), 1, 480, new BigDecimal("10")));
        assertThat(result.workerGap()).isPositive();
        assertThat(result.status()).isIn("SHORTAGE", "CRITICAL");
    }
}
