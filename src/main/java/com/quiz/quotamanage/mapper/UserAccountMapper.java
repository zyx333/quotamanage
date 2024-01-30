package com.quiz.quotamanage.mapper;

import com.quiz.quotamanage.data.UserAccountPo;
import org.apache.ibatis.annotations.Param;


public interface UserAccountMapper {

    UserAccountPo selectByUser(@Param("userId") Long userId);

    int insertUserAccount(UserAccountPo userAccountPo);

    int updateUserAccount(UserAccountPo userAccountPo);

}
