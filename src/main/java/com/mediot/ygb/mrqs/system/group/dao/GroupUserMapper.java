package com.mediot.ygb.mrqs.system.group.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.system.groupUser.entity.GroupUser;
import com.mediot.ygb.mrqs.system.user.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色用户关系表 Mapper 接口
 * </p>
 *
 * @author
 * @since
 */
public interface GroupUserMapper extends BaseMapper<GroupUser> {

    /**
     * 查询组成员列表
     * @param pager
     * @return
     */
    List<GroupUser> getGroupUserList(Map<String,Object> paramMap);

    /**
     * 查询待添加进用户组的用户列表
     * @param pager
     * @return
     */
    List<UserInfo> getUnGroupUserList(Map<String, Object> queryMap);
}
