package com.example.demo.domain.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Member {
  private Long memberId;    //  MEMBER_ID	NUMBER
  private String email;     //  EMAIL	VARCHAR2(50 BYTE)
  private String passwd;    //  PASSWD	VARCHAR2(12 BYTE)
  private String nickname;  //  NICKNAME	VARCHAR2(30 BYTE)
  private String gubun;     //  GUBUN VARCHAR2(15 BYTE)
}
