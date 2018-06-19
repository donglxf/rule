package com.ht.rule.common.util;

import com.ht.rule.common.util.CollectionUtils;
import com.ht.rule.common.util.StringUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * <p>扩展ObjectUtils</p> 
 *
 * @author: dyb
 * @since: 2018年1月4日上午10:40:38
 * @version: 1.0
 */
public abstract class ObjectUtils extends org.springframework.util.ObjectUtils {

	/**
	 * 
	 * <p>判断对象是否为空</p> 
	 * @param object
	 * @return 
	 * @throws
	 */
	public static boolean isEmpty(Object object) {
		if (object == null)
			return true;
		if (String.class.isAssignableFrom(object.getClass())) {
			return StringUtils.isBlank((String) object);
		}
		if (isArray(object)) {
			return (((Object[]) object).length == 0);
		}
		if (CollectionUtils.isCollection(object.getClass())) {
			return CollectionUtils.isEmpty((Collection<?>) object);
		}
		if (CollectionUtils.isMap(object.getClass())) {
			return CollectionUtils.isEmpty((Map<?, ?>) object);
		}
		return false;
	}

	/**
	 * 
	 * <p>转换对象</p> 
	 * @param value
	 * @param defaultValue
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static <E> E transfer(Object value, E defaultValue) {
		E ret = defaultValue;
		if (null != value) {
			ret = (E) value;
		}
		return ret;
	}
	
	/**
	 * 为空时能得到默认非空值
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static <E> E getNotNull(E value, E defaultValue) {
		if (null == defaultValue) {
			throw new IllegalArgumentException("argument[defaultValue] cannot be null!");
		}
		E ret = defaultValue;
		if (null != value) {
			ret = value;
		}
		return ret;
	}
	
	public static boolean isNotEmpty(Object[] array) {
		return !isEmpty(array);
	}

	public static boolean isNotEmpty(Object object) {
		return !isEmpty(object);
	}


	private static final String JAVAP = "java.";
	private static final String JAVADATESTR = "java.util.Date";

	/**
	 * 获取利用反射获取类里面的值和名称
	 *
	 * @param obj
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Map<String, Object> objectToMap(Object obj,String pre,String... fillters ) throws IllegalAccessException {
		Map<String, Object> map = new HashMap<>();
		Class<?> clazz = obj.getClass();
		System.out.println(clazz);
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			boolean flag = false;
			for(int i = 0 ;i < fillters.length;i++){
				if(fieldName.equals(fillters[i])){
					flag = true;
				}
			}
			if(flag)
				continue;
			Object value = field.get(obj);
			map.put(pre+fieldName, value);
		}
		return map;
	}

	/**
	 * 利用递归调用将Object中的值全部进行获取
	 *
	 * @param timeFormatStr 格式化时间字符串默认<strong>2017-03-10 10:21</strong>
	 * @param obj           对象
	 * @param excludeFields 排除的属性
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Map<String, String> objectToMapString(String timeFormatStr, Object obj, String... excludeFields) throws IllegalAccessException {
		Map<String, String> map = new HashMap<>();

		if (excludeFields.length!=0){
			List<String> list = Arrays.asList(excludeFields);
			objectTransfer(timeFormatStr, obj, map, list);
		}else{
			objectTransfer(timeFormatStr, obj, map,null);
		}
		return map;
	}


	/**
	 * 递归调用函数
	 *
	 * @param obj           对象
	 * @param map           map
	 * @param excludeFields 对应参数
	 * @return
	 * @throws IllegalAccessException
	 */
	private static Map<String, String> objectTransfer(String timeFormatStr, Object obj, Map<String, String> map, List<String> excludeFields) throws IllegalAccessException {
		boolean isExclude=false;
		//默认字符串
		String formatStr = "YYYY-MM-dd HH:mm:ss";
		//设置格式化字符串
		if (timeFormatStr != null && !timeFormatStr.isEmpty()) {
			formatStr = timeFormatStr;
		}
		if (excludeFields!=null){
			isExclude=true;
		}
		Class<?> clazz = obj.getClass();
		//获取值
		for (Field field : clazz.getDeclaredFields()) {
			String fieldName = clazz.getSimpleName() + "." + field.getName();
			//判断是不是需要跳过某个属性
			if (isExclude&&excludeFields.contains(fieldName)){
				continue;
			}
			//设置属性可以被访问
			field.setAccessible(true);
			Object value = field.get(obj);
			Class<?> valueClass = value.getClass();
			if (valueClass.isPrimitive()) {
				map.put(fieldName, value.toString());

			} else if (valueClass.getName().contains(JAVAP)) {//判断是不是基本类型
				if (valueClass.getName().equals(JAVADATESTR)) {
					//格式化Date类型
					SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
					Date date = (Date) value;
					String dataStr = sdf.format(date);
					map.put(fieldName, dataStr);
				} else {
					map.put(fieldName, value.toString());
				}
			} else {
				objectTransfer(timeFormatStr, value, map,excludeFields);
			}
		}
		return map;
	}
}
