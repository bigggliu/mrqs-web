package com.mediot.ygb.mrqs.system.group.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.system.group.entity.Group;

import java.util.List;


/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author
 * @since
 */
public interface GroupMapper extends BaseMapper<Group> {

    List<Group> selectRefUserList(String userId);
}
