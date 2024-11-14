package com.example.demo.web;

import com.example.demo.domain.entity.Member;
import com.example.demo.domain.member.svc.MemberSVC;
import com.example.demo.web.form.member.JoinForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
  private final MemberSVC memberSVC;

  //가입화면
  @GetMapping("/join")
  public String joinForm(Model model) {
    model.addAttribute("joinForm", new JoinForm());

    return "member/joinForm";
  }

  //가입처리
  @PostMapping("/join")
  public String join(@Valid JoinForm joinForm, BindingResult bindingResult, Model model) {
    log.info("joinForm={}", joinForm);

    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      model.addAttribute("joinForm", joinForm);      //에러 났을시 기존 입력값을 돌려줘야됨
      return "/member/joinForm";
    }

    Member member = new Member();
    BeanUtils.copyProperties(joinForm,member);      // entity의 joinForm과 member 양쪽이 객체가 일치해야 BeanUtils로 카피가 가능
    Member joinedMember = memberSVC.join(member);

    return "redirect:/login";

  }
}
