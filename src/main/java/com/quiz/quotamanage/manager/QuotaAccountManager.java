package com.quiz.quotamanage.manager;

import com.quiz.quotamanage.BizException;
import com.quiz.quotamanage.data.QuotaAccountPo;
import com.quiz.quotamanage.data.QuotaLogTypeEnum;
import com.quiz.quotamanage.data.QuotaUpdateLogPo;
import com.quiz.quotamanage.data.UserAccountPo;
import com.quiz.quotamanage.mapper.QuotaAccountMapper;
import com.quiz.quotamanage.mapper.QuotaUpdateLogMapper;
import com.quiz.quotamanage.mapper.UserAccountMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 额度操作
 * @description:
 * @author: zyx
 * @date: 2024-01-30 16:12
 **/
@Component
@RequiredArgsConstructor
public class QuotaAccountManager {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(QuotaAccountManager.class);

    private final QuotaAccountMapper quotaAccountMapper;

    private final UserAccountMapper userAccountMapper;

    private final QuotaUpdateLogMapper quotaUpdateLogMapper;


    @Transactional(rollbackFor = Exception.class)
    public void initAccount(Long userId, Byte accountType, Double quota) throws BizException {

        try {
            UserAccountPo userAccountPo = UserAccountPo.builder().userId(userId).quota(quota).build();
            userAccountMapper.insertUserAccount(userAccountPo);

            QuotaAccountPo quotaAccountPo = QuotaAccountPo.builder().userId(userId).accountType(accountType).quota(quota)
                    .build();
            quotaAccountMapper.insertQuotaAccount(quotaAccountPo);

            QuotaUpdateLogPo logPo = QuotaUpdateLogPo.builder().quotaAccountId(quotaAccountPo.getId()).updateType(
                    QuotaLogTypeEnum.ADD_ACCOUNT.getType()).quota(quota).build();
            quotaUpdateLogMapper.insertQuotaLog(logPo);
        } catch (Exception e) {
            logger.error("initAccount failed", e);
            throw new BizException("账号初始化失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addQuotaAccount(Long userId, Byte accountType, Double quota) throws BizException {

        try {
            QuotaAccountPo quotaAccountPo = QuotaAccountPo.builder().userId(userId).accountType(accountType).quota(quota)
                    .build();
            quotaAccountMapper.insertQuotaAccount(quotaAccountPo);

            UserAccountPo userAccountPo = UserAccountPo.builder().userId(userId).quota(quota).build();
            userAccountMapper.updateUserAccount(userAccountPo);

            QuotaUpdateLogPo logPo = QuotaUpdateLogPo.builder().quotaAccountId(quotaAccountPo.getId()).updateType(
                    QuotaLogTypeEnum.ADD_ACCOUNT.getType()).quota(quota).build();
            quotaUpdateLogMapper.insertQuotaLog(logPo);
        } catch (Exception e) {
            logger.error("addQuotaAccount failed", e);
            throw new BizException("添加额度账号失败");
        }


    }

    @Transactional(rollbackFor = Exception.class)
    public void updateQuota(final Long userId, final Byte accountType, final Double quota, Long quotaAccountId)
            throws BizException {
        try {
            QuotaAccountPo newQuota =
                    QuotaAccountPo.builder().userId(userId).accountType(accountType).quota(quota).build();
            quotaAccountMapper.updateQuotaAccount(newQuota);

            UserAccountPo newUserAccount = UserAccountPo.builder().userId(userId).quota(quota).build();
            userAccountMapper.updateUserAccount(newUserAccount);

            QuotaLogTypeEnum logType =
                    quota > 0 ? QuotaLogTypeEnum.ACCOUNT_QUOTA_UP : QuotaLogTypeEnum.ACCOUNT_QUOTA_DOWN;
            QuotaUpdateLogPo logPo =
                    QuotaUpdateLogPo.builder().quotaAccountId(quotaAccountId).updateType(logType.getType()).quota(quota)
                            .build();
            quotaUpdateLogMapper.insertQuotaLog(logPo);
        } catch (Exception e) {
            logger.error("increaseQuota failed", e);
            throw new BizException("提升额度失败");
        }
    }


}
