package com.quiz.quotamanage.controller;

import com.quiz.quotamanage.data.QuotaAccountDto;
import com.quiz.quotamanage.service.QuotaAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuotaAccountController {
    private final QuotaAccountService quotaAccountService;


    @GetMapping
    public Double getQuotaAccount(@RequestParam("userId") Long userId,
                                @RequestParam(value = "accountType", required = false) Integer accountType) {

        QuotaAccountDto account = quotaAccountService.getQuotaAccountByUserAndType(userId, accountType);

        return 0d;
    }


}
