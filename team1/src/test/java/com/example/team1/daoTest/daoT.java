package com.example.team1.daoTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.team1.dao.IBbsDao;

@SpringBootTest
class daoT {

	@Autowired
	IBbsDao dao;
	@Test
	void test() {
		int result = dao.login("111", "111");
		assertThat(result).isEqualTo(1);
	}

}
