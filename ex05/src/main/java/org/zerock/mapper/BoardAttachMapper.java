package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardAttachVo;

public interface BoardAttachMapper {
	
	public void insert(BoardAttachMapper vo);
	
	public void delete(String uuid);
	
	public List<BoardAttachVo>findByBno(Long bno);
}
