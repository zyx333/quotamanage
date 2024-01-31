package com.quiz.quotamanage.controller;

import com.quiz.quotamanage.BizException;
import com.quiz.quotamanage.data.UserAccountDto;
import com.quiz.quotamanage.data.UserAccountParamVo;
import com.quiz.quotamanage.service.QuotaAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuotaAccountController {

    private final QuotaAccountService quotaAccountService;

    @PostMapping("/account/init")
    public void initAccount(@RequestBody UserAccountParamVo paramVo) throws BizException {

        quotaAccountService.initAccount(paramVo.getUserId(), paramVo.getAccountType());
    }

    @PutMapping("/account/quota/add")
    public void addQuotaAccount(@RequestBody UserAccountParamVo paramVo) throws BizException {
        quotaAccountService.addQuotaAccount(paramVo.getUserId(), paramVo.getAccountType());
    }

    @PutMapping("/account/quota/increase")
    public void increaseQuota(@RequestBody UserAccountParamVo paramVo) throws BizException {
        quotaAccountService.increaseQuota(paramVo.getUserId(), paramVo.getAccountType(), paramVo.getQuota());
    }

    @PutMapping("/account/quota/decrease")
    public void decreaseQuota(@RequestBody UserAccountParamVo paramVo) throws BizException {
        quotaAccountService.decreaseQuota(paramVo.getUserId(), paramVo.getAccountType(), paramVo.getQuota());
    }

    @GetMapping("/account/quota")
    public Double getQuotaAccount(@RequestParam("userId") Long userId) {

        UserAccountDto account = quotaAccountService.getQuotaAccountByUserAndType(userId);

        return account == null ? 0.0 : account.getQuotaTotal();
    }

}
