package org.zerock.sample;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;

@Component
@Data

@Getter
public class SampleHotel {
	private Chef chef;

	// 생성자를 선언하고 Chef를 주입하도록 작성함 Autowired가 필요없음
	public SampleHotel(Chef chef) {
		this.chef = chef;
	}
}
