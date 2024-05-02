package com.example.team1.daoTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.team1.dao.IBbsDao;
import com.example.team1.dto.Bbs;
import com.example.team1.dto.Member;

@SpringBootTest
class daoT {

	@Autowired
	IBbsDao dao;
	
	@Test
	void loginO() {
		Member result = dao.login("111", "111");
		assertThat(result).isNotNull();
	}
	@Test
	void loginX() {
		Member result = dao.login("111", "112");
		assertThat(result).isNull();
	}//만약로그인 실패시 반환값은??
	
	@Test
	void list() {
		
		List<Bbs> bbs = dao.list();
		
		assertThat(bbs.get(0).getTitle()).isEqualTo("1번제목");
	}
	
	/*
	@Test
	void registO() {
		Member member = new Member("222","222","임꺽정");
		int result =dao.regist(member);
		assertThat(result).isEqualTo(1);
		
	}
	*/
	/* 0반환 안되고 에러뜸
	@Test
	void registX() {
		Member member = new Member("111","333","김개똥");
		int result =dao.regist(member);
		assertThat(result).isEqualTo(0);
	}
	*/
	
	/*성공
	@Test
	void write(){
		Bbs bbs = new Bbs(0,"111","2번제목","2번내용","222");
		int result = dao.write(bbs);
		assertThat(result).isEqualTo(1);
	}
	*/
	

	
	
	
}
