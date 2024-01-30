package com.quiz.quotamanage.data;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class QuotaAccountPo {

    private Long id;

    /**
     * 账号名称
     */
    private String accountName;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 账户类型
     */
    private Integer accountType;

    /**
     * 账户额度
     */
    private Double quota;


    /**
     * 删除状态，0  否， 1  是
     */
    private Integer deleted;


    /**
     * 创建时间
     */
    private Timestamp createdTime;

    /**
     * 更新时间
     */
    private Timestamp updatedTime;

}
