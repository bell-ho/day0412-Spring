package org.zerock.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
						"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class BoardControllerTests {
	
	@Setter(onMethod_ =@Autowired )
	private WebApplicationContext ctx;
	//@WebAppConfiguration은 Servlet의 ServletContext를 이용하가 위해서인데， 
	//스프링에서는 WebApplicationContext라는 존재를 이용하기 위해서 입니다.
	private MockMvc mockMvc;
	//가짜 MVC
	
	
	@Before
	public void setup() {
		System.out.println("여기까지 실행됨1");
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
//	@Test
	public void testList() throws Exception{
		System.out.println("여기까지 실행됨2");
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
				//MockMvcRequestBuilders라는 존재를 이용해서 GET 방식 호출을 합니다. 
				.andReturn()
				.getModelAndView()
				.getModelMap()
				);
	}
//	@Test
	public void testRegister()throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "테스트 새글 제목")
				.param("content", "테스트 새글 내용")
				.param("writer", "user00")
		).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	
//	@Test
	public void testGet() throws Exception{
		log.info(mockMvc.perform(MockMvcRequestBuilders
				.get("/board/get")
				.param("bno", "81"))
				.andReturn()
				.getModelAndView().getModelMap());
	}
//	@Test
	public void testModify()throws Exception{
		String resultPage = mockMvc
				.perform(MockMvcRequestBuilders.post("/board/modify")
				.param("bno", "1")
				.param("title","수정된 테스트 새글제목")
				.param("content","수정된 테스트 새글내용")
				.param("writer","user00"))
				.andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
//	@Test
	public void testRemove()throws Exception{
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
				.param("bno", "25")
				).andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
//	@Test
	public void testListPage() throws Exception{
		log.info(mockMvc.perform(
				MockMvcRequestBuilders.get("/board/list")
				.param("pageNum", "2")
				.param("amount", "50"))
				.andReturn().getModelAndView().getModelMap());
	}
}
