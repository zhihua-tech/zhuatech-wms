/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity
@Table(name = "wms_inventory", uniqueConstraints = @UniqueConstraint(name = "uk_wms_inventory", columnNames = {"skuCode", "locationCode", "batchNo"}))
public class InventoryBalance extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum QualityStatus { QUALIFIED, HOLD, DAMAGED }
    @Column(nullable = false, length = 40) private String skuCode;
    @Column(nullable = false, length = 120) private String productName;
    @Column(nullable = false, length = 60) private String warehouseName;
    @Column(nullable = false, length = 20) private String zoneCode;
    @Column(nullable = false, length = 30) private String locationCode;
    @Column(nullable = false, length = 40) private String batchNo;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 16) private QualityStatus qualityStatus;
    @Column(nullable = false) private Integer availableQty;
    @Column(nullable = false) private Integer allocatedQty;
    @Column(nullable = false) private Integer inboundQty;
    @Column(nullable = false, length = 12) private String unit;
    private LocalDate expiryDate;
    private LocalDateTime lastCountAt;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected InventoryBalance() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public InventoryBalance(String skuCode, String productName, String warehouseName, String zoneCode,
                            String locationCode, String batchNo, QualityStatus qualityStatus, Integer availableQty,
                            Integer allocatedQty, Integer inboundQty, String unit, LocalDate expiryDate,
                            LocalDateTime lastCountAt) {
        this.skuCode = skuCode; this.productName = productName; this.warehouseName = warehouseName;
        this.zoneCode = zoneCode; this.locationCode = locationCode; this.batchNo = batchNo;
        this.qualityStatus = qualityStatus; this.availableQty = availableQty; this.allocatedQty = allocatedQty;
        this.inboundQty = inboundQty; this.unit = unit; this.expiryDate = expiryDate; this.lastCountAt = lastCountAt;
    }
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
    public String getWarehouseName() { return warehouseName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getZoneCode() { return zoneCode; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getLocationCode() { return locationCode; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getBatchNo() { return batchNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public QualityStatus getQualityStatus() { return qualityStatus; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getAvailableQty() { return availableQty; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getAllocatedQty() { return allocatedQty; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getInboundQty() { return inboundQty; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getUnit() { return unit; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDate getExpiryDate() { return expiryDate; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getLastCountAt() { return lastCountAt; }
}
