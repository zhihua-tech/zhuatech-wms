/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.controller;

import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.service.FefoLotAllocationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enterprise/wms")
public class FefoLotAllocationController {
    private final FefoLotAllocationService service;

    public FefoLotAllocationController(FefoLotAllocationService service) {
        this.service = service;
    }

    @PostMapping("/fefo-lot-allocation")
    public ApiResponse<FefoLotAllocationService.Result> allocate(
            @Valid @RequestBody FefoLotAllocationService.Request request) {
        return ApiResponse.ok("FEFO 批次分配完成", service.allocate(request));
    }
}
