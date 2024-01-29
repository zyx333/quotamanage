package com.quiz.quotamanage.service;

import com.quiz.quotamanage.data.QuotaAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuotaAccountServiceImpl implements QuotaAccountService {

    @Override
    public void initAccount(Long userId, Integer accountType) {

    }

    @Override
    public void addQuotaAccount(Long userId, Integer accountType, Double quota) {
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
