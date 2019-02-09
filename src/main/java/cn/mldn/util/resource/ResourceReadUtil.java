package cn.mldn.util.resource;

import java.lang.reflect.Method;

import org.springframework.web.method.HandlerMethod;

public class ResourceReadUtil {
	/**
	 * 读取错误也配置信息
	 * @param handlerMethod
	 * @return
	 */
	public static String getErrorPageValue(HandlerMethod handlerMethod) {
		String pageKey=handlerMethod.getBean().getClass().getSimpleName()+"."+handlerMethod.getMethod().getName()+".error.page";
		String pageUrl=getValue(handlerMethod,pageKey);
		if(pageUrl==null) {
			pageUrl=getValue(handlerMethod,"error.page");
		}
		return pageUrl;
	}
	/**
	 * 读取资源信息
	 * @param handlerMethod
	 * @param pageKey
	 * @return
	 */
	public static String getValue(HandlerMethod handlerMethod,String pageKey) {
		try {
			Method getValueMethod=handlerMethod.getBean().getClass().getMethod("getValue", String.class,Object[].class);
			return (String) getValueMethod.invoke(handlerMethod.getBean(), pageKey,null);
		} catch (Exception e) {
			return null;
		}
		
	}
}
