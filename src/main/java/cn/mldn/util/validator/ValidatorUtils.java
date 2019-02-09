package cn.mldn.util.validator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.mldn.util.resource.ResourceReadUtil;

public class ValidatorUtils {
	private static Logger log=Logger.getLogger(ValidatorUtils.class);
	public static Map<String,String> validate(HttpServletRequest request,HandlerMethod handlerMethod){
		String validationKey=handlerMethod.getBeanType().getSimpleName()+"."+handlerMethod.getMethod().getName()+".rules";
		Logger.getLogger(ValidatorUtils.class).info(validationKey);
		Map<String,String> errors=new HashMap<String,String>();//保存所有验证错误信息
		//log.info("prehandler***"+validationKey);
		try {
			Method getValueMethod=handlerMethod.getBean().getClass().getMethod("getValue", String.class,Object[].class);
			try {
				String validationValue=(String) getValueMethod.invoke(handlerMethod.getBean(), validationKey,null);
				if(validationValue!=null) {
					//log.info("【prehandler】validationValue"+validationValue);
					String result[]=validationValue.split("\\|");
					for(int x=0;x<result.length;x++) {
						String temp[]=result[x].split(":");
						String paramName=temp[0];//参数名称
						String paramRule=temp[1];//验证规则
						String paramValue=request.getParameter(paramName);
						switch(paramRule) {
							case "string":{
								if(!ValidateRuleUtil.isString(paramValue)) {
									String msg=(String) getValueMethod.invoke(handlerMethod.getBean(), "validation.string.msg",null);
									errors.put(paramName, msg);
								}
								break;
							}
							case "int":{
								if(!ValidateRuleUtil.isInt(paramValue)) {
									String msg=(String) getValueMethod.invoke(handlerMethod.getBean(), "validation.int.msg",null);
									errors.put(paramName, msg);
								}
								break;
							}
							case "double":{
								if(!ValidateRuleUtil.isDouble(paramValue)) {
									String msg=(String) getValueMethod.invoke(handlerMethod.getBean(), "validation.double.msg",null);
									errors.put(paramName, msg);
								}
								break;
							}
							case "date":{
								if(!ValidateRuleUtil.isDate(paramValue)) {
									String msg=(String) getValueMethod.invoke(handlerMethod.getBean(), "validation.date.msg",null);
									errors.put(paramName, msg);
								}
								break;
							}
							case "datetime":{
								if(!ValidateRuleUtil.isDateTime(paramValue)) {
									String msg=(String) getValueMethod.invoke(handlerMethod.getBean(), "validation.datetime.msg",null);
									errors.put(paramName, msg);
								}
								break;
							}
							case "rand":{
								if(!ValidateRuleUtil.idRand(request,paramValue)) {
									String msg=(String) getValueMethod.invoke(handlerMethod.getBean(), "validation.rand.msg",null);
									errors.put(paramName, msg);
								}
								break;
							}
						}
					}
				}
			}catch(Exception e) {
				log.warn("没有验证规则！！！");
			}
		} catch (Exception e) {
			log.warn("出错啦！！！");
		}
		if(errors.size()==0) {
			MultipartResolver mr=new CommonsMultipartResolver();//判断对于接收文件的接收操作
			if(mr.isMultipart(request)) {//表示当前有上传文件
				String mimeKey=handlerMethod.getBean().getClass().getSimpleName()+"."+handlerMethod.getMethod().getName()+".mime.rules";
				String mimeValue=ResourceReadUtil.getValue(handlerMethod,mimeKey);
				if(mimeValue==null) {
					mimeValue=ResourceReadUtil.getValue(handlerMethod, "mime.rules");
				}
				String mimeResult[]=mimeValue.split("\\|");
				MultipartRequest mreq=(MultipartRequest) request;//对于request的封装
				Map<String,MultipartFile> fileMap=mreq.getFileMap();
				if(fileMap.size()>0) {//有文件上传
					Iterator<Map.Entry<String,MultipartFile>> iter=fileMap.entrySet().iterator();
					while(iter.hasNext()) {
						Map.Entry<String, MultipartFile> me=iter.next();
						if(me.getValue().getSize()>0) {//有文件上传
							if(!ValidateRuleUtil.isMime(mimeResult, me.getValue().getContentType())) {//没有验证通过
								errors.put("file", ResourceReadUtil.getValue(handlerMethod, "validation.mime.msg"));
							}
						}
					}
				}
			}
		}
		return errors;
	}
}
