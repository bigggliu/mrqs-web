package com.mediot.ygb.mrqs.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.system.pojo.Org;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrgDao extends BaseMapper<Org> {

    List<Org> orgListFuzzyQuery(String queryStr);
}
