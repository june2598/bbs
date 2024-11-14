package com.example.demo.web.form.bbs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AllForm {
  private Long bbsId;             //게시글 id
  private String title;           //게시글 제목
  private String writer;          //게시글 작성자
  private LocalDateTime cdate;    //게시글 작성 날짜
  private LocalDateTime udate;    //게시글 수정 날짜

}
