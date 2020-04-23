package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVo {
	private long bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updatedate;
	
	private int replyCnt;
}
