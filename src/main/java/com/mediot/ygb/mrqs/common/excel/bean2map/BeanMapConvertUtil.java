/**
 * 
 */
package com.mediot.ygb.mrqs.common.excel.bean2map;

/**  
* <p>Description: </p>  
*   2018年10月8日
*  @version 1.0
* @since   JDK 1.8
*/


import com.google.common.collect.Lists;
import com.mediot.ygb.mrqs.common.excel.mapper.JsonMapper;
import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BeanMapConvertUtil {
	
	
	private static Logger logger = Logger.getLogger(BeanMapConvertUtil.class);

	

	public  static <T> List<Map<String, Object>> beanToMap(List<T> list,Class<T> clazz) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		try {
			for(T t : list){
				Map<String, Object> map = new HashMap<String, Object>();
				map =  JsonMapper.toMapString(t);
				mapList.add(map);
			}

		} catch (Exception e) {
			logger.error("transBean2Map Error " + e);
		}
		return mapList;
	}
	
	public static Map<String, Object> beanToMap(Object bean) {
		if (bean == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(bean);
					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;
	}

//	public static <T> T mapToBean(Map<String, Object> map, T bean) {
//		try {
//			BeanUtils.populate(bean, map);
//		} catch (IllegalAccessException | InvocationTargetException e) {
//			logger.error(e, e.fillInStackTrace());
//		}
//		return bean;
//	}
	
	
	
//	public static void main(String[] args) {
//		List<YpjxqStatics> list = Lists.newArrayList();
//		YpjxqStatics xx  = new YpjxqStatics();
//		xx.setApprovalNo("nn");
//
//		list.add(xx);
//
//		beanToMap(list,YpjxqStatics.class);
//
//
//	}
}
