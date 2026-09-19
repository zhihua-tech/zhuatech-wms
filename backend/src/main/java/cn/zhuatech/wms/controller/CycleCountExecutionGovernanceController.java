/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.controller;
import cn.zhuatech.wms.common.ApiResponse; import cn.zhuatech.wms.service.CycleCountExecutionGovernanceService;
import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/wms")
public class CycleCountExecutionGovernanceController{
 private final CycleCountExecutionGovernanceService service;
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public CycleCountExecutionGovernanceController(CycleCountExecutionGovernanceService service){this.service=service;}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @PostMapping("/cycle-count-execution") public ApiResponse<CycleCountExecutionGovernanceService.Assessment> assess(@Valid @RequestBody CycleCountExecutionGovernanceService.Request request){return ApiResponse.ok("循环盘点评估完成",service.assess(request));}
}
