package com.quiz.quotamanage.data;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class QuotaUpdateLogPo {

    private Long id;

    /**
     * 账号id
     */
    private Long quotaAccountId;

    /**
     * 变更类型, {@link QuotaLogTypeEnum}
     */
    private Byte updateType;

    /**
     * 变更额度
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

}
