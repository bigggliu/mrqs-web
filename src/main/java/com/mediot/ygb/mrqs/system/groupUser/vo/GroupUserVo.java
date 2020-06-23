package com.mediot.ygb.mrqs.system.groupUser.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class GroupUserVo implements Serializable {

    private String groupUserId;

    private Long groupId;

    private Long userId;

    private String userName;

    private String userNo;

}
