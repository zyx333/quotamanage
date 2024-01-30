package com.quiz.quotamanage.service;

import com.quiz.quotamanage.BizException;
import com.quiz.quotamanage.data.QuotaAccountDto;

public interface QuotaAccountService {

    /**
     * 初始化账号
     * @param userId
     * @param accountType
     */
    void initAccount(Long userId, Integer accountType, Double quota) throws BizException;

    /**
     * 新增额度账户
     * @param userId
     * @param accountType
     * @param quota
     */
    void addQuotaAccount(Long userId, Integer accountType, Double quota) throws BizException;

    /**
     * 提高额度
     * @param userId
     * @param accountType
     * @param quota
     */
    void increaseQuota(Long userId, Integer accountType, Double quota) throws BizException;
    /**
     * 降低额度
     * @param userId
     * @param accountType
     * @param quota
     */
    void decreaseQuota(Long userId, Integer accountType, Double quota) throws BizException;

    /**
     * 查询额度账号
     * @param userId
     * @param accountType
     * @return
     */
    QuotaAccountDto getQuotaAccountByUserAndType(Long userId, Integer accountType);
}
