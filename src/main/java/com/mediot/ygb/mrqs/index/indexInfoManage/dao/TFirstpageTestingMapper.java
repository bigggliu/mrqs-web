package com.mediot.ygb.mrqs.index.indexInfoManage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.mediot.ygb.mrqs.index.indexInfoManage.entity.TFirstPageTesting;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

public interface TFirstpageTestingMapper extends BaseMapper<TFirstPageTesting> {


    int batchInsertTFInfo(List<TFirstPageTesting> tFirstPageTestingList);

    List<TFirstPageTesting> selectPageList(Map<String, Object> queryMap);

    Integer selectCountByMap(Map<String, Object> queryMap);

    /*@SelectProvider(type =TFirstpageTestingDao.class,method = "findHitRecordByQueryStr")
    List<TFirstPageTesting> findHitRecordByQueryStr(String str);
     */

    String selectFirstRowByParam(Map<String, Object> queryMap);

    String selectLastRowByParam(Map<String, Object> queryMap);

    List<TFirstPageTesting> selectCaseList(Map<String, Object> queryMap);

    List<TFirstPageTesting> selectBaseInfoOfCaseList(Map<String, Object> queryMap);

    /*class TFirstpageTestingDao{
        public String findHitRecordByQueryStr(String str) {
            return str;
        }
    }
     */

    List<TFirstPageTesting> queryFirstPageList(Map<String, Object> queryMap);


}