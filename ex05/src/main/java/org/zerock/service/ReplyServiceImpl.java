package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVo;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Setter(onMethod_ =@Autowired )
	private BoardMapper boardMapper;
	
	@Transactional
	@Override
	public int register(ReplyVo vo) {
		// TODO Auto-generated method stub
		
		log.info("register......"+vo);
		
		return mapper.insert(vo);
	}

	@Override
	public ReplyVo get(Long rno) {
		// TODO Auto-generated method stub
		
		log.info("get....."+rno);
		
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyVo vo) {
		// TODO Auto-generated method stub
		
		log.info("modify......"+vo);
		
		return mapper.update(vo);
	}

	@Transactional
	@Override
	public int remove(Long rno) {
		// TODO Auto-generated method stub
		
		log.info("remove....."+rno);
		
		return mapper.delete(rno);
	}

	@Override
	public List<ReplyVo> getList(Criteria cri, Long bno) {
		// TODO Auto-generated method stub
		
		log.info("get Reply List of a board "+bno);
		
		return mapper.getListWithPaging(cri, bno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {
		// TODO Auto-generated method stub
		return new ReplyPageDTO(
				mapper.getCountByBno(bno),
				mapper.getListWithPaging(cri, bno));
	}
	
}
