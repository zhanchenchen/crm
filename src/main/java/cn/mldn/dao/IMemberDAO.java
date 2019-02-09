package cn.mldn.dao;

import java.util.List;

import cn.mldn.vo.Member;

public interface IMemberDAO {
	public boolean doCreate(Member vo);
	public List<Member> findAllSplit(String column,String keyWord,Integer currentPage,Integer lineSize);
}
