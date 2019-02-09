package cn.mldn.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.mldn.service.IMemberService;
import cn.mldn.util.action.AbstractAction;
import cn.mldn.vo.Member;

@Controller
@Scope(scopeName="prototype")
@RequestMapping("/pages/member/*")
public class MemberAction extends AbstractAction{
	private Logger log=Logger.getLogger(MemberAction.class);
	@Resource
	private IMemberService memberService;
	@RequestMapping("add")
	public ModelAndView add(Member vo) throws Exception{
		log.info(vo);
		log.info("处理结果："+this.memberService.add(vo));
		return null;
	}
	@RequestMapping("split")
	public ModelAndView split(String col, String kw, Integer cp, Integer ls) throws Exception{
		log.info(col+","+kw+","+cp+","+ls);
		log.info("数据查询结果："+this.memberService.split(col,kw,cp,ls));
		return null;
	}
	@Override
	public String getFileUploadDir() {
		return null;
	}
	
}
