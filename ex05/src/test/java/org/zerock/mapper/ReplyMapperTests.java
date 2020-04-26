package org.zerock.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVo;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	
	private Long[] bnoArr = {793901L,793902L,793903L,793904L,793905L};
	//5개를 넣으니까 됨
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
//	@Test
	public void tesetMapper() {
		log.info(mapper);
	}
	
//	@Test
	public void testCreate() {
		
		IntStream.rangeClosed(1, 10).forEach(i ->{

			ReplyVo vo = new ReplyVo();
			
			//게시물번호
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글테스트" + i);
			vo.setReplyer("replyer" + i);
			
			mapper.insert(vo);
			
		});
		
	}
	
//	@Test
	public void testRead() {
		Long targetRno = 181L;
		
		ReplyVo vo = mapper.read(targetRno);
		
		log.info(vo);
	}
	
//	@Test
	public void testDelete() {
		
		Long targetRno = 181L;
		
		mapper.delete(targetRno);
	}
	
//	@Test
	public void testUpdate() {
		Long targetRno = 184L;
		
		ReplyVo vo = mapper.read(targetRno);
		
		vo.setReply("Update 리플");
		
		int count = mapper.update(vo);
		
		log.info("UPDATE COUNT: "+count);
	}
//	@Test
	public void testList() {
		Criteria cri = new Criteria();
		
		List<ReplyVo>replies = mapper.getListWithPaging(cri, bnoArr[0]);
		
		replies.forEach(reply-> log.info(reply));
	}
	@Test
	public void testList2() {
		Criteria cri = new Criteria(2,10);
		
		List<ReplyVo>replies = mapper.getListWithPaging(cri, 1278094L);
		
		replies.forEach(reply->log.info(reply));
	}
}
