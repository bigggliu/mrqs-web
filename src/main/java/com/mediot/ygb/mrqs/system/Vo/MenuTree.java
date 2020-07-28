package com.mediot.ygb.mrqs.system.Vo;

import com.mediot.ygb.mrqs.system.pojo.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuTree extends Menu {

    private List<MenuTree> children;
}
