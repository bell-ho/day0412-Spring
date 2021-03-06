package org.zerock.service.BoardServiceTests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVo;
import org.zerock.domain.Criteria;
import org.zerock.service.BoardService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {
	@Setter(onMethod_ = @Autowired)
	private BoardService service;

//	@Test
	public void testExist() {
		log.info(service);
		assertNotNull(service);
		System.out.println("객체주입됨");
	}

//	@Test
	public void testRegister() {

		BoardVo board = new BoardVo();
		board.setTitle("새로 작성하는 글");
		board.setContent("새로 작성하는 내용");
		board.setWriter("뉴비");

		service.register(board);

		log.info("생성된 게시물의 번호: " + board.getBno());
		System.out.println("생성된 게시물의 번호 생성된 게시물의 번호 생성된 게시물의 번호 생성된 게시물의 번호");
	}

	@Test
	public void testGetList() {
//		service.getList().forEach(board -> log.info(board));
		service.getList(new Criteria(2, 10)).forEach(board -> log.info(board));
	}

//	@Test
	public void testGet() {
		log.info(service.get(1L));
	}

//	@Test
	public void testeDelete() {
		log.info("remove result:" + service.remove(2L));
	}

//	@Test
	public void testUpdate() {
		BoardVo board = service.get(1L);

		if (board == null) {
			return;
		}
		board.setTitle("제목을 수정한다");
		log.info("modify result:" + service.modify(board));
	}
}
