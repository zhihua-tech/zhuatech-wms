# 企业级出库波次编排

`POST /api/enterprise/wms/outbound-wave-planning` 面向电商、零售、制造和三方仓储的批量出库场景。

系统按业务优先级、承运商截单时间和订单号形成稳定队列，检查库存预分配、支付/信用放行、地址、温区、危化属性、订单数和件数容量。结果包含入选订单、容量利用率及逐单延后原因，返回 `READY / PARTIAL / BLOCKED`，可直接驱动后续拣选任务生成。

商业授权或定制开发请微信添加微信号 `zhuatech` 或 `zhuatech2` 进行咨询。官网：[知华科技](https://www.zhuatech.cn/)。
