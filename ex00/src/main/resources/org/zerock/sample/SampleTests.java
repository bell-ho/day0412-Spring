package org.zerock.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
//테스트 코드가 스프링을 실행하는 역할을 할 것

@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
//위의 문자열로 되있는 주소를 이용하여 필요한 객체를 스프링내로 등록하게 됨

@Log4j
//로그 객체를 생성
public class SampleTests {
	@Setter(onMethod_ = @Autowired)
	private Restaurant restaurant;
	// @Autowired 때문에 restaurant를 주입하게됨 그래서 객체생성을 하게됨

	@Test
	// Junit의 테스트 대상

	public void testExit() {
		assertNotNull(restaurant);
		// assertNotNull 는 해당 매개변수가 null이 아니어야만 테스트 성공
		log.info(restaurant);
		log.info("====================");
		log.info(restaurant.getChef());

		// 이 테스트를 통해 알수 있는점
		// 1. 동작하는 과정에서 스프링 프레임워크가 작동됨
		// 2. 작동되는 과정에 객체들이 스프링에 등록됨
		// 3. 의존성이 필요한 객체는 자동으로 주입됨
	}
}
