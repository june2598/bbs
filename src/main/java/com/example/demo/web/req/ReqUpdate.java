package com.example.demo.web.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReqUpdate {

  private Long bbsId;
  private String writer;
  @NotBlank
  @Size(max = 100, message = "댓글 내용은 100글자를 초과할 수 없음")
  private String comments;
  private LocalDateTime udate;
}
