/*
 *
 * QuotaAccountManager.java
 * Copyright 2023 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

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
 *
 * @description:
 * @author: chixian
 * @date: 2024-01-30 16:12
 **/
@Component
@RequiredArgsConstructor
public class QuotaAccountManager {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(QuotaAccountManager.class);

    private final QuotaAccountMapper quotaAccountMapper;

    private final UserAccountMapper userAccountMapper;

    private final QuotaUpdateLogMapper quotaUpdateLogMapper;


    @Transactional(rollbackFor = Exception.class, value = "transactionManager") // 定义manager todo
    public void initAccount(Long userId, Integer accountType, Double quota) throws BizException {

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


}
