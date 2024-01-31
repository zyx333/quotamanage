package com.quiz.quotamanage.data;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuotaAccountVo {

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 账户类型
     */
    private Byte accountType;

    /**
     * 账户额度
     */
    private Double quota;

    /**
     * 创建时间
     */
    private Timestamp createdTime;



    public static QuotaAccountVo fromDto(QuotaAccountDto quotaAccountDto){
        if (quotaAccountDto == null) {
            return null;
        }

        QuotaAccountVo vo = new QuotaAccountVo();
        vo.setQuota(quotaAccountDto.getQuota());
        vo.setUserId(quotaAccountDto.getUserId());
        vo.setAccountType(quotaAccountDto.getAccountType());
        vo.setCreatedTime(quotaAccountDto.getCreatedTime());

        return vo;

    }

}
