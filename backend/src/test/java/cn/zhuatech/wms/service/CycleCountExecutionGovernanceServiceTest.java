/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;
import org.junit.jupiter.api.Test; import java.math.BigDecimal; import static org.assertj.core.api.Assertions.assertThat;
class CycleCountExecutionGovernanceServiceTest{
 private final CycleCountExecutionGovernanceService service=new CycleCountExecutionGovernanceService();
 private CycleCountExecutionGovernanceService.Request request(BigDecimal variance,boolean evidence,boolean posting,boolean unfreeze){return new CycleCountExecutionGovernanceService.Request("CC-1","counter","approver",variance,new BigDecimal("0.02"),true,true,true,true,true,true,true,evidence,posting,unfreeze,false);}
 @Test void postsControlledCount(){assertThat(service.assess(request(new BigDecimal("0.01"),true,true,true)).decision()).isEqualTo(CycleCountExecutionGovernanceService.Decision.POST);}
 @Test void reviewsMissingClosureActions(){var a=service.assess(request(new BigDecimal("0.01"),false,false,false));assertThat(a.decision()).isEqualTo(CycleCountExecutionGovernanceService.Decision.REVIEW);assertThat(a.actions()).hasSize(3);}
 @Test void blocksUnrecountedHighVariance(){var r=new CycleCountExecutionGovernanceService.Request("CC-2","u1","u1",new BigDecimal("0.12"),new BigDecimal("0.02"),false,true,true,true,false,false,false,true,true,true,true);var a=service.assess(r);assertThat(a.decision()).isEqualTo(CycleCountExecutionGovernanceService.Decision.BLOCKED);assertThat(a.riskLevel()).isEqualTo(CycleCountExecutionGovernanceService.RiskLevel.HIGH);assertThat(a.blockers()).hasSizeGreaterThanOrEqualTo(5);}
}
