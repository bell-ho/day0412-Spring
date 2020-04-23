package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVo;

public interface ReplyMapper {
	
	public int insert(ReplyVo vo);
	
	public ReplyVo read(Long bno);
	
	public int delete(Long bno);
	
	public int update(ReplyVo reply);
	
	public List<ReplyVo>getListWithPaging(
			@Param("cri")Criteria cri,
			@Param("bno")Long bno);
	
	public int getCountByBno(Long bno);
}
