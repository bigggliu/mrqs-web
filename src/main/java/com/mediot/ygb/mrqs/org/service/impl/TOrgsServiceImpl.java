package com.mediot.ygb.mrqs.org.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mediot.ygb.mrqs.analysis.monitoringIndexManage.dao.TCheckOrgMapper;
import com.mediot.ygb.mrqs.common.core.exception.ValidationException;
import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.common.core.util.LocalAssert;
import com.mediot.ygb.mrqs.common.core.util.StringUtils;
import com.mediot.ygb.mrqs.common.enumcase.ResultCodeEnum;
import com.mediot.ygb.mrqs.common.excelListener.UploadExcelListener;
import com.mediot.ygb.mrqs.common.util.JsonUtil;
import com.mediot.ygb.mrqs.common.util.ResultUtil;
import com.mediot.ygb.mrqs.org.dao.TOrgsMapper;
import com.mediot.ygb.mrqs.org.dto.OrgDto;
import com.mediot.ygb.mrqs.org.dto.OrgExcelDto;
import com.mediot.ygb.mrqs.org.entity.TOrgsEntity;
import com.mediot.ygb.mrqs.org.enumcase.OrgLevelEnum;
import com.mediot.ygb.mrqs.org.enumcase.OrgTypeEnum;
import com.mediot.ygb.mrqs.org.service.TOrgsService;
import com.mediot.ygb.mrqs.org.vo.OrgVo;
import com.mediot.ygb.mrqs.system.user.dao.UserInfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 机构 服务实现类
 * </p>
 *
 *
 * @since
 */
@Service
public class TOrgsServiceImpl extends BaseServiceImpl<TOrgsMapper, TOrgsEntity> implements TOrgsService {

    @Autowired
    TOrgsMapper tOrgsMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    TCheckOrgMapper tCheckOrgMapper;


    public void validateFiled(OrgDto orgDto){
        LocalAssert.notNull(orgDto,"对象不能为空");
        //验证字段逻辑
        LocalAssert.notNull(orgDto.getOrgName(),"机构名称不能为空");
        //LocalAssert.notNull(orgDto.getOrgGrade(),"医院等级不能为空");
        //LocalAssert.notNull(orgDto.getOrgType(),"类别代码不能为空");
        //LocalAssert.notNull(orgDto.getDockingScheme(),"可选对接方案不能为空");
    };

    @Override
    public OrgVo selectOrgsById(String orgId) {
        TOrgsEntity tOrgsEntity=tOrgsMapper.selectById(orgId);
        OrgVo orgVo=new OrgVo();
        BeanUtils.copyProperties(tOrgsEntity,orgVo);
        return orgVo;
    }

    @Override
    public ResultUtil importOrgsFromExcel(MultipartFile uploadFile) {
        ResultUtil res=ResultUtil.build();
        try {
            EasyExcel.read(uploadFile.getInputStream(), OrgExcelDto.class, new UploadExcelListener(tOrgsMapper,res,this)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Map<String, Object> getTOrgsByParam(OrgDto orgDto) {
        QueryWrapper queryWrapper=new QueryWrapper();
        List<TOrgsEntity> teList=Lists.newArrayList();
        if(StringUtils.isNotBlank(orgDto.getQueryStr())){
            queryWrapper.like("AREA",orgDto.getQueryStr());
            queryWrapper.or();
            queryWrapper.like("ORG_NAME",orgDto.getQueryStr());
        } else {
            if(!StringUtils.isNotBlank(orgDto.getArea())){
                queryWrapper.select("distinct AREA");
            }else {
                queryWrapper.eq("AREA",orgDto.getArea());
            }
        }
        teList=tOrgsMapper.selectList(queryWrapper);
        teList.removeAll(Collections.singleton(null));
        List<OrgVo> orgVoList = teList.stream().map(e -> JsonUtil.
                getJsonToBean(JsonUtil.getBeanToJson(e), OrgVo.class)).
                collect(Collectors.toList());
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("data",orgVoList);//数据结果
        return jsonMap;
    }

    @Override
    public Boolean deleteOrgById(String orgId) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("ORG_ID",orgId);
        List uList=userInfoMapper.selectList(queryWrapper);
        if(uList.size()>0){
            throw new ValidationException("删除失败，该机构有关联的机构管理员！");
        }else {
            List cList=tCheckOrgMapper.selectList(queryWrapper);
            if(cList.size()>0){
                throw new ValidationException("删除失败，该机构有关联的指标数据！");
            }
            return this.deleteById(orgId);
        }
    }

    public ResultUtil validateExcelColumns(List<TOrgsEntity> totalExcelColumnsList,ResultUtil res){
        List<TOrgsEntity> errorList=Lists.newArrayList();
        List<TOrgsEntity> correctList=Lists.newArrayList();
        Boolean isError=false;
        if(totalExcelColumnsList.size(  )==0){
            return res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).data(errorList).msg("excel文件不能为空！");
        }
        for(int i=0;i<totalExcelColumnsList.size();i++){
            StringBuffer sb=new StringBuffer();
            sb.append("第"+i+"条");
            if(!StringUtils.isNotBlank(totalExcelColumnsList.get(i).getOrgName())){
                isError=true;
                sb.append("机构名不能为空,");
            }else if(!StringUtils.isNotBlank(totalExcelColumnsList.get(i).getOrgCode())){
                sb.append("机构代码不能为空,");
                isError=true;
            }else if(true){
                isError=true;
            }
            if(isError){
                errorList.add(totalExcelColumnsList.get(i));
            }else {
                correctList.add(totalExcelColumnsList.get(i));
            }
            isError=false;
        }
        if(errorList.size()>0){
            res.code(ResultCodeEnum.getCode(ResultCodeEnum.FAIL)).data(errorList).msg("存在错误信息,请修正后再导入！");
            return  res;
        }
        return res;
    }

    @Override
    public int insertAndUpdateOrg(OrgDto orgDto) {
        TOrgsEntity tOrgsEntity=new TOrgsEntity();
        validateFiled(orgDto);
        BeanUtils.copyProperties(orgDto,tOrgsEntity);
        if(orgDto.getOrgId()==null){
            return tOrgsMapper.insert(tOrgsEntity);
        }else {
            return tOrgsMapper.updateById(tOrgsEntity);
        }
    }

    @Override
    public int batchDelOrgs(String orgIds) {
        LocalAssert.notNull(orgIds,"机构ids不能为空");
        String ids[]=orgIds.split(",");
        List<String> orgIdsList= Stream.of(ids).collect(Collectors.toList());
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.in("ORG_ID",orgIdsList);
        int n=userInfoMapper.selectCount(queryWrapper);
        if(n>0){
            throw new ValidationException("删除失败，该批次机构里有关联的机构管理员！");
        }else {
            int i=tCheckOrgMapper.selectCount(queryWrapper);
            if(i>0){
                throw new ValidationException("删除失败，该批次机构里有关联的指标数据！");
            }
            return tOrgsMapper.deleteBatchIds(orgIdsList);
        }
    }

    @Override
    public void exportOrgExcelData(HttpServletResponse response,String orgIds) {
        String fileName;
        StringBuffer stringBuffer = new StringBuffer();
        List<OrgExcelDto> list = Lists.newArrayList();
        if(StringUtils.isEmpty(orgIds)){
            fileName = "机构导入模板.xlsx";
            stringBuffer.append("注意事项:");
            // "1.组件名称、型号、规格、单位(最小)，必填。\n" +
            // "2.产品材质、骨科产品属性、包装材质、条形码、REF，非必填。";
        }else {
            fileName = "机构列表数据.xlsx";
            String ids[]=orgIds.split(",");
            List<String> orgIdsList= Stream.of(ids).collect(Collectors.toList());
            List<TOrgsEntity> orgList=tOrgsMapper.selectBatchIds(orgIdsList);
            orgList.stream().forEach(e->{
                e.setOrgType(OrgTypeEnum.getOrgTypeLabel(e.getOrgType()));
                e.setOrgGrade(OrgLevelEnum.getOrgLevelLabel(e.getOrgGrade()));
            });
            list=orgList.stream().map(e->JsonUtil.getJsonToBean(JsonUtil.getBeanToJson(e),OrgExcelDto.class)).collect(Collectors.toList());
        }
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fn = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fn);
            EasyExcel.write(response.getOutputStream(), OrgExcelDto.class).sheet("数据").doWrite(list);
            //new ExportExcel(stringBuffer.toString(), OrgExcelDto.class, 2).setDataList(list).write(response, fileName).dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> findtOrgByParam(OrgDto orgDto) {
        LocalAssert.notNull(orgDto.getPageNum(),"pageNum不能为空");
        LocalAssert.notNull(orgDto.getPageSize(),"pageSize不能为空");
        QueryWrapper queryWrapper=new QueryWrapper();
        //queryWrapper.eq("1",1);
        if(StringUtils.isNotBlank(orgDto.getOrgName())){
            queryWrapper.like("ORG_NAME",orgDto.getOrgName());
        }
        queryWrapper.orderByDesc("CREATE_TIME");
        Page page=PageHelper.startPage(orgDto.getPageNum(),orgDto.getPageSize());
        List<TOrgsEntity> teList=tOrgsMapper.selectList(queryWrapper);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        List<OrgVo> orgVoList = teList.stream().map(e -> JsonUtil.
        getJsonToBean(JsonUtil.getBeanToJson(e), OrgVo.class)).
        collect(Collectors.toList());
        orgVoList.stream().forEach(e->{
            Map<String,Object> map= Maps.newHashMap();
            map.put("orgId",e.getOrgId());
            TOrgsEntity pt=tOrgsMapper.selectPOInfoByOrgId(map);
            if(pt!=null){
                e.setPid(pt.getPid());
                e.setPname(pt.getPname());
            }
        });
        PageInfo<OrgVo> pageInfo = new PageInfo<>(orgVoList);
        pageInfo.setPages(page.getPages());//总页数
        pageInfo.setTotal(page.getTotal());//总条数
        jsonMap.put("data",orgVoList);//数据结果
        jsonMap.put("total", pageInfo.getTotal());//获取数据总数
        jsonMap.put("pageSize", pageInfo.getPageSize());//获取长度
        jsonMap.put("pageNum", pageInfo.getPageNum());//获取当前页数
        return jsonMap;
    }

}
