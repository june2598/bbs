package com.example.demo.web.form.login;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString

public class LoginMember {
  private Long memberId;      //내부 관리용 아이디
  private String email;       //회원 로그인 이메일
  private String nickname;    //회원 별칭
}
