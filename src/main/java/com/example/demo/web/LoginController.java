package com.example.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class LoginController {
  //로그인화면
  @GetMapping("/login")
  public String loginForm(){
    return "/login/loginForm";
  }

}
