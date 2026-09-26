# 月台预约与运力治理

`POST /api/enterprise/wms/dock-appointment` 用于入库送货和出库提货车辆预约。

- 根据托盘数和月台每小时处理能力计算必需作业时长。
- 检查并行车道冲突、承运商资质、ASN/提货单和入园预审。
- 对危险品和冷链强制验证专用月台能力。

返回 `SCHEDULE / REVIEW / BLOCKED`、所需时长、阻断项与准备任务。
