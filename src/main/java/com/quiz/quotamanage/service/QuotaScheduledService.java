package com.quiz.quotamanage.service;

import com.quiz.quotamanage.BizException;
import com.quiz.quotamanage.data.UserAccountDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@RequiredArgsConstructor
public class QuotaScheduledService {

    private static final Logger logger = LoggerFactory.getLogger(QuotaScheduledService.class);

    private final ThreadPoolExecutor threadPoolExecutor;

    private final QuotaAccountService quotaAccountService;


    @Scheduled(fixedDelay = 1000 * 60)
    public void initAccount() {
        Random random = new Random();
        final long userId = random.nextInt(1000);
        final int accountType = random.nextInt(3);
        try {
            logger.info("initAccount, userId:{}, accountType:{}", userId, accountType);
            quotaAccountService.initAccount(userId, (byte) accountType);
        } catch (BizException e) {
            logger.error("initAccount failed. userId:{}, accountType:{}", userId, accountType, e);
        }
    }

    @Scheduled(fixedDelay = 1000 * 80)
    public void addQuotaAccount() {
        Random random = new Random();
        final long userId = random.nextInt(1000);
        final int accountType = random.nextInt(3);
        try {
            logger.info("addQuotaAccount, userId:{}, accountType:{}", userId, accountType);
            quotaAccountService.initAccount(userId, (byte) 0);

            quotaAccountService.addQuotaAccount(userId, (byte) accountType);
        } catch (BizException e) {
            logger.error("addQuotaAccount failed. userId:{}, accountType:{}", userId, accountType, e);
        }
    }

    @Scheduled(fixedDelay = 1000 * 100)
    public void increaseQuota() {
        Random random = new Random();
        final long userId = random.nextInt(1000);
        final int accountType = random.nextInt(3);
        try {
            logger.info("increaseQuota, userId:{}, accountType:{}", userId, accountType);
            quotaAccountService.initAccount(userId, (byte) accountType);

            quotaAccountService.increaseQuota(userId, (byte) accountType, 1000.0);
        } catch (BizException e) {
            logger.error("increaseQuota failed. userId:{}, accountType:{}", userId, accountType, e);
        }
    }

    @Scheduled(fixedDelay = 1000 * 110)
    public void decreaseQuota() {
        Random random = new Random();
        final long userId = random.nextInt(1000);
        final int accountType = random.nextInt(3);
        try {
            logger.info("decreaseQuota, userId:{}, accountType:{}", userId, accountType);
            quotaAccountService.initAccount(userId, (byte) accountType);

            quotaAccountService.decreaseQuota(userId, (byte) accountType, -100.0);
        } catch (BizException e) {
            logger.error("decreaseQuota failed. userId:{}, accountType:{}", userId, accountType, e);
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void getQuotaAccountByUserAndType() {
        Random random = new Random();
        final long userId = random.nextInt(1000);
        try {
            logger.info("getQuotaAccountByUserAndType, userId:{}", userId);
            final UserAccountDto userAccountDto = quotaAccountService.getQuotaAccountByUserAndType(userId);
            logger.info("getQuotaAccountByUserAndType result:{}", userAccountDto);
        } catch (Exception e) {
            logger.error("getQuotaAccountByUserAndType failed. userId:{} ", userId, e);
        }
    }


    @Scheduled(cron = "0 0/3 * * * ?")
    public void concurrentTask() {

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            final long userId = random.nextInt(100);
            final int accountType = random.nextInt(3);
            final QuotaTask task = new QuotaTask(userId, (byte) accountType);
            threadPoolExecutor.execute(task);
        }

    }

    class QuotaTask implements Runnable{
        private final Long userId;

        private final Byte accountType;

        public QuotaTask(Long userId, Byte accountType) {
            this.userId = userId;
            this.accountType = accountType;
        }

        @Override
        public void run() {
            try {
                logger.info("QuotaTask executed, userId:{}, accountType:{}", userId, accountType);
                quotaAccountService.initAccount(userId, accountType);
                quotaAccountService.decreaseQuota(userId, accountType, -100.0);
            } catch (BizException e) {
                logger.error("QuotaTask failed. userId:{}, accountType:{}", userId, accountType, e);
            }
        }
    }

}
