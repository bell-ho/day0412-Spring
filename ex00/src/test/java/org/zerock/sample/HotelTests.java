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
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
//위의 문자열로 되있는 주소를 이용하여 필요한 객체를 스프링내로 등록하게 됨
@Log4j
public class HotelTests {
	@Setter(onMethod_ = @Autowired)
	private SampleHotel hotel;

	@Test
	public void testExist() {

		assertNotNull(hotel);
		// assertNotNull 는 해당 매개변수가 null이되면 실패임 즉 매개변수가 잘와야지 성공

		log.info(hotel);
		log.info("==================================================");
		log.info(hotel.getChef());
	}
}
