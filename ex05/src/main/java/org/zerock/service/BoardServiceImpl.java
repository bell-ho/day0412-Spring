package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVo;
import org.zerock.domain.BoardVo;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
//@AllArgsContstructor는 모든 파라미터를 이용하는 생성자를 만든다
public class BoardServiceImpl implements BoardService {
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ =@Autowired )
	private BoardAttachMapper attachMapper;
	

	@Override
	public BoardVo get(Long bno) {
		// TODO Auto-generated method stub

		log.info("get........." + bno);
		return mapper.read(bno);
	}

	@Transactional
	@Override
	public void register(BoardVo board) {

		log.info("register......" + board);

		mapper.insertSelectKey(board);

		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}

		board.getAttachList().forEach(attach -> {

			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	
	@Override
	public List<BoardVo> getList(Criteria cri) {

		log.info("겟 리스트 윗 크리테리아 " + cri);

		return mapper.getListWithPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		// TODO Auto-generated method stub
		
//		BoardService의 getTotal( )에 굳이 Criteria는 파라미터로 전달된 필요가 없기는 하지만，
//		목록과 전체 데이터 개수는 항상 같이 동작하는 경우가 많기 때문에 파라미터로 지정 합니다.
		log.info("get total count");
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<BoardAttachVo> getAttachList(Long bno) {
		
		log.info("겟 어테치 리스트 바이 bno" +bno);
		
		return attachMapper.findByBno(bno);
	}	

	@Transactional
	@Override
	public boolean remove(Long bno) {
		
		log.info("삭제" +bno);
		
		attachMapper.deleteAll(bno);
		
		return mapper.delete(bno) ==1;
	}
	
	@Transactional
	@Override
	public boolean modify(BoardVo board) {
		
		log.info("수정"+board);
		
		attachMapper.deleteAll(board.getBno());
		
		boolean modifyResult = mapper.update(board) ==1;
		
		if(modifyResult && board.getAttachList() != null && board.getAttachList().size() >0 ) {
			
			board.getAttachList().forEach(attach->{
				
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
				
			});
			
		}
		return modifyResult;
	}
}
