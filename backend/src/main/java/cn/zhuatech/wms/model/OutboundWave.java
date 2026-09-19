/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name = "wms_outbound_wave")
public class OutboundWave extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Status { CREATED, RELEASED, PICKING, PACKING, CLOSED, EXCEPTION }
    @Column(nullable = false, unique = true, length = 32) private String waveNo;
    @Column(nullable = false, length = 60) private String warehouseName;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status;
    @Column(nullable = false) private Integer orderCount;
    @Column(nullable = false) private Integer skuCount;
    @Column(nullable = false) private Integer pieceCount;
    @Column(nullable = false) private Integer pickedQty;
    @Column(nullable = false, length = 40) private String carrierName;
    @Column(nullable = false) private LocalDateTime cutoffAt;
    @Column(length = 40) private String ownerName;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected OutboundWave() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public OutboundWave(String waveNo, String warehouseName, Status status, Integer orderCount,
                        Integer skuCount, Integer pieceCount, Integer pickedQty, String carrierName,
                        LocalDateTime cutoffAt, String ownerName) {
        this.waveNo = waveNo; this.warehouseName = warehouseName; this.status = status;
        this.orderCount = orderCount; this.skuCount = skuCount; this.pieceCount = pieceCount;
        this.pickedQty = pickedQty; this.carrierName = carrierName; this.cutoffAt = cutoffAt; this.ownerName = ownerName;
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getWaveNo() { return waveNo; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getWarehouseName() { return warehouseName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Status getStatus() { return status; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getOrderCount() { return orderCount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getSkuCount() { return skuCount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getPieceCount() { return pieceCount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getPickedQty() { return pickedQty; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getCarrierName() { return carrierName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public LocalDateTime getCutoffAt() { return cutoffAt; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getOwnerName() { return ownerName; }
}
