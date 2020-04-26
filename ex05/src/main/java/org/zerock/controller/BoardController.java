package org.zerock.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVo;
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

		int total = service.getTotal(cri);

		log.info("total: " + total);

		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}

	@GetMapping("/register")
	public void register() {

	}
	
	@PostMapping("/register")
	public String register(BoardVo board, RedirectAttributes rttr) {

		log.info("==========================");

		log.info("register: " + board);

		if (board.getAttachList() != null) {

			board.getAttachList().forEach(attach -> log.info(attach));

		}

		log.info("==========================");

		service.register(board);

		rttr.addFlashAttribute("result", board.getBno());

		return "redirect:/board/list";
	}
	
	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {

		log.info("/get or modify");
		model.addAttribute("board", service.get(bno));
	}

	@PostMapping("/modify")
	public String modify(BoardVo board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify:" + board);

		if (service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + cri.getListLink();
	}

	
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVo>>getAttachList(Long bno){
		
		log.info("어테치리스트"+bno);
		
		return new ResponseEntity<>(service.getAttachList(bno),HttpStatus.OK);
	}
	
	private void deleteFiles(List<BoardAttachVo>attachList) {
		
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		log.info("삭제 어테치 파일");
		log.info(attachList);
		
		attachList.forEach(attach->{
			try {
				Path file = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
				
				Files.deleteIfExists(file);
				
				if(Files.probeContentType(file).startsWith("image")) {
					
					Path thumbNail = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
					
					Files.delete(thumbNail);
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.error("파일 삭제 에러"+e.getMessage());
			}
		});
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr) {
		
		log.info("삭제"+bno);
		
		List<BoardAttachVo>attachList = service.getAttachList(bno);
		
		if(service.remove(bno)) {
			
			deleteFiles(attachList);
			
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/board/list" + cri.getListLink();
	}
}
