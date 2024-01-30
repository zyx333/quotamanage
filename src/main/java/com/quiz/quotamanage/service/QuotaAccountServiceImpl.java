package com.quiz.quotamanage.service;

import com.quiz.quotamanage.BizException;
import com.quiz.quotamanage.data.QuotaAccountDto;
import com.quiz.quotamanage.data.QuotaAccountPo;
import com.quiz.quotamanage.data.UserAccountPo;
import com.quiz.quotamanage.manager.QuotaAccountManager;
import com.quiz.quotamanage.mapper.QuotaAccountMapper;
import com.quiz.quotamanage.mapper.QuotaUpdateLogMapper;
import com.quiz.quotamanage.mapper.UserAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuotaAccountServiceImpl implements QuotaAccountService {

    private final QuotaAccountMapper quotaAccountMapper;

    private final UserAccountMapper userAccountMapper;

    private final QuotaUpdateLogMapper quotaUpdateLogMapper;

    private final QuotaAccountManager quotaAccountManager;

    @Override
    public void initAccount(Long userId, Integer accountType, Double quota) throws BizException {
        // todo 分布式锁
        final UserAccountPo existedAccount = userAccountMapper.selectByUser(userId);
        if (existedAccount != null) {
            throw new BizException("账号已存在");
        }

        quotaAccountManager.initAccount(userId, accountType, quota);

    }

    @Override
    public void addQuotaAccount(Long userId, Integer accountType, Double quota) throws BizException {
        // 前置检查
        preCheckAddQuotaAccount(userId, accountType, quota);

        // 添加额度账号
        quotaAccountManager.addQuotaAccount(userId, accountType, quota);

    }

    private void preCheckAddQuotaAccount(Long userId, Integer accountType, Double quota) throws BizException {
        final UserAccountPo existedAccount = userAccountMapper.selectByUser(userId);
        if (existedAccount == null) {
            throw new BizException("账号不存在，请先初始化账号");
        }

        final QuotaAccountPo existedQuotaAccount = quotaAccountMapper.selectByUserAndType(userId, accountType);
        if (existedQuotaAccount != null) {
            throw new BizException("额度类型已存在");
        }
    }

    @Override
    public void increaseQuota(final Long userId, final Integer accountType, final Double quota) throws BizException {
        if (quota <= 0) {
            throw new BizException("额度必须大于0");
        }
        final QuotaAccountPo existedQuota = quotaAccountMapper.selectByUserAndType(userId, accountType);
        if (existedQuota == null) {
            throw new BizException("额度类型不存在");
        }

        // 提升额度
        quotaAccountManager.updateQuota(userId, accountType, quota, existedQuota.getId());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseQuota(Long userId, Integer accountType, Double quota) throws BizException {
        if (quota >= 0) {
            throw new BizException("额度必须小于0");
        }

        final QuotaAccountPo existedQuota = quotaAccountMapper.selectByUserAndType(userId, accountType);
        if (existedQuota == null) {
            throw new BizException("额度类型不存在");
        }

        if (existedQuota.getQuota() + quota < 0) {
            throw new BizException("额度不足");
        }

        // 降低额度
        quotaAccountManager.updateQuota(userId, accountType, quota, existedQuota.getId());

    }

    @Override
    public QuotaAccountDto getQuotaAccountByUserAndType(Long userId, Integer accountType) {
        return null;
    }
}
