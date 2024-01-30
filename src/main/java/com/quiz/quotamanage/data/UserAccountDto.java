package com.quiz.quotamanage.data;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UserAccountDto {

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 账户总额度
     */
    private Double quotaTotal;

    /**
     * 子额度列表
     */
    private List<QuotaAccountDto> quotaAccountDtos;
}
