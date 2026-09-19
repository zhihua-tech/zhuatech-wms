/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 按货主、质量状态和最短保质期执行 FEFO 批次分配。 */
@Service
public class FefoLotAllocationService {
    public Result allocate(Request request) {
        List<RejectedLot> rejected = new ArrayList<>();
        List<Lot> eligible = new ArrayList<>();
        Set<String> lotNumbers = new HashSet<>();
        for (Lot lot : request.lots()) {
            if (!lotNumbers.add(lot.lotNo())) {
                return new Result(request.allocationNo(), Decision.BLOCKED, BigDecimal.ZERO,
                        request.requestedQuantity(), List.of(),
                        List.of(new RejectedLot(lot.lotNo(), "批次编号重复")));
            }
            BigDecimal available = lot.onHandQuantity().subtract(lot.reservedQuantity()).max(BigDecimal.ZERO);
            String reason = null;
            if (lot.reservedQuantity().compareTo(lot.onHandQuantity()) > 0) reason = "预留数量超过账面库存";
            else if (!request.ownerId().equals(lot.ownerId())) reason = "货主不一致";
            else if (!lot.qaReleased()) reason = "质量状态未放行";
            else if (lot.status() != LotStatus.AVAILABLE) reason = "批次不可分配";
            else if (lot.shelfLifeRemainingDays() < request.minimumShelfLifeDays()) reason = "剩余保质期不足";
            else if (available.signum() == 0) reason = "可用库存为零";
            if (reason == null) eligible.add(lot);
            else rejected.add(new RejectedLot(lot.lotNo(), reason));
        }
        eligible.sort(Comparator.comparingInt(Lot::shelfLifeRemainingDays).thenComparing(Lot::lotNo));
        BigDecimal remaining = request.requestedQuantity();
        List<AllocationLine> allocations = new ArrayList<>();
        for (Lot lot : eligible) {
            if (remaining.signum() == 0 || allocations.size() == request.maximumSplitLots()) break;
            BigDecimal available = lot.onHandQuantity().subtract(lot.reservedQuantity()).max(BigDecimal.ZERO);
            BigDecimal allocated = available.min(remaining);
            allocations.add(new AllocationLine(lot.lotNo(), allocated, lot.shelfLifeRemainingDays()));
            remaining = remaining.subtract(allocated);
        }
        BigDecimal allocated = request.requestedQuantity().subtract(remaining);
        Decision decision = remaining.signum() == 0 ? Decision.ALLOCATED
                : allocated.signum() > 0 ? Decision.PARTIAL : Decision.BLOCKED;
        if (remaining.signum() > 0 && allocations.size() == request.maximumSplitLots()) {
            rejected.add(new RejectedLot("ALLOCATION", "达到最大拆分批次数，仍有未满足数量"));
        }
        return new Result(request.allocationNo(), decision, allocated, remaining,
                List.copyOf(allocations), List.copyOf(rejected));
    }

    public record Request(@NotBlank String allocationNo, @NotBlank String ownerId,
                          @NotBlank String sku, @NotNull @DecimalMin("0.0001") BigDecimal requestedQuantity,
                          @Min(0) int minimumShelfLifeDays, @Min(1) int maximumSplitLots,
                          @NotEmpty List<@Valid Lot> lots) {}

    public record Lot(@NotBlank String lotNo, @NotBlank String ownerId,
                      @NotNull @DecimalMin("0.0") BigDecimal onHandQuantity,
                      @NotNull @DecimalMin("0.0") BigDecimal reservedQuantity,
                      @Min(0) int shelfLifeRemainingDays, boolean qaReleased,
                      @NotNull LotStatus status) {}

    public record AllocationLine(String lotNo, BigDecimal allocatedQuantity,
                                 int shelfLifeRemainingDays) {}

    public record RejectedLot(String lotNo, String reason) {}

    public record Result(String allocationNo, Decision decision, BigDecimal allocatedQuantity,
                         BigDecimal shortageQuantity, List<AllocationLine> allocations,
                         List<RejectedLot> rejectedLots) {}

    public enum LotStatus { AVAILABLE, HOLD, QUARANTINE, EXPIRED }
    public enum Decision { ALLOCATED, PARTIAL, BLOCKED }
}
