package cn.mldn.util.validator;

import javax.servlet.http.HttpServletRequest;

/**
 * 进行具体规则的判断
 * @author Administrator
 *
 */
public class ValidateRuleUtil {
	/**
	 * 验证传入的mime类型是否符合与当前的开发要求
	 * @param mimeRules 整体的验证规则
	 * @param mime 每一个上传文件的类型
	 * @return
	 */
	public static boolean isMime(String mimeRules[],String mime) {
		if(isString(mime)) {
			for(int x=0;x<mimeRules.length;x++) {
				if(mime.equals(mimeRules[x])) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 进行验证码检测
	 * @param request
	 * @param str
	 * @return
	 */
	public static boolean idRand(HttpServletRequest request,String str) {
		if(isString(str)) {
			String rand=(String) request.getSession().getAttribute("rand");
			if(isString(rand)) {
				return rand.equalsIgnoreCase(str);
			}
		}
		return false;
	}
	/**
	 * 验证是否为日期，格式为“yyyy-MM-dd HH:mm:ss”
	 * @param str
	 * @return
	 */
	public static boolean isDateTime(String str) {
		if(isString(str)) {
			return str.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		}
		return false;
	}
	/**
	 * 验证是否为日期，格式为“yyyy-MM-dd”
	 * @param str
	 * @return
	 */
	public static boolean isDate(String str) {
		if(isString(str)) {
			return str.matches("\\d{4}-\\d{2}-\\d{2}");
		}
		return false;
	}
	/**
	 * 验证数据是否为整数
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str) {
		if(isString(str)) {
			return str.matches("\\d+");
		}
		return false;
	}
	/**
	 * 验证是否为小数
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		if(isString(str)) {
			return str.matches("\\d+(\\.\\d+)?");
		}
		return false;
	}
	/**
	 * 验证内容是否为null或空字符串
	 * @param str
	 * @return
	 */
	public static boolean isString(String str) {
		if(str==null || "".equals(str)) {
			return false;
		}
		return true;
	}
}
