package com.example.demo.web.form.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JoinForm {
  @NotBlank(message = "이메일은 필수항목입니다.")
  @Email(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$",message = "이메일 형식에 맞지 않습니다")
  @Size( min = 7, max = 50, message = "이메일 크기는 7자~50자 사이입니다.")
  private String email;     //EMAIL	VARCHAR2(50 BYTE)
  @NotBlank(message = "비밀번호는 필수")
  private String passwd;    //PASSWD	VARCHAR2(12 BYTE)
  @NotBlank(message = "별칭은 필수 항목입니다.")
  private String nickname;  //NICKNAME	VARCHAR2(30 BYTE)
}