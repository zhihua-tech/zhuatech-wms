/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity
@Table(name = "wms_task", indexes = {
    @Index(name = "idx_wms_task_status", columnList = "status"),
    @Index(name = "idx_wms_task_assignee", columnList = "assignee")
})
public class WarehouseTask extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Type { RECEIVING, PUTAWAY, PICKING, REPLENISHMENT, COUNTING, PACKING }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Status { WAITING, IN_PROGRESS, EXCEPTION, COMPLETED }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Priority { NORMAL, HIGH, URGENT }

    @Column(nullable = false, unique = true, length = 32) private String taskNo;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 24) private Type type;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 12) private Priority priority;
    @Column(nullable = false, length = 60) private String warehouseName;
    @Column(nullable = false, length = 20) private String zoneCode;
    @Column(length = 30) private String sourceLocation;
    @Column(length = 30) private String targetLocation;
    @Column(nullable = false, length = 40) private String skuCode;
    @Column(nullable = false, length = 120) private String productName;
    @Column(length = 40) private String batchNo;
    @Column(nullable = false) private Integer plannedQty;
    @Column(nullable = false) private Integer completedQty;
    @Column(length = 40) private String assignee;
    @Column(nullable = false) private LocalDateTime dueAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    @Column(length = 200) private String remark;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected WarehouseTask() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public WarehouseTask(String taskNo, Type type, Status status, Priority priority, String warehouseName,
                         String zoneCode, String sourceLocation, String targetLocation, String skuCode,
                         String productName, String batchNo, Integer plannedQty, Integer completedQty,
                         String assignee, LocalDateTime dueAt) {
        this.taskNo = taskNo; this.type = type; this.status = status; this.priority = priority;
        this.warehouseName = warehouseName; this.zoneCode = zoneCode; this.sourceLocation = sourceLocation;
        this.targetLocation = targetLocation; this.skuCode = skuCode; this.productName = productName;
        this.batchNo = batchNo; this.plannedQty = plannedQty; this.completedQty = completedQty;
        this.assignee = assignee; this.dueAt = dueAt;
        if (status == Status.IN_PROGRESS) this.startedAt = LocalDateTime.now().minusMinutes(18);
        if (status == Status.COMPLETED) this.completedAt = LocalDateTime.now().minusMinutes(9);
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public void update(Status status, Integer completedQty, String assignee, String remark) {
        if (status == Status.IN_PROGRESS && startedAt == null) startedAt = LocalDateTime.now();
        if (status == Status.COMPLETED) completedAt = LocalDateTime.now();
        this.status = status;
        if (completedQty != null) this.completedQty = Math.min(plannedQty, Math.max(0, completedQty));
        if (assignee != null && !assignee.isBlank()) this.assignee = assignee;
        if (remark != null) this.remark = remark;
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getTaskNo() { return taskNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Type getType() { return type; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Status getStatus() { return status; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Priority getPriority() { return priority; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getWarehouseName() { return warehouseName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getZoneCode() { return zoneCode; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getSourceLocation() { return sourceLocation; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getTargetLocation() { return targetLocation; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getSkuCode() { return skuCode; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getProductName() { return productName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getBatchNo() { return batchNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getPlannedQty() { return plannedQty; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getCompletedQty() { return completedQty; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getAssignee() { return assignee; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getDueAt() { return dueAt; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getStartedAt() { return startedAt; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getCompletedAt() { return completedAt; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getRemark() { return remark; }
}
