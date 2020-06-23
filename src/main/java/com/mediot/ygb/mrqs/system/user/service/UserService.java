package com.mediot.ygb.mrqs.system.user.service;



import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.system.user.dto.UserInfoDto;
import com.mediot.ygb.mrqs.system.user.entity.UserInfo;


import java.util.Map;

public interface UserService extends BaseService<UserInfo> {
    Map<String, Object> findPageListByParam(UserInfoDto userInfoDto);

    int insertAndUpdateUser(UserInfoDto userInfoDto,Boolean isOrgUser);

    void updatePwd(UserInfo userInfo);

    UserInfo findUserInfoByParameter(UserInfoDto userInfoDto);

    void saveUserInfoDto(UserInfoDto userInfoDto);

    void modifyPwd(UserInfo userInfo);

    /**
     * 分页查询用户
     * @param userInfoDto
     * @return
     */
    //Pager<UserInfoVo> list(UserInfoDto userInfoDto);

}
