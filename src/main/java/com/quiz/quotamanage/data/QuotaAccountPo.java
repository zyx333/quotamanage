package com.quiz.quotamanage.data;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class QuotaAccountPo {

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
     * 删除状态，0  否， 1  是
     */
    private Integer deleted;


    /**
     * 创建时间
     */
    private Timestamp created;

    /**
     * 更新时间
     */
    private Timestamp lastmodified;

}
