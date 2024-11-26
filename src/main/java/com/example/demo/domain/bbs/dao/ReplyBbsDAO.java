package com.example.demo.domain.bbs.dao;

import com.example.demo.domain.entity.ReplyBbs;

import java.util.List;
import java.util.Optional;

public interface ReplyBbsDAO {

  //댓글등록
  Long save(ReplyBbs replyBbs);

//  //댓글 목록(전체)
//  List<ReplyBbs> listAll(Long bbsId);

  //댓글목록(페이징)
  List<ReplyBbs> listAll(int reqPage, int reqRec, Long bbsId);

  //댓글조회
  Optional<ReplyBbs> findById(Long replyId);

  //댓글삭제
  int deleteById(Long replyId);

  //댓글수정
  int updateById(Long replyId, ReplyBbs replyBbs);

  //총 댓글수 조회
  int getTotalReplyRecord(Long bbsId);

}
