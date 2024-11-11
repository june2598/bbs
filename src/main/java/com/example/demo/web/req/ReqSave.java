package com.example.demo.web.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ReqSave {
  private String writer;
  private String comments;
  private LocalDateTime cdate;
}
