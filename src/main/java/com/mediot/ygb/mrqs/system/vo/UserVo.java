package com.mediot.ygb.mrqs.system.vo;

import com.mediot.ygb.mrqs.system.pojo.Org;
import com.mediot.ygb.mrqs.system.pojo.Role;
import com.mediot.ygb.mrqs.system.pojo.User;
import lombok.Data;

import java.util.List;

@Data
public class UserVo extends User {

    private String imageCode;
    private Org org;
    private List<Role> roleList;
    private List<MenuTree> menuTrees;
}
