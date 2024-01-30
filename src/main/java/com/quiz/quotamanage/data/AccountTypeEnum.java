package com.quiz.quotamanage.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum AccountTypeEnum {

    /**
     * 额度账户
     */
    ACCOUNT_TYPE_DEFAULT((byte) 0, "默认账户"),
    ACCOUNT_TYPE_1((byte) 1, "账户 1"),
    ACCOUNT_TYPE_2((byte) 2, "账户 2"),
    ;

    private static final Map<Byte, AccountTypeEnum> MAP =
            Collections.unmodifiableMap(Arrays.stream(AccountTypeEnum.values())
                    .collect(Collectors.toMap(AccountTypeEnum::getType, Function.identity())));

    public static AccountTypeEnum getEnumByType(final Byte type) {
        return MAP.get(type);
    }
    /**
     * 账户类型
     */
    private Byte type;

    /**
     * 账户说明
     */
    private String desc;
}
