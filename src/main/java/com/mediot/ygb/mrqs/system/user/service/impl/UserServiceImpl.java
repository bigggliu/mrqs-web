package com.mediot.ygb.mrqs.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.common.constant.CustomConst;
import com.mediot.ygb.mrqs.common.core.exception.MediotException;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.util.JsonUtil;

import com.mediot.ygb.mrqs.common.util.MD5Util;
import com.mediot.ygb.mrqs.system.group.dao.GroupMapper;
import com.mediot.ygb.mrqs.system.group.entity.Group;
import com.mediot.ygb.mrqs.system.orgManage.dao.OrgRefSystemMapper;
import com.mediot.ygb.mrqs.system.orgManage.entity.OrgRefSystem;
import com.mediot.ygb.mrqs.system.user.dao.UserInfoMapper;
import com.mediot.ygb.mrqs.system.user.dto.UserInfoDto;
import com.mediot.ygb.mrqs.system.user.entity.UserInfo;
import com.mediot.ygb.mrqs.system.user.service.UserService;
import com.mediot.ygb.mrqs.system.user.vo.UserInfoVo;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserInfoMapper, UserInfo> implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private OrgRefSystemMapper orgRefSystemMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Map<String, Object> findPageListByParam(UserInfoDto userInfoDto) {
        LocalAssert.notNull(userInfoDto.getPageNum(),"pageNum不能为空");
        LocalAssert.notNull(userInfoDto.getPageSize(),"pageSize不能为空");
        List<UserInfoVo> userInfoVoList= Lists.newArrayList();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map<String,Object> queryMap= Maps.newHashMap();
        if(StringUtils.isNotBlank(userInfoDto.getOrgId())){
            queryMap.put("orgId",userInfoDto.getOrgId().trim());
        }
        if(StringUtils.isNotBlank(userInfoDto.getQueryStr())){
            queryMap.put("queryStr",userInfoDto.getQueryStr());
        }
        Page<UserInfoVo> page= PageHelper.startPage(userInfoDto.getPageNum(),userInfoDto.getPageSize());
        List<UserInfo> userInfoList= userInfoMapper.selecUsertList(queryMap);
        if(userInfoList.size()>0){
            userInfoVoList=userInfoList.stream().map(
                    e-> JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e), UserInfoVo.class)
            ).collect(Collectors.toList());
        }
        userInfoVoList.stream().forEach(
                e->{
                    StringBuffer sb=new StringBuffer();
                    List<Group> list=groupMapper.selectRefUserList(e.getUserId());
                    list.stream().forEach(g->{
                        sb.append(g.getName()).append(",");
                    });
                    if(sb.length()>0){
                        e.setGroups(sb.toString().substring(0,sb.length()-1));
                    }
                }
        );
        //Page<TBaseDictVo> page=PageHelper.startPage(tBaseDictDto.getPageNum(),  tBaseDictDto.getPageSize());
        //int total= (int) page.getTotal();
        PageInfo<UserInfoVo> pageInfo = new PageInfo<>(userInfoVoList);
        pageInfo.setPages(page.getPages());//总页数
        pageInfo.setTotal(page.getTotal());//总条数
        jsonMap.put("data",userInfoVoList);//数据结果
        jsonMap.put("total", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }

    @Override
    public int insertAndUpdateUser(UserInfoDto userInfoDto,Boolean isOrgUser) {
        //validateFiled(orgDto);
        LocalAssert.notNull(userInfoDto, "请求数据，不能为空！");
        LocalAssert.notBlank(userInfoDto.getUserNo(), "账号不能为空！");
        LocalAssert.notBlank(userInfoDto.getMobilePhone(), "手机号不能为空！");
        LocalAssert.notBlank(userInfoDto.getUserName(), "姓名不能为空！");
        LocalAssert.notNull(userInfoDto.getFstate(), "状态不能为空！");
        if(isOrgUser){
            LocalAssert.notBlank(userInfoDto.getOrgId(), "机构不能为空！");
        }
        UserInfo userInfo=new UserInfo();
        BeanUtils.copyProperties(userInfoDto,userInfo);
        if(isOrgUser){
            userInfo.setOrgId(Long.parseLong(userInfoDto.getOrgId().trim()));
        }
        if(StringUtils.isBlank(userInfoDto.getUserId())){
            int count = this.selectCount(new QueryWrapper<UserInfo>().eq("USER_NO", userInfoDto.getUserNo()));
            if (count > 0) {
                throw new MediotException("账号已经存在");
            }

            int mobileCount = this.selectCount(new QueryWrapper<UserInfo>().eq("MOBILE_PHONE", userInfoDto.getMobilePhone()));
            if (mobileCount > 0) {
                throw new MediotException("手机号已经存在");
            }
            //设置初始密码
            userInfo.setPwd(MD5Util.getMd5Str(CustomConst.DEFAULT_PASSWORD));
            userInfo.setCreateTime(new Date());
            return userInfoMapper.insert(userInfo);
        }else {
            userInfo.setUserId(Long.parseLong(userInfoDto.getUserId().replaceAll(",","").trim()));
            userInfo.setModifyTime(new Date());
            return userInfoMapper.updateById(userInfo);
        }
    }

    @Override
    public void updatePwd(UserInfo userInfo) {
        LocalAssert.notNull(userInfo.getUserId(), "用户id不能为空！");
        UpdateWrapper updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("user_id",userInfo.getUserId());
        if(!StringUtils.isNotBlank(userInfo.getPwd())){
            userInfo.setPwd(MD5Util.getMd5Str(CustomConst.DEFAULT_PASSWORD));
        }
        userInfo.setModifyTime(new Date());
        userInfoMapper.update(userInfo,updateWrapper);
    }

    @Override
    public UserInfo findUserInfoByParameter(UserInfoDto userInfoDto) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDto, userInfo);
        QueryWrapper queryWrapper = new QueryWrapper<UserInfo>();
        if (StringUtils.isNotBlank(userInfo.getUserNo())) {
            queryWrapper.eq("USER_NO", userInfo.getUserNo());
        }
        if (StringUtils.isNotBlank(userInfo.getPwd())) {
            queryWrapper.eq("PWD", userInfo.getPwd());
        }
        if(userInfo.getUserId()!=null){
            if (StringUtils.isNotBlank(String.valueOf(userInfo.getUserId()))) {
                queryWrapper.eq("USER_ID", userInfo.getUserId());
            }
        }
        if (StringUtils.isNotBlank(userInfo.getMobilePhone())) {
            queryWrapper.eq("mobile_phone", userInfo.getMobilePhone());
        }
        if (StringUtils.isNotBlank(userInfo.getEmail())) {
            queryWrapper.eq("email", userInfo.getEmail());
        }
        if(userInfo.getOrgId()!=null){
            if (StringUtils.isNotBlank(String.valueOf(userInfo.getOrgId()))) {
                queryWrapper.eq("org_id", userInfo.getOrgId());
            }
        }
        List<UserInfo> baseUserInfo = userInfoMapper.selectList(queryWrapper);
        if (baseUserInfo == null || baseUserInfo.size() == 0){
            return null;
        }else{
            return baseUserInfo.get(0);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserInfoDto(UserInfoDto userInfoDto) {
        insertAndUpdateUser(userInfoDto,true);
        if(userInfoDto.getSystemCodes()!=null&&userInfoDto.getOrgId()!=null){
           if(StringUtils.isNotBlank(userInfoDto.getSystemCodes())&&StringUtils.isNotBlank(userInfoDto.getOrgId())){
               String[] codeArr=userInfoDto.getSystemCodes().split(",");
               List<OrgRefSystem> orgRefSystemList=Lists.newArrayList();
               for(int i=0;i<codeArr.length;i++){
                   OrgRefSystem orgRefSystem=new OrgRefSystem();
                   orgRefSystem.setOrgId(userInfoDto.getOrgId());
                   orgRefSystem.setSystemCode(codeArr[i]);
                   orgRefSystemList.add(orgRefSystem);
               }
               QueryWrapper queryWrapper = new QueryWrapper<OrgRefSystem>();
               queryWrapper.eq("ORG_ID",userInfoDto.getOrgId());
               List<OrgRefSystem> tempList=orgRefSystemMapper.selectList(queryWrapper);
               if(tempList.size()>0){
                   List<Long> ids=tempList.stream().map(OrgRefSystem::getRefId).collect(Collectors.toList());
                   orgRefSystemMapper.deleteBatchIds(ids);
               }
               orgRefSystemMapper.batchInsertRelation(orgRefSystemList);
           }
        }
    }

    @Override
    public void modifyPwd(UserInfo userInfo) {
        LocalAssert.notNull(userInfo.getUserId(), "用户id不能为空！");
        LocalAssert.notNull(userInfo.getNewPwd(), "新密码不能为空！");
        LocalAssert.notNull(userInfo.getOldPwd(), "旧密码不能为空！");
        UpdateWrapper updateWrapper =new UpdateWrapper<>();
        updateWrapper.eq("user_id",userInfo.getUserId());
        UserInfo u=userInfoMapper.selectOne(updateWrapper);
        if(!u.getPwd().equals(userInfo.getOldPwd())){
            throw new MediotException("错误的旧密码");
        }
        userInfo.setPwd(userInfo.getNewPwd());
        userInfo.setModifyTime(new Date());
        userInfoMapper.update(userInfo,updateWrapper);
    }
}
