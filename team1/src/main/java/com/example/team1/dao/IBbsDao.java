package com.example.team1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.team1.dto.Bbs;
import com.example.team1.dto.Member;

@Mapper
public interface IBbsDao {

	public int login(@Param("id") String id,@Param("pw") String pw);
	public int regist(@Param("member") Member member);
	public int write(@Param("bbs") Bbs bbs);
	public List<Bbs> list();
	public List<Bbs> sendList(@Param("id") String id);
	public List<Bbs> receiveList(@Param("id") String id);
	public Bbs detail(@Param("bno") int bno);
	public int delete(@Param("bno") int bno);
}
