/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
package cn.zhuatech.wms.repository;
import cn.zhuatech.wms.model.WarehouseTask;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
public interface WarehouseTaskRepository extends JpaRepository<WarehouseTask, Long> {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<WarehouseTask> findAllByOrderByDueAtAsc();
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    long countByStatus(WarehouseTask.Status status);
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    List<WarehouseTask> findByAssigneeOrderByDueAtAsc(String assignee);
}
