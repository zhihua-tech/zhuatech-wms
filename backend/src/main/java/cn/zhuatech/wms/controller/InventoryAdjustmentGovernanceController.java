/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.controller;

import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.service.InventoryAdjustmentGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/enterprise/wms")
public class InventoryAdjustmentGovernanceController {
    private final InventoryAdjustmentGovernanceService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public InventoryAdjustmentGovernanceController(InventoryAdjustmentGovernanceService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/inventory-adjustment")
    public ApiResponse<InventoryAdjustmentGovernanceService.Assessment> assess(
            @Valid @RequestBody InventoryAdjustmentGovernanceService.Request request) {
        return ApiResponse.ok("库存差异调整评估完成", service.assess(request));
    }
}
