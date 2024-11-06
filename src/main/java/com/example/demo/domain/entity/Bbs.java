package com.example.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Bbs {
  private Long bbsId;           //게시글 ID  bbs_id number(10),
  private String writer;        //게시글 작성자 writer varchar2(30) not null,
  private String title;         //게시글 제목    title varchar2(100) not null,
  private String contents;      //게시글 내용     contents clob not null,
  private LocalDateTime cdate;  //게시글 작성날짜    cdate timestamp,
  private LocalDateTime udate;  //게시글 수정날짜  udate timestamp,
}
