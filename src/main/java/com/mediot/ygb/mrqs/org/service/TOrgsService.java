package com.mediot.ygb.mrqs.org.service;



import com.mediot.ygb.mrqs.common.core.service.BaseService;
import com.mediot.ygb.mrqs.common.util.ResultUtil;
import com.mediot.ygb.mrqs.org.dto.OrgDto;
import com.mediot.ygb.mrqs.org.entity.TOrgsEntity;
import com.mediot.ygb.mrqs.org.vo.OrgVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 诊断字典表 服务类
 * </p>
 *
 *
 * @since
 */
public interface TOrgsService extends BaseService<TOrgsEntity> {

    int insertAndUpdateOrg(OrgDto orgDto);

    int batchDelOrgs(String orgIds);

    void exportOrgExcelData(HttpServletResponse response, String orgIds);

    Map<String, Object> findtOrgByParam(OrgDto orgDto);

    OrgVo selectOrgsById(String orgId);

    ResultUtil importOrgsFromExcel(MultipartFile uploadFile);

    Map<String, Object> getTOrgsByParam(OrgDto orgDto);

    Boolean deleteOrgById(String orgId);
}
