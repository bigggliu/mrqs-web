package com.mediot.ygb.mrqs.system.orgManage.web;




import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.system.user.dto.UserInfoDto;
import com.mediot.ygb.mrqs.system.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orgMgr")
public class OrgMgrController {

    @Autowired
    UserService userService;

    /**
     * 保存用户
     * @param userInfoDto
     */
    @RequestMapping("/save")
    //@OperationInfo(operationName="保存用户信息",moduleId = "saveUser",moduleName="用户管理", keys="userId,email",values="用户id,邮箱")
    public void saveUser(UserInfoDto userInfoDto){
        LocalAssert.notNull(userInfoDto,"对象不能为空");
        LocalAssert.notNull(userInfoDto.getOrgId(),"orgId不能为空");
        LocalAssert.notNull(userInfoDto.getSystemCodes(),"systemCodes不能为空");
        userService.saveUserInfoDto(userInfoDto);
    }

}
