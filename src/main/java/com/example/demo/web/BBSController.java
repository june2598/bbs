package com.example.demo.web;

import com.example.demo.domain.bbs.svc.BbsSVC;
import com.example.demo.domain.entity.Bbs;
import com.example.demo.web.form.bbs.AllForm;
import com.example.demo.web.form.bbs.DetailForm;
import com.example.demo.web.form.bbs.SaveForm;
import com.example.demo.web.form.bbs.UpdateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class BBSController {

  //bbsSVC 서비스 클래스 주입받음
  private final BbsSVC bbsSVC;

  //목록양식
  @GetMapping // get방식 /bbs
  //모든 게시글 목록을 조회후 반환하는 메소드. Model 객체를 통해 데이터를 뷰로 전달
  public String listAll(Model model) {

    //모든 게시글 조회
    List<Bbs> list = bbsSVC.listAll();
    //게시글 정보를 담을 리스트생성
    List<AllForm> all = new ArrayList<>();
    //향상된 for문 - 게시글 리스트를 순회
    for (Bbs bbs : list) {
      //게시글 정보를 담기위한 AllForm 객체 생성
      AllForm allForm = new AllForm();
      //게시글 데이터 설정
      allForm.setBbsId(bbs.getBbsId());
      allForm.setTitle(bbs.getTitle());
      allForm.setWriter(bbs.getWriter());
      allForm.setCdate(bbs.getCdate());
      allForm.setUdate(bbs.getUdate());
      //설정한 객체를 정보를 담을 리스트에 추가
      all.add(allForm);
    }

    //모델에 리스트를 추가해 뷰에서 사용할수 있도록 설정
    model.addAttribute("all", all);

    return "bbs/list";    // list 뷰를 반환
  }

  //등록양식
  @GetMapping("/add") //get방식으로 /bbs/add 요청 들어올때 호출
  public String addForm(Model model) {
    model.addAttribute("saveForm", new SaveForm());
    //게시글 등록 폼 뷰를 반환
    return "/bbs/add";
  }

  //등록처리
  @PostMapping("/add") //post방식으로 /bbs/add 요청이 들어올때 호출
  public String add(
      @Valid
      @ModelAttribute SaveForm saveForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {
    //사용자의 입력정보
    log.info("title={}, writer={}, contents={}", saveForm.getTitle(), saveForm.getWriter(), saveForm.getContents());
    // 서버 데이터 오류 처리 유효성검사 : 실패시
    if (bindingResult.hasErrors()) {
      return "/bbs/add";
    }

    //제목 길이 제한 조건 : 33글자 초과 불가 (varchar2(100))
    if(saveForm.getTitle().length() > 33){
      bindingResult.rejectValue("title",null,"제목 길이 33자 초과 불가");
    }

    //작성자 이름 제한 조건 : 10글자 초과 불가(varchar(30))
    if(saveForm.getWriter().length() > 10) {
      bindingResult.rejectValue("writer",null,"작성자 이름 10자 초과 불가");
    }

    if (bindingResult.hasErrors()) {
      return "/bbs/add"; //폼으로 다시 리다이렉트
    }




    //게시글 테이블에 저장할 데이터 설정
    Bbs bbs = new Bbs();
    bbs.setTitle(saveForm.getTitle());
    bbs.setWriter(saveForm.getWriter());
    bbs.setContents(saveForm.getContents());

    //게시글을 저장하고 생성된 id를 반환받아 bid에 저장
    Long bid = bbsSVC.save(bbs);
    //리다이렉트시 사용할 url경로 변수에 값을 동적으로 할당
    redirectAttributes.addAttribute("id", bid);

    return "redirect:/bbs/{id}/";   //게시글 상세 페이지로 리다이렉트
  }


  //게시글 단건조회
  @GetMapping("/{id}/")    //get 방식
//PathVariable("id") Long bbsId: url패턴에서 "id"라는 이름의 변수를 찾아, 그 값을 메소드의 bbsId 변수에 할당
//Model 객체를 사용해 컨트롤러에서 처리한 데이터를 뷰로 전달 할 수 있다.
  public String findById(
      @PathVariable("id") Long bbsId,
      Model model) {
    log.info("bbsId={}", bbsId);

    Optional<Bbs> listBbs = bbsSVC.listById(bbsId);

    //게시글이 존재하지 않으면 예외 처리
    Bbs bbs = listBbs.orElseThrow();

    //게시글 상세 정보를 담기위한 객체 생성
    DetailForm detailForm = new DetailForm();

    //게터메소드를 통해 상세정보를 가져와 생성한 객체에 세터 메소드를 이용해 추가
    detailForm.setBbsId(bbs.getBbsId());
    detailForm.setTitle(bbs.getTitle());
    detailForm.setWriter(bbs.getWriter());
    detailForm.setContents(bbs.getContents());
    detailForm.setCdate(bbs.getCdate());
    detailForm.setUdate(bbs.getUdate());

    //모델에 게시글 상세정보 추가
    model.addAttribute("detailForm", detailForm);

    return "/bbs/detailForm"; //게시글 상세 페이지 뷰 봔환

  }

  //단건삭제
  @GetMapping("/{id}/del")
  public String deleteById(@PathVariable("id") Long bbsId) {

    log.info("bbsId={}", bbsId);   //삭제영억 로그에 찍어보기

    int rows = bbsSVC.deleteById(bbsId);

    return "redirect:/bbs";
  }

  //수정화면
  @GetMapping("/{id}/edit")
  public String updateForm(@PathVariable("id") Long bbsId, Model model) {
    Optional<Bbs> optionalBbs = bbsSVC.listById(bbsId);
    Bbs listBbs = optionalBbs.orElseThrow();

    UpdateForm updateForm = new UpdateForm();
    updateForm.setBbsId(listBbs.getBbsId());
    updateForm.setTitle(listBbs.getTitle());
    updateForm.setContents(listBbs.getContents());
    updateForm.setWriter(listBbs.getWriter());
    updateForm.setUdate(listBbs.getUdate());

    model.addAttribute("updateForm", updateForm);

    return "/bbs/updateForm";

  }

  //게시글 단건 수정처리
  @PostMapping("/{id}/edit")
  public String updateById(

      @PathVariable("id") Long bbsId,
      @Valid
      @ModelAttribute UpdateForm updateForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes) {
    // 서버 데이터 오류 처리 유효성검사 : 실패시
    if (bindingResult.hasErrors()) {
      return "bbs/updateForm"; //폼으로 다시 이동
    }

    //제목 길이 제한 조건 : 33글자 초과 불가 (varchar2(100))
    if(updateForm.getTitle().length() > 33){
      bindingResult.rejectValue("title",null,"제목 길이 33자 초과 불가");
    }

    if (bindingResult.hasErrors()) {
      return "bbs/updateForm"; //폼으로 다시 이동
    }

    log.info("bbsId={}", bbsId);
    log.info("updateForm={}", updateForm);

    Bbs bbs = new Bbs();
    bbs.setBbsId(updateForm.getBbsId());
    bbs.setTitle(updateForm.getTitle());
    bbs.setContents(updateForm.getContents());
    bbs.setWriter(updateForm.getWriter());
    bbs.setUdate(LocalDateTime.now());  //현재 날짜와 시간으로 수정 날짜 설정

    int rows = bbsSVC.updateById(bbsId, bbs);

    redirectAttributes.addAttribute("id", bbsId);

    return "redirect:/bbs/{id}/";   //게시글 상세 페이지로 리다이렉트
  }


  //여러건 삭제처리
  @PostMapping("/del")
  public String deleteByIds(@RequestParam("bbsIds") List<Long> bbsIds) {

    log.info("bbsIds={}", bbsIds);
    int rows = bbsSVC.deleteByIds(bbsIds);
    return "redirect:/bbs"; //302 get redirectUrl
  }

}
