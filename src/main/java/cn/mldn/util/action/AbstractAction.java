package cn.mldn.util.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import cn.mldn.util.file.UploadFileUtil;

public abstract class AbstractAction {
	/**
	 * 生成图片名称
	 * 
	 * @param photo
	 * @return
	 */
	public String createFileName(MultipartFile photo) {
		if (photo.isEmpty()) { // 没有文件上传
			return "nophoto.png"; // 默认没有文件
		} else { // 需要自己生成一个文件
			return UploadFileUtil.createFileName(photo.getContentType());
		}
	}

	/**
	 * 进行文件的保存处理
	 * @param photo
	 */
	public boolean saveFile(MultipartFile photo, String fileName, HttpServletRequest request) { // 保存上传的图片名称
		if (!photo.isEmpty()) {
			String filePath = request.getServletContext().getRealPath(this.getFileUploadDir()) + fileName;
			try {
				return UploadFileUtil.save(photo.getInputStream(), new File(filePath));
			} catch (IOException e) {
				e.printStackTrace();
				return false ;
			}
		} else {
			return false ;
		}
	}

	/**
	 * 保存上传文件路径
	 * @return
	 */
	public abstract String getFileUploadDir();

	@Resource
	private MessageSource msgSource; // 表示此对象直接引用配置好的类对象（根据类型匹配）

	/**
	 * 根据指定的key的信息进行资源数据的读取控制
	 * 
	 * @param msgKey
	 *            表示要读取的资源文件的key的内容
	 * @return 表示资源对应的内容
	 */
	public String getValue(String msgKey, Object... args) {
		return this.msgSource.getMessage(msgKey, args, Locale.getDefault());
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) { // 方法名称自己随便写
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 本方法的处理指的是追加有一个自定义的转换编辑器，如果遇见的操作目标类型为java.util.Date类
		// 则使用定义好的SimpleDateFormat类来进行格式化处理，并且允许此参数的内容为空
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(sdf, true));
	}
}
