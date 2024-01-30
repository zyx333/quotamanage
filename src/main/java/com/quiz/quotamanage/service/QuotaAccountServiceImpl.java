package com.quiz.quotamanage.service;

import com.quiz.quotamanage.BizException;
import com.quiz.quotamanage.data.AccountTypeEnum;
import com.quiz.quotamanage.data.QuotaAccountDto;
import com.quiz.quotamanage.data.QuotaAccountPo;
import com.quiz.quotamanage.data.UserAccountDto;
import com.quiz.quotamanage.data.UserAccountPo;
import com.quiz.quotamanage.manager.QuotaAccountManager;
import com.quiz.quotamanage.mapper.QuotaAccountMapper;
import com.quiz.quotamanage.mapper.UserAccountMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuotaAccountServiceImpl implements QuotaAccountService {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(QuotaAccountServiceImpl.class);

    private static final Double DEFAULT_QUOTA = 10000.0;

    private final QuotaAccountMapper quotaAccountMapper;

    private final UserAccountMapper userAccountMapper;

    private final QuotaAccountManager quotaAccountManager;

    @Override
    public void initAccount(Long userId, Byte accountType) throws BizException {
        if (AccountTypeEnum.getEnumByType(accountType) == null) {
            throw new BizException("账号类型不存在");
        }
        // todo 分布式锁

        final UserAccountPo existedAccount = userAccountMapper.selectByUser(userId);
        if (existedAccount != null) {
            throw new BizException("账号已存在");
        }

        quotaAccountManager.initAccount(userId, accountType, DEFAULT_QUOTA);

    }

    @Override
    public void addQuotaAccount(Long userId, Byte accountType) throws BizException {
        // 前置检查
        preCheckAddQuotaAccount(userId, accountType);

        // 添加额度账号
        quotaAccountManager.addQuotaAccount(userId, accountType, DEFAULT_QUOTA);

    }

    private void preCheckAddQuotaAccount(Long userId, Byte accountType) throws BizException {
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
    public void increaseQuota(final Long userId, final Byte accountType, final Double quota) throws BizException {
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
    public void decreaseQuota(Long userId, Byte accountType, Double quota) throws BizException {
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
    public UserAccountDto getQuotaAccountByUserAndType(Long userId) {
        UserAccountDto result = new UserAccountDto();
        result.setUserId(userId);

        final UserAccountPo userAccountPo = userAccountMapper.selectByUser(userId);
        if (userAccountPo == null) {
            logger.warn("查询的账户不存在, userId:{}", userId);
            return result;
        }
        final List<QuotaAccountPo> quotaAccountPos = quotaAccountMapper.selectByUser(userId);
        if (CollectionUtils.isEmpty(quotaAccountPos)) {
            logger.warn("查询的额度账号为空, userId:{}", userId);
            return result;
        }

        result.setQuotaTotal(userAccountPo.getQuota());
        final List<QuotaAccountDto> quotaAccountDtos = quotaAccountPos.stream().map(QuotaAccountDto::fromPo).filter(
                Objects::nonNull).collect(Collectors.toList());
        result.setQuotaAccountDtos(quotaAccountDtos);

        return result;
    }
}
