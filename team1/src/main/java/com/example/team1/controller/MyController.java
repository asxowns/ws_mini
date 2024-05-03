package com.example.team1.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;

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
	public String login(@RequestParam("id") String id, @RequestParam("pw") String pw) throws IOException {

		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		Member result = null;

		result = dao.login(id, pw);

		if (result != null) {
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			session.setAttribute("pw", pw);
			session.setAttribute("name", result.getName());
			out.print(String.format("<script>alert('로그인성공');</script>"));
			return "success";
		} else {
			out.print(String.format("<script>alert('로그인실패 다시입력해주세요.');</script>"));
			return "loginForm";
		}

	}

	@GetMapping("/logout")
	public String logout() {
		HttpSession session = request.getSession();

		session.invalidate();

		return "index";
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
	public String write(Bbs bbs) throws Exception {
		System.out.println(bbs.getId());
		HttpSession session = request.getSession();
		bbs.setId((String) session.getAttribute("id"));
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		dao.write(bbs);

		if (bbs.getId().equals("")) {
			out.print(String.format("<script>alert('아이디를 입력해주세요.');</script>"));
			return "writeForm";
		} else if (bbs.getTitle().equals("")) {
			out.print(String.format("<script>alert('제목을 입력해주세요.');</script>"));
			return "writeForm";
		} else if (bbs.getContent().equals("")) {
			out.print(String.format("<script>alert('내용을 입력해주세요.');</script>"));
			return "writeForm";
		} else if (bbs.getTarget().equals("")) {
			out.print(String.format("<script>alert('받는 사람을 입력해주세요.');</script>"));
			return "writeForm";
		} else {
			return "redirect:list";
		}

	}

	// 접근 실패후 전페 리스트
	@GetMapping("/list1")
	public String list(Model model, @RequestParam("msg") String msg) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");

		model.addAttribute("list", dao.list());
		if (msg != null) {
			PrintWriter out = response.getWriter();
			out.print(String.format("<script>alert('접근권한이 없습니다.');</script>"));
		}
		return "list";
	}

	// 전체 리스트
	@GetMapping("/list")
	public String list(Model model) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");

		model.addAttribute("list", dao.list());

		return "list";
	}

	// 보낸 리스트
	@GetMapping("/sendList")
	public String sendList(@RequestParam("id") String id, Model model) {

		model.addAttribute("list", dao.sendList(id));

		return "listSend";
	}

	// 받은 리스트
	@GetMapping("/receiveList")
	public String receiveList(@RequestParam("id") String id, Model model) {

		model.addAttribute("list", dao.receiveList(id));

		return "listReceive";
	}

	// 디테일 페이지
	@GetMapping("/detail")
	public String detail(@RequestParam("bno") int bno, Model model) throws Exception {
		HttpSession session = request.getSession();
		System.out.println("dddd");
		Bbs bbs = dao.detail(bno);

		model.addAttribute("dto", bbs);

		if (session.getAttribute("id").equals(bbs.getId()) || session.getAttribute("id").equals(bbs.getTarget())) {
			return "detail";
		} else {
			PrintWriter out = response.getWriter();
			String msg = "sss";

			return "redirect:list1?msg=" + msg;
		}

	}

	// 디테일 send
	@GetMapping("/detailSend")
	public String detailSend(@RequestParam("bno") int bno, Model model) {

		Bbs bbs = dao.detail(bno);

		model.addAttribute("dto", bbs);

		return "detailSend";
	}

	// 디테일 receive
	@GetMapping("/detailReceive")
	public String detailReceive(@RequestParam("bno") int bno, Model model) {

		Bbs bbs = dao.detail(bno);

		model.addAttribute("dto", bbs);

		return "detailReceive";
	}

	// 삭제
	@GetMapping("/delete")
	public String delete(@RequestParam("bno") int bno) {

		dao.delete(bno);

		return "redirect:list";
	}

	@GetMapping("/success")
	public String seccess() {

		return "success";
	}

	// 회원가입 폼
	@GetMapping("/regForm")
	public String registForm() {
		return "regForm";
	}

	// 회원가입
	@PostMapping("/regist")
	public String regist(Member member) throws Exception {

		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		List<Member> list = dao.memberList();

		if (member.getId().equals("")) {
			out.print(String.format("<script>alert('아이디가 없습니다 다시 입력해주세요.');</script>"));
			return "regForm";
		} else if (member.getPw().equals("")) {
			out.print(String.format("<script>alert('비밀번호가 없습니다 다시 입력해주세요.');</script>"));
			return "regForm";
		} else if (member.getName().equals("")) {
			out.print(String.format("<script>alert('이름이 없습니다 다시 입력해주세요.');</script>"));
			return "regForm";
		}

		for (Member m : list) {
			if (m.getId().equals(member.getId())) {
				out.print(String.format("<script>alert('중복된 아이디 입니다 다시 입력해주세요.');</script>"));
				return "regForm";
			}
		}

		session.setAttribute("id", member.getId());
		session.setAttribute("pw", member.getPw());
		session.setAttribute("name", member.getName());

		dao.regist(member);
		return "success";

	}
}
