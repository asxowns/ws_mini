package com.example.team1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.team1.dao.IBbsDao;
import com.example.team1.dto.Bbs;

@Controller
public class MyController {
	
	@Autowired
	private IBbsDao dao;
	
	// 인덱스
	@RequestMapping("/index")
	public String root() {
		return "index";
	}
	
	// 로그인 폼
	@GetMapping("/loginForm")
	public String loginForm() {
		
		return "loginForm";
	}
	
	// 로그인하기
	@PostMapping("/login")
	public String login(@RequestParam("id") String id, @RequestParam("pw") String pw) {
		
		dao.login(id, pw);
		
		if(sessionScope.id != null) {
			return "success";
		}else {
			return "loginForm";
		}
	}
	
	@GetMapping("/writeForm")
	public String writeForm() {
		
		return "writeForm";
	}
	
	@PostMapping("/write")
	public String write(Bbs bbs) {
		
		dao.write(bbs);
		
		return "redirect:list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		
		model.addAttribute("dto", dao.list());
		
		return "list";
	}
	
	@GetMapping("/sendList")
	public String sendList(@RequestParam("id") String id, Model model) {
		
		model.addAttribute("dto", dao.sendList(id));
		
		return "listSend";
	}
	
	@GetMapping("/receiveList")
	public String receiveList(@RequestParam("id") String id, Model model) {
		
		model.addAttribute("dto", dao.receiveList(id));
		
		return "listReceive";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
