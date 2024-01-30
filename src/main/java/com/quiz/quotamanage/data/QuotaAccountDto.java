package com.quiz.quotamanage.data;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuotaAccountDto {

    private Long id;

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

    /**
     * 更新时间
     */
    private Timestamp updatedTime;

    public static QuotaAccountDto fromPo(QuotaAccountPo quotaAccountPo){
        if (quotaAccountPo == null) {
            return null;
        }

        QuotaAccountDto quotaAccountDto = new QuotaAccountDto();
        quotaAccountDto.setId(quotaAccountPo.getId());
        quotaAccountDto.setUserId(quotaAccountPo.getUserId());
        quotaAccountDto.setAccountType(quotaAccountPo.getAccountType());
        quotaAccountDto.setQuota(quotaAccountPo.getQuota());
        quotaAccountDto.setCreatedTime(quotaAccountPo.getCreatedTime());
        quotaAccountDto.setUpdatedTime(quotaAccountPo.getUpdatedTime());

        return quotaAccountDto;

    }

}
