package com.mediot.ygb.mrqs.analysis.medRecManage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.analysis.medRecManage.vo.MyErrorDetiVo;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface MyErrorDetiVoMapper extends BaseMapper<MyErrorDetiVo> {

    @SelectProvider(type = MyErrorDetiVoDao.class,method = "findHitRecordByQueryStr")
    List<MyErrorDetiVo> findHitRecordByQueryStr(String str);

    class MyErrorDetiVoDao{
        public String findHitRecordByQueryStr(String str) {
            return str;
        }
    }
}
