package com.quiz.quotamanage.controller;

import com.quiz.quotamanage.data.UserAccountDto;
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
    public Double getQuotaAccount(@RequestParam("userId") Long userId) {

        UserAccountDto account = quotaAccountService.getQuotaAccountByUserAndType(userId);

        return 0d;
    }


}
