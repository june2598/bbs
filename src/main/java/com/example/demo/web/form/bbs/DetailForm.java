package com.example.demo.web.form.bbs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DetailForm {
  private Long bbsId;             //게시글ID
  private String title;           //게시글 제목
  private String writer;          //게시글 작성자
  private String contents;        //게시글 내용
  private LocalDateTime cdate;    //게시글 작성날짜
  private LocalDateTime udate;    //게시글 수정날짜
}
