package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVo;

public interface ReplyService {
	
	public int register(ReplyVo vo);
	
	public ReplyVo get(Long rno);
	
	public int modify(ReplyVo vo);
	
	public int remove(Long rno);
	
	public List<ReplyVo> getList(Criteria cri,Long bno);
	
	public ReplyPageDTO getListPage(Criteria cri, Long bno);
}
