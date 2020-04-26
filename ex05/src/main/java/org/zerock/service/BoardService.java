package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVo;
import org.zerock.domain.BoardVo;
import org.zerock.domain.Criteria;

public interface BoardService {

	public void register(BoardVo board);

	public BoardVo get(Long bno); // 특정 게시물을 가져옴

	public boolean modify(BoardVo board);

	public boolean remove(Long bno);

	public List<BoardVo> getList(Criteria cri);
	
	public int getTotal(Criteria cri);
	
	public List<BoardAttachVo> getAttachList(Long bno);

}
