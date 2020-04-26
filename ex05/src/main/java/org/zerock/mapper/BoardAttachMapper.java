package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardAttachVo;

public interface BoardAttachMapper {
	
	public void insert(BoardAttachVo vo);
	
	public void delete(String uuid);
	
	public List<BoardAttachVo>findByBno(Long bno);
	
	public void deleteAll(Long bno);
	
	public List<BoardAttachVo> getOldFiles();
}
