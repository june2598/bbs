package com.example.demo.web.form.bbs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveForm {
  private Long bbsId; //게시글 id  bbs_id number(10),
  private String writer; //게시글 작성자 writer varchar2(30) not null,
  @NotBlank(message = "제목은 필수 항목입니다.")
  @Size(max = 33, message = "제목은 33자 이내로 작성해야 합니다.")
  private String title; //게시글 제목    title varchar2(100) not null,
  @NotBlank(message = "내용은 필수 항목입니다.")
  private String contents; //게시글 내용     contents clob not null,
}
