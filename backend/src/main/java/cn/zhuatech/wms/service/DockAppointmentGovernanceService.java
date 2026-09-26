/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 月台预约前检查时段容量、承运商、单证、危险品与冷链资质。
 *
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class DockAppointmentGovernanceService {
    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public Assessment assess(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        int requiredMinutes = (int) Math.ceil(request.palletCount() * 60.0 / request.dockHourlyThroughput());
        if (!request.carrierActive()) blockers.add("承运商未启用或资质已过期");
        if (!request.transportDocumentsReady()) blockers.add("送货单、ASN或提货单不完整");
        if (request.overlappingAppointments() >= request.parallelLaneCapacity()) blockers.add("该时段月台并行车道已满");
        if (requiredMinutes > request.reservedMinutes()) blockers.add("预留时长不足以处理计划托盘量");
        if (request.hazardousGoods() && !request.hazardousCertifiedDock()) blockers.add("危险品必须使用具备资质的隔离月台");
        if (request.temperatureControlled() && !request.coldChainDock()) blockers.add("冷链任务必须使用温控月台");
        if (!request.securityPrecleared()) actions.add("车辆、司机与随车人员完成入园预审");
        if (!request.yardSlotReserved()) actions.add("预留等候区车位并下发到场指引");
        if (!request.auditEvidenceAttached()) actions.add("归档预约、单证、资质和现场作业证据");
        Decision decision = !blockers.isEmpty() ? Decision.BLOCKED
                : !actions.isEmpty() ? Decision.REVIEW : Decision.SCHEDULE;
        return new Assessment(request.appointmentNo(), request.dockCode(), request.direction(), decision,
                requiredMinutes, List.copyOf(blockers), List.copyOf(actions));
    }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public record Request(@NotBlank String appointmentNo, @NotBlank String dockCode,
                          @NotNull Direction direction, @Min(1) int palletCount,
                          @Min(1) int dockHourlyThroughput, @Min(15) int reservedMinutes,
                          @Min(0) int overlappingAppointments, @Min(1) int parallelLaneCapacity,
                          boolean carrierActive, boolean transportDocumentsReady,
                          boolean hazardousGoods, boolean hazardousCertifiedDock,
                          boolean temperatureControlled, boolean coldChainDock,
                          boolean securityPrecleared, boolean yardSlotReserved,
                          boolean auditEvidenceAttached) {}

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public record Assessment(String appointmentNo, String dockCode, Direction direction,
                             Decision decision, int requiredMinutes,
                             List<String> blockers, List<String> actions) {}

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public enum Direction { INBOUND, OUTBOUND }

    /** 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public enum Decision { SCHEDULE, REVIEW, BLOCKED }
}
