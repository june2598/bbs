package com.example.demo.web;

import com.example.demo.domain.bbs.svc.ReplyBbsSVC;
import com.example.demo.domain.entity.ReplyBbs;
import com.example.demo.web.api.ApiResponse;
import com.example.demo.web.req.ReqSave;
import com.example.demo.web.req.ReqUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
  @GetMapping
  public ApiResponse<List<ReplyBbs>> all() {
    ApiResponse<List<ReplyBbs>> res = null;
    List<ReplyBbs> replyBbsList = replyBbsSVC.listAll();
    if(replyBbsList.size() != 0){
      res = ApiResponse.createApiResponse("00", "success", replyBbsList);
    }else{
      res = ApiResponse.createApiResponse("01", "fail", null);
    }
    return res;
  }

  //댓글 등록
  @PostMapping
  public ApiResponse<ReplyBbs> add(@RequestBody ReqSave reqSave){
    log.info("reqSave={}",reqSave);
    ApiResponse<ReplyBbs> res = null;
    ReplyBbs replyBbs = new ReplyBbs();
    BeanUtils.copyProperties(reqSave, replyBbs);
    Long rid = replyBbsSVC.save(replyBbs);

    Optional<ReplyBbs> optionalReply = replyBbsSVC.findById(rid);
    if(optionalReply.isPresent()){
      ReplyBbs savedReply = optionalReply.get();
      res = ApiResponse.createApiResponse("00", "success", savedReply);
    }else{
      res = ApiResponse.createApiResponse("01", "fail", null);
    }
    return res;
    }
  //댓글 삭제
  @DeleteMapping("/{rid}")
  public ApiResponse delete(@PathVariable("rid") Long rid){
    ApiResponse res = null;

    int rows = replyBbsSVC.deleteById(rid);
    if(rows == 1){
      res = ApiResponse.createApiResponse("00", "success", null);
    }else{
      res = ApiResponse.createApiResponse("01", "fail", null);
    }
    return res;
    }

    //댓글수정
  @PatchMapping("/{rid}")
  public ApiResponse update(@PathVariable("rid") Long rid, @RequestBody ReqUpdate reqUpdate) {
    log.info("reqUpdate={}", "reqUpdate={}", rid, reqUpdate);
    ApiResponse res = null;

    ReplyBbs replyBbs = new ReplyBbs();
    BeanUtils.copyProperties(reqUpdate,replyBbs);
    int rows = replyBbsSVC.updateById(rid, replyBbs);
    if(rows == 1){
      ReplyBbs updatedReply = replyBbsSVC.findById(rid).get();
      res = ApiResponse.createApiResponse("00", "success", updatedReply);
    }else{
      res = ApiResponse.createApiResponse("01", "fail", null);
    }
    return res;
  }
  //댓글조회
  @GetMapping("/{rid}")
  public ApiResponse<ReplyBbs> findbyId(@PathVariable("rid") Long rid){
    ApiResponse<ReplyBbs> res = null;
    Optional<ReplyBbs> optionalReply = replyBbsSVC.findById(rid);

    if(optionalReply.isPresent()){
      ReplyBbs replyBbs = optionalReply.get();
      res = ApiResponse.createApiResponse("00", "success", replyBbs);
    }else{
      res = ApiResponse.createApiResponse("01", "fail", null);
    }
    return res;
  }
}
