package com.quiz.quotamanage.service;

import com.quiz.quotamanage.data.QuotaAccountDto;

public interface QuotaAccountService {

    /**
     * 初始化账号
     * @param userId
     * @param accountType
     */
    void initAccount(Long userId, Integer accountType);

    /**
     * 提高额度
     * @param userId
     * @param accountType
     * @param quota
     */
    void addQuotaAccount(Long userId, Integer accountType, Double quota);

    /**
     * 降低额度
     * @param userId
     * @param accountType
     * @param quota
     */
    void deductQuotaAccount(Long userId, Integer accountType, Double quota);

    /**
     * 查询额度账号
     * @param userId
     * @param accountType
     * @return
     */
    QuotaAccountDto getQuotaAccountByUserAndType(Long userId, Integer accountType);
}
