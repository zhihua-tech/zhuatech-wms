/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.controller;
import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.service.AiLaborForecastService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/wms/ai")
public class AiLaborForecastController {
    private final AiLaborForecastService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public AiLaborForecastController(AiLaborForecastService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/labor-forecast")
    public ApiResponse<AiLaborForecastService.Result> forecast(@Valid @RequestBody AiLaborForecastService.Request request) {
        return ApiResponse.ok(service.forecast(request));
    }
}
