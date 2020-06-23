package com.mediot.ygb.mrqs.system.menu.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediot.ygb.mrqs.system.menu.entity.SystemMenu;
import com.mediot.ygb.mrqs.system.menu.vo.MenuVo;
import com.mediot.ygb.mrqs.system.menu.vo.SystemLabelVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 系统菜单关系表 Mapper 接口
 * </p>
 *
 * @author
 * @since
 */
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {
    /**
     * 查询系统下的菜单列表
     * @param pager
     * @return
     */
    List<MenuVo> querySystemMenuList(Map<String,Object> queryMap);

    /*List<Map<String, Object>> selectSubMenusByParentId(Pager pager);*/

    /**
     * 查找一级菜单(下拉框)
     * @param pager
     * @return
     */
    List<SystemLabelVo> searchTopMenuBySystemId(Map<String,Object> queryMap);

    /**
     * 验证“菜单名称”是否重复
     * @param pager
     * @return
     */
    int selectCountMenuName(Map<String, Object> queryMap);

    /**
     * 获取系统下菜单编码的最大值
     * @param systemCode
     * @param parentCode
     * @return
     */
    String getMenuCode(@Param("systemCode") String systemCode, @Param("parentCode") String parentCode);

    /**
     * 查询系统下同级别中的菜单编码是否重复
     * @param afterCode
     * @param systemCode
     * @param parentCode
     * @return
     */
    int selectMenuCodeCount(@Param("afterCode") String afterCode, @Param("systemCode") String systemCode, @Param("parentCode") String parentCode);

    /**
     * 查询某个“系统管理员”的菜单权限集合
     * @param userId
     * @param platformSystemId
     * @return Set<String>
     * @author
     *      */
    Set<String> findMeueCodesBygbsAdmin(@Param("userId") String userId, @Param("platformSystemId") String platformSystemId);

    List<MenuVo> querySystemMenuByArray(String[] systemCodeArr);
}
