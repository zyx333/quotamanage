package com.quiz.quotamanage.data;

import lombok.Data;

@Data
public class UserAccountParamVo {

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

}
