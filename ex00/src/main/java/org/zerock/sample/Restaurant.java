package org.zerock.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;

@Component
//@Component는 스프링에게 해당 클래스가 스프링에서 관리해야하는 대상임을 표시한다
@Data
public class Restaurant {
	@Setter(onMethod_ = @Autowired)

	// @Setter는 자동으로 setChef( )를 컴파일 시 생성합니다.
	// @Setter에서 사용된 onMethod 속성은
	// 생성되는 setChef( )에 @Autowired 어노테이션을 추가하도록 합니다
	// 레스토랑 객체는 셰프 객체가 필요하다는 것을 명시

	private Chef chef;
}
