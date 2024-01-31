package com.quiz.quotamanage.data;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserAccountVo {

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
    private List<QuotaAccountVo> quotaAccountVos;

    public static UserAccountVo fromDto(UserAccountDto userAccountDto){
        if (userAccountDto == null) {
            return null;
        }

        UserAccountVo vo = new UserAccountVo();
        vo.setQuotaTotal(userAccountDto.getQuotaTotal());
        vo.setUserId(userAccountDto.getUserId());

        if (userAccountDto.getQuotaAccountDtos() != null) {
            vo.setQuotaAccountVos(userAccountDto.getQuotaAccountDtos().stream().map(QuotaAccountVo::fromDto).collect(
                    Collectors.toList()));
        }

        return vo;
    }

}
