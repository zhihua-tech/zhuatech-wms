/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name = "wms_inbound_receipt")
public class InboundReceipt extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Status { APPOINTED, ARRIVED, RECEIVING, QC_HOLD, PUTAWAY, COMPLETED }
    @Column(nullable = false, unique = true, length = 32) private String receiptNo;
    @Column(nullable = false, length = 100) private String supplierName;
    @Column(nullable = false, length = 30) private String sourceType;
    @Column(nullable = false, length = 60) private String warehouseName;
    @Column(nullable = false, length = 30) private String dockCode;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status;
    @Column(nullable = false) private Integer lineCount;
    @Column(nullable = false) private Integer plannedQty;
    @Column(nullable = false) private Integer receivedQty;
    @Column(nullable = false) private LocalDateTime appointmentAt;
    @Column(length = 40) private String operatorName;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected InboundReceipt() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public InboundReceipt(String receiptNo, String supplierName, String sourceType, String warehouseName,
                          String dockCode, Status status, Integer lineCount, Integer plannedQty,
                          Integer receivedQty, LocalDateTime appointmentAt, String operatorName) {
        this.receiptNo = receiptNo; this.supplierName = supplierName; this.sourceType = sourceType;
        this.warehouseName = warehouseName; this.dockCode = dockCode; this.status = status;
        this.lineCount = lineCount; this.plannedQty = plannedQty; this.receivedQty = receivedQty;
        this.appointmentAt = appointmentAt; this.operatorName = operatorName;
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getReceiptNo() { return receiptNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getSupplierName() { return supplierName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getSourceType() { return sourceType; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getWarehouseName() { return warehouseName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getDockCode() { return dockCode; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Status getStatus() { return status; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getLineCount() { return lineCount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getPlannedQty() { return plannedQty; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getReceivedQty() { return receivedQty; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getAppointmentAt() { return appointmentAt; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getOperatorName() { return operatorName; }
}
