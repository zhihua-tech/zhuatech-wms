/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.controller;
import cn.zhuatech.wms.common.ApiResponse; import cn.zhuatech.wms.service.CycleCountExecutionGovernanceService;
import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/enterprise/wms")
public class CycleCountExecutionGovernanceController{
 private final CycleCountExecutionGovernanceService service;
 public CycleCountExecutionGovernanceController(CycleCountExecutionGovernanceService service){this.service=service;}
 @PostMapping("/cycle-count-execution") public ApiResponse<CycleCountExecutionGovernanceService.Assessment> assess(@Valid @RequestBody CycleCountExecutionGovernanceService.Request request){return ApiResponse.ok("循环盘点评估完成",service.assess(request));}
}
