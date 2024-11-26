package com.example.demo.web;

import com.example.demo.domain.bbs.svc.ReplyBbsSVC;
import com.example.demo.domain.entity.ReplyBbs;
import com.example.demo.web.api.ApiResponse;
import com.example.demo.web.api.ApiResponseCode;
import com.example.demo.web.exception.BusinessException;
import com.example.demo.web.form.login.LoginMember;
import com.example.demo.web.req.ReqSave;
import com.example.demo.web.req.ReqUpdate;
import com.example.demo.web.util.khUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/replybbs")
@RequiredArgsConstructor
public class ApiReplyBbsController {

  private final ReplyBbsSVC replyBbsSVC;

  //댓글 목록
  @GetMapping("/{bbsId}")
  public ApiResponse<List<ReplyBbs>> reqPage(
      @PathVariable(name = "bbsId") Long bbsId,
      @RequestParam(value = "reqPage", defaultValue = "1") Integer reqPage,
      @RequestParam(value = "reqRec", defaultValue = "10") Integer reqRec) {

    ApiResponse<List<ReplyBbs>> res = null;
    List<ReplyBbs> replyBbsList = replyBbsSVC.listAll(reqPage,reqRec, bbsId);
    int totalRec = replyBbsSVC.getTotalReplyRecord(bbsId);

    if (replyBbsList.size() != 0) {
      res = ApiResponse.of(ApiResponseCode.SUCCESS, replyBbsList, totalRec);
    } else {
      throw new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND, null);
    }
    return res;
  }

  //댓글 등록
  @PostMapping
  public ApiResponse<ReplyBbs> add(
      @Valid @RequestBody ReqSave reqSave,
      BindingResult bindingResult,
      HttpSession session) {

//    log.info("reqSave={}", reqSave);
    ApiResponse<ReplyBbs> res;

    // 로그인된 닉네임 가져오기
    LoginMember loginOkMember = (LoginMember) session.getAttribute("loginOkMember");
    String loggedInNickname = loginOkMember.getNickname();
    log.info("Logged in nickname: {}", loggedInNickname);

    // 작성자 필드 설정
    reqSave.setWriter(loggedInNickname);

    // 요청 데이터 유효성 체크
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      throw new BusinessException(ApiResponseCode.VALIDATION_ERROR, khUtil.getValidChkMap(bindingResult));
    }

    // 댓글 길이 및 특수 문자 체크
    if (reqSave.getComments() == null || reqSave.getComments().length() > 100) {
      bindingResult.rejectValue("comments", null, "댓글내용 100자 초과 불가");
    }

    // writer가 null인지 체크한 후 길이 체크
//    String writer = reqSave.getWriter();
//    if (writer == null || writer.length() > 10) {
//      bindingResult.rejectValue("writer", null, "작성자명 10자 초과불가");
//    }

//    if (writer != null && writer.matches(".*[<>?;:!@#$%^&*()_+=-].*")) {
//      bindingResult.rejectValue("writer", null, "작성자명에 허용되지 않는 문자가 포함되어 있습니다.");
//    }

    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      throw new BusinessException(ApiResponseCode.VALIDATION_ERROR, khUtil.getValidChkMap(bindingResult));
    }

    // ReplyBbs 객체 생성
    ReplyBbs replyBbs = new ReplyBbs();
    BeanUtils.copyProperties(reqSave, replyBbs);

    // 게시글 ID 설정
    replyBbs.setBbsId(reqSave.getBbsId());

    Long rid = replyBbsSVC.save(replyBbs);

    log.info("replyBbs={}", replyBbs);

    Optional<ReplyBbs> optionalReply = replyBbsSVC.findById(rid);
    if (optionalReply.isPresent()) {
      ReplyBbs savedReply = optionalReply.get();
      res = ApiResponse.of(ApiResponseCode.SUCCESS, savedReply);
    } else {
      throw new BusinessException(ApiResponseCode.INTERNAL_SERVER_ERROR, null);
    }
    return res;
  }
//  public ApiResponse<ReplyBbs> add(
//      @Valid
//      @RequestBody ReqSave reqSave,
//      BindingResult bindingResult) {
//    log.info("reqSave={}", reqSave);
//    ApiResponse<ReplyBbs> res = null;
//
//    //요청 데이터 유효성 체크
//    //1. 어노테이션 기반의 필드 검증
//    if (bindingResult.hasErrors()) {
//      log.info("bindingResult={}", bindingResult);
//      throw new BusinessException(ApiResponseCode.VALIDATION_ERROR, khUtil.getValidChkMap(bindingResult));
//    }
//
//    //2. 코드기반 검증
//    //필드 오류 : 댓글 길이 100자 초과 불가
//    if (reqSave.getComments().length() > 100) {
//      bindingResult.rejectValue("comments", null, "댓글내용 100자 초과 불가");
//    }
//
//    //필드 오류 2 : 댓글 작성자 길이 10자 초과 불가
//    if (reqSave.getWriter().length() > 10) {
//      bindingResult.rejectValue("writer", null, "작성자명 10자 초과불가");
//    }
//
//    //필드 오류 3 : 댓글 작성자명에 특수문자 사용 불가
//    if (reqSave.getWriter().matches(".*[<>?;:!@#$%^&*()_+=-].*")) {
//      bindingResult.rejectValue("writer", null, "작성자명에 허용되지 않는 문자가 포함되어 있습니다.");
//    }
//
//
//
//    if (bindingResult.hasErrors()) {
//      log.info("bindingResult={}", bindingResult);
//      throw new BusinessException(ApiResponseCode.VALIDATION_ERROR, khUtil.getValidChkMap(bindingResult));
//    }
//
//    ReplyBbs replyBbs = new ReplyBbs();
//    BeanUtils.copyProperties(reqSave, replyBbs);
//    Long rid = replyBbsSVC.save(replyBbs);
//
//    log.info("replyBbs={}", replyBbs);
//
//    Optional<ReplyBbs> optionalReply = replyBbsSVC.findById(rid);
//    if (optionalReply.isPresent()) {
//      ReplyBbs savedReply = optionalReply.get();
//      res = ApiResponse.of(ApiResponseCode.SUCCESS, savedReply);
//    } else {
//      throw new BusinessException(ApiResponseCode.INTERNAL_SERVER_ERROR, null);
//    }
//    return res;
//  }

  //댓글 삭제
  @DeleteMapping("/{rid}")
  public ApiResponse delete(@PathVariable("rid") Long rid) {
    ApiResponse res = null;

    int rows = replyBbsSVC.deleteById(rid);
    if (rows == 1) {
      res = ApiResponse.of(ApiResponseCode.SUCCESS, null);
    } else {
      throw new BusinessException(ApiResponseCode.ENTITY_NOT_FOUND, null);
    }
    return res;
  }

  //댓글수정
  @PatchMapping("/{rid}")
  public ApiResponse update(
      @PathVariable("rid") Long rid,
      @Valid @RequestBody ReqUpdate reqUpdate,
      BindingResult bindingResult) {
    log.info("reqUpdate={}", "reqUpdate={}", rid, reqUpdate);
    ApiResponse res = null;

    //요청 데이터 유효성 체크
    //1. 어노테이션 기반의 필드 검증
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      throw new BusinessException(ApiResponseCode.VALIDATION_ERROR, khUtil.getValidChkMap(bindingResult));
    }

    //2. 코드기반 검증
    //필드 오류 : 댓글 길이 100자 초과 불가
    if (reqUpdate.getComments().length() > 100) {
      bindingResult.rejectValue("comments", null, "댓글내용 100자 초과 불가");
    }

    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      throw new BusinessException(ApiResponseCode.VALIDATION_ERROR, khUtil.getValidChkMap(bindingResult));
    }

    ReplyBbs replyBbs = new ReplyBbs();
    BeanUtils.copyProperties(reqUpdate, replyBbs);
    int rows = replyBbsSVC.updateById(rid, replyBbs);
    if (rows == 1) {
      ReplyBbs updatedReply = replyBbsSVC.findById(rid).get();
      res = ApiResponse.of(ApiResponseCode.SUCCESS, updatedReply);
    } else {
      res = ApiResponse.of(ApiResponseCode.ENTITY_NOT_FOUND, null);
    }
    return res;
  }

  //댓글조회
  @GetMapping("/reply/{rid}")
  public ApiResponse<ReplyBbs> findbyId(@PathVariable("rid") Long rid) {
    ApiResponse<ReplyBbs> res = null;
    Optional<ReplyBbs> optionalReply = replyBbsSVC.findById(rid);

    if (optionalReply.isPresent()) {
      ReplyBbs replyBbs = optionalReply.get();
      res = ApiResponse.of(ApiResponseCode.SUCCESS, replyBbs);
    } else {
      res = ApiResponse.of(ApiResponseCode.ENTITY_NOT_FOUND, null);
    }
    return res;
  }

  @GetMapping("/totalCnt/{bbsId}")
  public ApiResponse<Integer> totalCnt(@PathVariable(name = "bbsId") Long bbsId){
    ApiResponse<Integer> res = null;
    Integer totalRec = replyBbsSVC.getTotalReplyRecord(bbsId);

    res = ApiResponse.of(ApiResponseCode.SUCCESS,null,totalRec);

    return res;
  }
}
