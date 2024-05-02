package com.example.team1.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.team1.dao.IBbsDao;
import com.example.team1.dto.Bbs;
import com.example.team1.dto.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
	public String login(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id, @RequestParam("pw") String pw) throws IOException {
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		Member result = dao.login(id, pw);
		
		if(result != null) {
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			session.setAttribute("pw", pw);
			out.print(String.format("<script>alert('로그인성공');</script>"));
			return "success";
		}else {
			out.print(String.format("<script>alert('로그인실패 다시입력해주세요.');</script>"));
			return "loginForm";
		}
	}
	
	@GetMapping("/myPage")
	public String myPage() {
		return "myPage";
	}
	
	// 글작성 폼
	@GetMapping("/writeForm")
	public String writeForm() {
		
		return "writeForm";
	}
	
	// 글작성하기
	@PostMapping("/write")
	public String write(Bbs bbs, HttpServletResponse response, HttpServletRequest request) throws Exception{
		System.out.println(bbs.getId());
		HttpSession session = request.getSession();
		bbs.setId((String)session.getAttribute("id"));
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		dao.write(bbs);
		
		if(bbs.getId().equals("")) {
			out.print(String.format("<script>alert('아이디를 입력해주세요.');</script>"));
			return "writeForm";
		}else if(bbs.getTitle().equals("")) {
			out.print(String.format("<script>alert('제목을 입력해주세요.');</script>"));
			return "writeForm";
		}else if(bbs.getContent().equals("")) {
			out.print(String.format("<script>alert('내용을 입력해주세요.');</script>"));
			return "writeForm";
		}else if(bbs.getTarget().equals("")) {
			out.print(String.format("<script>alert('받는 사람을 입력해주세요.');</script>"));
			return "writeForm";
		}else {
			return "redirect:list";
		}
			
		
	}
	
	//전체 리스트
	@GetMapping("/list")
	public String list(Model model) {
		
		model.addAttribute("list", dao.list());
		
		return "list";
	}
	
	//보낸 리스트
	@GetMapping("/sendList")
	public String sendList(@RequestParam("id") String id, Model model) {
		
		model.addAttribute("list", dao.sendList(id));
		
		return "listSend";
	}
	
	//받은 리스트
	@GetMapping("/receiveList")
	public String receiveList(@RequestParam("id") String id, Model model) {
		
		model.addAttribute("list", dao.receiveList(id));
		
		return "listReceive";
	}
	
	// 디테일 페이지
	@GetMapping("/detail")
	public String detail(@RequestParam("bno") int bno, Model model) {
		
		Bbs bbs = dao.detail(bno);
		
		model.addAttribute("dto", bbs);
		
		return "detail";
	}
	
	// 삭제
	@GetMapping("/delete")
	public String delete(@RequestParam("bno") int bno) {
		
		dao.delete(bno);
		
		return "redirect:list";
	}
	
	
	
	
	
	
	
	
	
	
	

}
