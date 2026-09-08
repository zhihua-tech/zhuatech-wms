/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;
import jakarta.validation.constraints.*; import org.springframework.stereotype.Service;
import java.math.BigDecimal; import java.util.*;
@Service
public class CycleCountExecutionGovernanceService {
    public Assessment assess(Request r) {
        List<String> blockers=new ArrayList<>(); List<String> actions=new ArrayList<>();
        if (!r.zoneFrozen()) blockers.add("盘点区域尚未冻结库存移动");
        if (!r.blindCountEnabled()) blockers.add("盘点任务未启用盲盘");
        if (!r.lotSerialTraceComplete()) blockers.add("批次或序列号追溯记录不完整");
        if (!r.idempotencyKeyRegistered()) blockers.add("盘点结果缺少幂等键，存在重复过账风险");
        boolean varianceExceeded=r.varianceRate().abs().compareTo(r.toleranceRate())>0;
        if (varianceExceeded && !r.independentRecountCompleted()) blockers.add("超容差差异必须完成独立复盘");
        if (varianceExceeded && !r.varianceReasonConfirmed()) blockers.add("超容差差异未确认根因");
        if (varianceExceeded && !r.adjustmentApproved()) blockers.add("库存调整尚未通过授权审批");
        if (r.counterId().equals(r.approverId())) blockers.add("盘点执行人与差异审批人必须职责分离");
        if (!r.auditEvidenceAttached()) actions.add("补充盘点表、照片和复盘证据");
        if (!r.adjustmentPostingPlanned()) actions.add("配置差异调整过账任务");
        if (!r.zoneUnfreezePlanned()) actions.add("配置过账后区域解冻检查");
        RiskLevel risk=varianceExceeded||r.serialControlled()?RiskLevel.HIGH:RiskLevel.NORMAL;
        Decision decision=!blockers.isEmpty()?Decision.BLOCKED:!actions.isEmpty()?Decision.REVIEW:Decision.POST;
        String route=risk==RiskLevel.HIGH?"仓库主管→库存控制→财务/质量":"仓库主管";
        return new Assessment(r.countNo(),decision,risk,route,List.copyOf(blockers),List.copyOf(actions));
    }
    public record Request(@NotBlank String countNo,@NotBlank String counterId,@NotBlank String approverId,
        @NotNull @DecimalMin("0.00") BigDecimal varianceRate,@NotNull @DecimalMin("0.00") BigDecimal toleranceRate,
        boolean zoneFrozen,boolean blindCountEnabled,boolean lotSerialTraceComplete,boolean idempotencyKeyRegistered,
        boolean independentRecountCompleted,boolean varianceReasonConfirmed,boolean adjustmentApproved,
        boolean auditEvidenceAttached,boolean adjustmentPostingPlanned,boolean zoneUnfreezePlanned,boolean serialControlled){}
    public record Assessment(String countNo,Decision decision,RiskLevel riskLevel,String approvalRoute,List<String> blockers,List<String> actions){}
    public enum Decision{POST,REVIEW,BLOCKED} public enum RiskLevel{NORMAL,HIGH}
}
