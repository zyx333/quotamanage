package com.quiz.quotamanage.service;

import com.quiz.quotamanage.data.QuotaAccountDto;
import com.quiz.quotamanage.data.UserAccountPo;
import com.quiz.quotamanage.mapper.QuotaAccountMapper;
import com.quiz.quotamanage.mapper.QuotaUpdateLogMapper;
import com.quiz.quotamanage.mapper.UserAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadPoolExecutor;

@Service
@RequiredArgsConstructor
public class QuotaAccountServiceImpl implements QuotaAccountService {

    private final QuotaAccountMapper quotaAccountMapper;

    private final UserAccountMapper userAccountMapper;

    private final QuotaUpdateLogMapper quotaUpdateLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, value = "transactionManager") // 定义manager todo
    public void initAccount(Long userId, Integer accountType) {
        final UserAccountPo userAccountPo = userAccountMapper.selectByUser(userId);
        if (userAccountPo != null) {

        }

    }

    @Override
    public void addQuotaAccount(Long userId, Integer accountType, Double quota) {
        // 添加额度汇总表
        // todo 更新额度时，记录变更日志

    }

    @Override
    public void deductQuotaAccount(Long userId, Integer accountType, Double quota) {

    }

    @Override
    public QuotaAccountDto getQuotaAccountByUserAndType(Long userId, Integer accountType) {
        return null;
    }
}
