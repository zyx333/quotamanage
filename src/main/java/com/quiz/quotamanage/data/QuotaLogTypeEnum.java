package com.quiz.quotamanage.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuotaLogTypeEnum {

    /**
     * 变更类型
     */
    ADD_ACCOUNT((byte) 0, "新增账号"),
    ACCOUNT_QUOTA_UP((byte) 1, "账户提额"),
    ACCOUNT_QUOTA_DOWN((byte) 2, "账户降额"),
    ;

    /**
     * 变更类型
     */
    private Byte type;

    /**
     * 变更说明
     */
    private String desc;
}
