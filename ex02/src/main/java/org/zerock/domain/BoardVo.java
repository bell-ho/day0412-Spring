package org.zerock.domain;

import lombok.Data;

@Data
public class BoardVo {
	private long bno;
	private String title;
	private String content;
	private String writer;
}