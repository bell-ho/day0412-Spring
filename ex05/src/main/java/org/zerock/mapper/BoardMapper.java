package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.BoardVo;
import org.zerock.domain.Criteria;

public interface BoardMapper {
//	@Select("select * from tbl_board02 where bno > 0")
	public List<BoardVo>getList();
	
	public List<BoardVo> getListWithPaging(Criteria cri);
	
	public void insert(BoardVo board);
	
	public Integer insertSelectKey(BoardVo board);
	
	public BoardVo read(Long bno);
	
	public int delete(Long bno);
	
	public int update(BoardVo board);
	
	public int getTotalCount(Criteria cri);
	
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount")int amount);
}
