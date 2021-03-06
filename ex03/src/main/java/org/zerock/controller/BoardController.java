package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVo;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
//BoardController는 BoardService에 대해서 의존적이므로 
//@AllArgsConstructor를 이용해서 생성자를 만들고 자동으로 주입
//(만일 생성자를 만들지 않을 경우 에는 @Setter(onMethod. = { @Autowired })를 이용
public class BoardController {
	private BoardService service;

	@GetMapping("/list")
	public void list(Criteria cri, Model model) {

		log.info("list: " + cri);
		model.addAttribute("list", service.getList(cri));
//		model.addAttribute("pageMaker", new PageDTO(cri, 123));
		
		int total = service.getTotal(cri);
		
		log.info("total: "+total);
		
		model.addAttribute("pageMaker",new PageDTO(cri, total));
	}

//	@GetMapping("/list")
//	public void list(Model model) {
//		log.info("list");
//		
//		model.addAttribute("list",service.getList());
//		//list( )는 나중에 게시물의 목록을 전달해야 하므로 Model을 파라미터로 지정하고，
//		//이를 통해서 BoardServiceImpl 객체의 getList( ) 결과를 담아 전달합니다(addAttribute). 
//		//BoardController 테스트는 스프링의 테스트 기능을 통해서 확인해 볼 수 있습니다. 
//	}

	@GetMapping("/register")
	public void register() {

	}

	@PostMapping("/register")
	public String register(BoardVo board, RedirectAttributes rttr) {
		// RedirectAttributes를 파라미터로 지정 , 등록 작업이 끝난 후 다시 목록 화면으로 가기 위함
		log.info("register: " + board);

		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		// 번호를 꺼내와서 result에 담아둠

//		rttr.addAttribute로 전달한 값은 url뒤에  붙으며, 
//		리프레시해도 데이터가 유지된다.

//		rttr.addFlashAttribute로 전달한 값은 url뒤에 붙지 않는다. 
//		일회성이라 리프레시할 경우 데이터가 소멸한다.
//		또한 2개이상 쓸 경우, 데이터는 소멸한다. 
//		따라서 맵을 이용하여 한번에 값전달해야한다.

		return "redirect:/board/list";
	}

	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri")
	Criteria cri, Model model) {

		log.info("/get or modify");
		model.addAttribute("board", service.get(bno));
	}

	@PostMapping("/modify")
	public String modify(BoardVo board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify:" + board);

		if (service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword",cri.getKeyword());
		
//		return "redirect:/board/list";
		
//		↓↓↓이렇게 간단히 바꿀수 있음↓↓↓
		return "redirect:/board/list"+cri.getListLink();
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove..." + bno);
		
		if (service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword",cri.getKeyword());
		
//		return "redirect:/board/list";
		return "redirect:/board/list"+cri.getListLink();

	}
}
