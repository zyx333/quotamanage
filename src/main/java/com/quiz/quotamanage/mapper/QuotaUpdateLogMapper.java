package com.quiz.quotamanage.mapper;

import com.quiz.quotamanage.data.QuotaAccountPo;
import com.quiz.quotamanage.data.QuotaUpdateLogPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface QuotaUpdateLogMapper {


    List<QuotaUpdateLogPo> selectByAccount(@Param("quotaAccountId") Long quotaAccountId);

    int insertQuotaLog(QuotaUpdateLogPo quotaAccountPo);

}
