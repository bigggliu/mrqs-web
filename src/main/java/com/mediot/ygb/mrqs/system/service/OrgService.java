package com.mediot.ygb.mrqs.system.service;

import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.system.vo.OrgTree;
import com.mediot.ygb.mrqs.system.dao.OrgDao;
import com.mediot.ygb.mrqs.system.dao.UserDao;
import com.mediot.ygb.mrqs.system.pojo.Org;
import com.mediot.ygb.mrqs.workingRecord.FileUploadManage.dao.FileUploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrgService {

    @Autowired
    private OrgDao orgDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FileUploadMapper fileUploadMapper;

    public int insert(Org org) throws Exception {
        validationOrg(org);
        checkOrgExistByName(org);
        checkOrgExistByCode(org);
        org.setCreateTime(new Date());
        org.setUpdateTime(new Date());
        return orgDao.insert(org);
    }

    public int update(Org org) throws Exception {
        validationOrg(org);
        Org tempOrg = orgDao.selectById(org.getOrgId());
        if(!tempOrg.getOrgName().equals(org.getOrgName())){
            checkOrgExistByName(org);
        }
        if(!tempOrg.getOrgCode().equals(org.getOrgCode())){
            checkOrgExistByCode(org);
        }
        org.setUpdateTime(new Date());
        return orgDao.updateById(org);
    }

    public int delete(Org org) throws Exception {
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("PARENT_ID",org.getOrgId());
        if(orgDao.selectByMap(queryMap).size() > 0){
            throw new Exception("无法删除，该机构下还有子机构");
        }
        Map<String,Object> queryMap1 = new HashMap<>();
        queryMap1.put("ORG_ID",org.getOrgId());
        if(userDao.selectByMap(queryMap1).size() > 0){
           throw new Exception("无法删除，该机构下有用户");
        }
        if(fileUploadMapper.selectByMap(queryMap1).size() > 0){
            throw new Exception("无法删除，该机构有检测数据");
        }
        return orgDao.deleteById(org);
    }

    public List<Org> orgListFuzzyQuery(String queryStr){
        return orgDao.orgListFuzzyQuery(queryStr);
    }

    public Org selectById(Org org){
        LocalAssert.notNull(org.getOrgId(),"机构ID不能为空");
        return orgDao.selectById(org);
    }

    /**
     * 查询机构树
     */
    public List<OrgTree> getOrgTree(){
        //获取顶级列表
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("PARENT_ID",0);
        List<Org> topList = orgDao.selectByMap(queryMap);
        List<OrgTree> orgTrees = new ArrayList<>();
        orgTrees = getTree(topList,orgTrees);
        return orgTrees;
    }

    /**
     * 迭代获得机构树
     */
    public List<OrgTree> getTree(List<Org> topList,List<OrgTree> orgTrees){
        for(Org org : topList){
            OrgTree orgTree = new OrgTree();
            orgTree.setOrgId(org.getOrgId());
            orgTree.setOrgName(org.getOrgName());
            orgTree.setOrgCode(org.getOrgCode());
            orgTree.setSourceCode(org.getSourceCode());
            orgTree.setStandardCode(org.getStandardCode());
            orgTree.setRemark(org.getRemark());
            orgTree.setState(org.getState());
            List<OrgTree> tempOrgTrees = new ArrayList<>();
            Map<String,Object> queryMap = new HashMap<>();
            queryMap.put("PARENT_ID",org.getOrgId());
            List<Org> tempTopList = orgDao.selectByMap(queryMap);
            getTree(tempTopList,tempOrgTrees);
            orgTree.setChildren(tempOrgTrees);
            orgTrees.add(orgTree);
        }
        return orgTrees;
    }


    public void validationOrg(Org org){
        LocalAssert.notNull(org.getOrgName(),"机构名称不能为空");
        LocalAssert.notNull(org.getParentId(),"父级机构不能为空");
        LocalAssert.notNull(org.getOrgCode(),"机构代码不能为空");
        LocalAssert.notNull(org.getStandardCode(),"编码格式不能为空");
        LocalAssert.notNull(org.getSourceCode(),"编码来源不能为空");
        LocalAssert.notNull(org.getParentId(),"机构状态不能为空");
    }

    public void checkOrgExistByName(Org org) throws Exception {
        Map<String,Object> orgNameQueryMap = new HashMap<>();
        orgNameQueryMap.put("ORG_NAME",org.getOrgName());
        if(orgDao.selectByMap(orgNameQueryMap).size() > 0){
            throw new Exception("机构名称已存在");
        }

    }

    public void checkOrgExistByCode(Org org) throws Exception {
        Map<String,Object> orgCodeQueryMap = new HashMap<>();
        orgCodeQueryMap.put("ORG_CODE",org.getOrgCode());
        if(orgDao.selectByMap(orgCodeQueryMap).size() > 0){
            throw new Exception("机构代码已存在");
        }
    }
}
