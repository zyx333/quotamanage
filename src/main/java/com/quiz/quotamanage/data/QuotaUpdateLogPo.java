package com.quiz.quotamanage.data;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class QuotaUpdateLogPo {

    private Long id;

    /**
     * 账号名称
     */
    private Long accountId;


    /**
     * 变更类型
     */
    private Integer accountType;

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

}
