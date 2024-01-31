package com.quiz.quotamanage.controller;

import com.quiz.quotamanage.BizException;
import com.quiz.quotamanage.data.Result;
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
    public Result<Boolean> initAccount(@RequestBody UserAccountParamVo paramVo) throws BizException {

        quotaAccountService.initAccount(paramVo.getUserId(), paramVo.getAccountType());
        return Result.success(true);
    }

    @PutMapping("/account/quota/add")
    public Result<Boolean> addQuotaAccount(@RequestBody UserAccountParamVo paramVo) throws BizException {
        quotaAccountService.addQuotaAccount(paramVo.getUserId(), paramVo.getAccountType());
        return Result.success(true);
    }

    @PutMapping("/account/quota/increase")
    public Result<Boolean> increaseQuota(@RequestBody UserAccountParamVo paramVo) throws BizException {
        quotaAccountService.increaseQuota(paramVo.getUserId(), paramVo.getAccountType(), paramVo.getQuota());
        return Result.success(true);
    }

    @PutMapping("/account/quota/decrease")
    public Result<Boolean> decreaseQuota(@RequestBody UserAccountParamVo paramVo) throws BizException {
        quotaAccountService.decreaseQuota(paramVo.getUserId(), paramVo.getAccountType(), paramVo.getQuota());
        return Result.success(true);
    }

    @GetMapping("/account/quota")
    public Result<Double> getQuotaAccount(@RequestParam("userId") Long userId) {

        UserAccountDto account = quotaAccountService.getQuotaAccountByUserAndType(userId);

        return Result.success(account == null ? 0.0 : account.getQuotaTotal());
    }

}
