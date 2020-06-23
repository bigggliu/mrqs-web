package com.mediot.ygb.mrqs.system.menu.service.impl;

import com.mediot.ygb.mrqs.common.core.service.impl.BaseServiceImpl;
import com.mediot.ygb.mrqs.system.menu.dao.MenuMapper;
import com.mediot.ygb.mrqs.system.menu.service.MenuService;
import com.mediot.ygb.mrqs.system.menu.entity.Menu;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统菜单关系表 服务实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements MenuService {
}
