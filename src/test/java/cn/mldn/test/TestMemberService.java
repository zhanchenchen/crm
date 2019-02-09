package cn.mldn.test;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.mldn.service.IMemberService;
import cn.mldn.vo.Member;
import junit.framework.TestCase;

public class TestMemberService {
	private static Random rand=new Random();
	private static ApplicationContext context;
	static {
		context=new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	private IMemberService memberService=context.getBean("memberServiceImpl", IMemberService.class);
	@Test
	public void testAdd() throws Exception{
		Member vo=new Member();
		int idx=rand.nextInt(10000);
		vo.setMid("mldn"+idx);
		vo.setName("小张同学-"+idx);
		vo.setAge(18);
		vo.setSalary(1.1);
		vo.setBirthday(new Date());
		vo.setNote("好同学!");
		TestCase.assertTrue(memberService.add(vo)); 
	}
	@Test
	public void testSplit() throws Exception{
		List<Member> all=this.memberService.split(null, null, 1, 3);
		Logger.getLogger(TestMemberService.class).info(all);
		TestCase.assertTrue(all.size()>0);
	}
}
