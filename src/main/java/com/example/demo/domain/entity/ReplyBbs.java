package com.example.demo.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyBbs {
  private Long reply_id;          //댓글 id NUMBER(10)
  private Long bbs_id;            //게시글 id NUMBER(10)
  private String comments;        //댓글 내용 CLOB
  private String writer;          //댓글 작성자 VARCHAR2(30)
  private LocalDateTime cdate;    //댓글 작성 날짜 TIMESTAMP
  private LocalDateTime udate;    //댓글 수정 날짜 TIMESTAMP
}
