/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.controller;

import cn.zhuatech.wms.common.ApiResponse;
import cn.zhuatech.wms.service.DockAppointmentGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@RestController
@RequestMapping("/api/enterprise/wms")
public class DockAppointmentGovernanceController {
    private final DockAppointmentGovernanceService service;

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public DockAppointmentGovernanceController(DockAppointmentGovernanceService service) {
        this.service = service;
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    @PostMapping("/dock-appointment")
    public ApiResponse<DockAppointmentGovernanceService.Assessment> assess(
            @Valid @RequestBody DockAppointmentGovernanceService.Request request) {
        return ApiResponse.ok("月台预约评估完成", service.assess(request));
    }
}
