package com.mediot.ygb.mrqs.system.Vo;

import com.mediot.ygb.mrqs.system.pojo.Org;
import lombok.Data;

import java.util.List;

@Data
public class OrgTree extends Org {

    private List<OrgTree> children;
}
