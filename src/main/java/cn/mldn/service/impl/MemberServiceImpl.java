package cn.mldn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.mldn.dao.IMemberDAO;
import cn.mldn.service.IMemberService;
import cn.mldn.vo.Member;
@Service
public class MemberServiceImpl implements IMemberService {
	@Resource
	private IMemberDAO memberDAO;
	@Override
	public boolean add(Member vo) throws Exception {
		return this.memberDAO.doCreate(vo);
	}

	@Override
	public List<Member> split(String column, String keyWord, Integer currentPage, Integer lineSize) throws Exception {
		return this.memberDAO.findAllSplit(column, keyWord, currentPage, lineSize);
	}

}
