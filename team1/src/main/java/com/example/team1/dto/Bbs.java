package com.example.team1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bbs {

	private int bno;
	private String id;
	private String title;
	private String content;
	private String target;
	
}
