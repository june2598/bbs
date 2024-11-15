package com.example.demo.web;

import com.example.demo.domain.entity.Member;
import com.example.demo.domain.member.dao.MemberDAO;
import com.example.demo.web.form.login.LoginForm;
import com.example.demo.web.form.login.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final MemberDAO memberDAO;
  //로그인화면
  @GetMapping("/login")
  public String loginForm(Model model){
    model.addAttribute("loginForm",new LoginForm());
    return "/login/loginForm";
  }
  //로그인처리
  @PostMapping("/login")
  public String login(@Valid LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request){
    log.info("loginForm={}",loginForm);

    //회원존재유무
    if(!memberDAO.isExist(loginForm.getEmail())){
      bindingResult.rejectValue("email","invalidMember");
      return "/login/loginForm";
    }

    //비밀번호 일치여부
    Optional<Member> optionalMember = memberDAO.findByEmail(loginForm.getEmail());
    Member loginMember = optionalMember.get();
    log.info("loginMember={}",loginMember);

    if (!loginForm.getPasswd().equals(loginMember.getPasswd())) {
      bindingResult.rejectValue("passwd","invalidMember");
      return "/login/loginForm";

    }

    //로그인 세션 반영

    //세션이 존재하면 해당 세션을 가져오고 없으면 신규생성
    HttpSession session = request.getSession(true);

    //세션에 회원정보 저장
    LoginMember loginOkMember = new LoginMember(
        loginMember.getMemberId(),
        loginMember.getEmail(),
        loginMember.getNickname());
    session.setAttribute("loginOkMember",loginOkMember);


    return "redirect:/";
  }

  //로그아웃 처리
  @GetMapping("/logout")
  public String logout(HttpServletRequest request) {
    //세션정보 가져오기
    //false : 세션이 있으면 가져오고 없으면 안가져옴(신규생성안함)
    HttpSession session = request.getSession(false);
    //세션제거
    session.invalidate();
    return "redirect:/";
  }

}