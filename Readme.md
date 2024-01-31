## 功能说明
- 初始化额度账号
- 新增额度子账号
- 子额度账号提额
- 子额度账号降额
- 额度信息查询

**功能操作入口**： `com.quiz.quotamanage.controller.QuotaAccountController`

## 测试用例说明
### 功能测试
- 入口 `com.quiz.quotamanage.service.QuotaScheduledService`
- 实现了所有额度操作的功能测试方法
- 模拟了不同用户并发更新额度的场景

### 单测
- 测试类：`com.quiz.quotamanage.service.QuotaAccountServiceImplTest`
- mock 各操作的异常场景，测试校验逻辑的正常执行

