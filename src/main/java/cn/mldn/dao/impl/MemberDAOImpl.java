package cn.mldn.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import cn.mldn.dao.IMemberDAO;
import cn.mldn.vo.Member;
@Repository
public class MemberDAOImpl implements IMemberDAO {
	@Resource
	private SqlSessionFactory factory;
	@Override
	public boolean doCreate(Member vo) {
		return factory.openSession().insert("cn.mldn.vo.mapping.MemberNS.doCreate", vo)>0;
	}

	@Override
	public List<Member> findAllSplit(String column, String keyWord, Integer currentPage, Integer lineSize) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("column", column);
		map.put("keyWord", keyWord);
		map.put("start", (currentPage-1)*lineSize);
		map.put("lineSize", lineSize);
		return factory.openSession().selectList("cn.mldn.vo.mapping.MemberNS.findAllSplit", map);
	}

}
