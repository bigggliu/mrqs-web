package com.mediot.ygb.mrqs.system.controller;

import com.mediot.ygb.mrqs.system.vo.OrgTree;
import com.mediot.ygb.mrqs.system.pojo.Org;
import com.mediot.ygb.mrqs.system.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @RequestMapping("/orgListFuzzyQuery")
    public List<Org> orgListFuzzyQuery(String queryStr){
        return orgService.orgListFuzzyQuery(queryStr);
    }

    @RequestMapping("/insert")
    public int insert(Org org) throws Exception{
        return orgService.insert(org);
    }

    @RequestMapping("/update")
    public int update(Org org) throws Exception {
        return orgService.update(org);
    }

    @RequestMapping("/delete")
    public int delete(Org org) throws Exception {
        return orgService.delete(org);
    }

    @RequestMapping("/selectById")
    public Org selectById(Org org){
        return orgService.selectById(org);
    }

    @RequestMapping("/queryOrgTree")
    public List<OrgTree> queryOrgTree(){
        return orgService.getOrgTree();
    }
}
