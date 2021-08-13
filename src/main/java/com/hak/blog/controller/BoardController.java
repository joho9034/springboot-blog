package com.hak.blog.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hak.blog.config.auth.PrincipalDetail;
import com.hak.blog.model.Board;
import com.hak.blog.service.BoardService;

@Controller
public class BoardController {

	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping({"", "/"})
	public String index(
			Model model,
			@PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC) Pageable pageable,
			@AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println("로그인 사용자 아이디: " + principal.getUsername());
		Page<Board> pagingBoard = boardService.findAll(pageable);
		model.addAttribute("boards", pagingBoard);
		return "index";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/board/{id}")
	public String detail(@PathVariable int id, Model model) {
//		System.out.println("ID: " + id);
		model.addAttribute("board", boardService.detail(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
//		System.out.println("ID: " + id);
		model.addAttribute("board", boardService.detail(id));
		return "board/updateForm";
	}
	
}
