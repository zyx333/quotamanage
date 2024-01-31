package com.quiz.quotamanage.service;

import com.quiz.quotamanage.BizException;
import com.quiz.quotamanage.data.QuotaAccountPo;
import com.quiz.quotamanage.mapper.QuotaAccountMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@RequiredArgsConstructor
public class QuotaScheduledService {

    private static final Logger logger = LoggerFactory.getLogger(QuotaScheduledService.class);

    private final ThreadPoolExecutor threadPoolExecutor;

    private final QuotaAccountMapper quotaAccountMapper;

    private final QuotaAccountService quotaAccountService;


    @Scheduled(cron = "0/10 * * * * ?")
    public void mockConcurrent() {

        threadPoolExecutor.execute(() -> {

            List<QuotaAccountPo> accountPos = quotaAccountMapper.selectByUser(1L);
            logger.info("mockConcurrent:{}", System.currentTimeMillis());
            logger.info("result:{}", accountPos);

        });

    }

    @Scheduled
    public void initAccount() {
        Random random = new Random();
        final long userId = random.nextLong();
        final int accountType = random.nextInt(3);
        try {
            quotaAccountService.initAccount(userId, (byte) accountType);
        } catch (BizException e) {
            throw new RuntimeException(e);
        }
    }

}
