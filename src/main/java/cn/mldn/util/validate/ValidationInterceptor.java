package cn.mldn.util.validate;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.mldn.util.resource.ResourceReadUtil;
import cn.mldn.util.validator.ValidatorUtils;

public class ValidationInterceptor implements HandlerInterceptor {
	Logger log=Logger.getLogger(ValidationInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean flag=true;
		HandlerMethod handlerMethod=(HandlerMethod)handler;
		Map<String,String> errors=ValidatorUtils.validate(request, handlerMethod);
		Logger.getLogger(ValidationInterceptor.class).warn(errors.size());
		if(errors.size()>0) {//有错进行跳转
			Logger.getLogger(ValidationInterceptor.class).warn("出错啦！！！！！！！！！！！！！！！！！");
			request.setAttribute("errors", errors);
			flag=false;
			request.getRequestDispatcher(ResourceReadUtil.getErrorPageValue(handlerMethod)).forward(request, response);
		}
		return flag;
	}
}
