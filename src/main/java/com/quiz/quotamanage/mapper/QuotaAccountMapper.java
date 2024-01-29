package com.quiz.quotamanage.mapper;

import com.quiz.quotamanage.data.QuotaAccountPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface QuotaAccountMapper {

    QuotaAccountPo selectByUserAndType(@Param("userId") Long userId, @Param("accountType") Integer accountType);

    List<QuotaAccountPo> selectByUser(@Param("userId") Long userId);

    int insertQuotaAccount(QuotaAccountPo quotaAccountPo);

    int updateQuotaAccount(QuotaAccountPo quotaAccountPo);



}
