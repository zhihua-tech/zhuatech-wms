/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.model;

import jakarta.persistence.*;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Entity @Table(name = "wms_warehouse_zone")
public class WarehouseZone extends BaseEntity {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Type { RECEIVING, STORAGE, PICKING, FROZEN, RETURNS, SHIPPING }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Status { NORMAL, BUSY, MAINTENANCE }
    @Column(nullable = false, unique = true, length = 20) private String zoneCode;
    @Column(nullable = false, length = 60) private String zoneName;
    @Column(nullable = false, length = 60) private String warehouseName;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Type type;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private Status status;
    @Column(nullable = false) private Integer locationCount;
    @Column(nullable = false) private Integer usedLocationCount;
    @Column(nullable = false) private Integer occupancyRate;
    @Column(length = 30) private String environment;

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    protected WarehouseZone() {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public WarehouseZone(String zoneCode, String zoneName, String warehouseName, Type type, Status status,
                         Integer locationCount, Integer usedLocationCount, Integer occupancyRate, String environment) {
        this.zoneCode = zoneCode; this.zoneName = zoneName; this.warehouseName = warehouseName;
        this.type = type; this.status = status; this.locationCount = locationCount;
        this.usedLocationCount = usedLocationCount; this.occupancyRate = occupancyRate; this.environment = environment;
    }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getZoneCode() { return zoneCode; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getZoneName() { return zoneName; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getWarehouseName() { return warehouseName; }
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
    public Integer getLocationCount() { return locationCount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getUsedLocationCount() { return usedLocationCount; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Integer getOccupancyRate() { return occupancyRate; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public String getEnvironment() { return environment; }
}
