package com.example.demo.domain.bbs.dao;

import com.example.demo.domain.entity.ReplyBbs;

import java.util.List;
import java.util.Optional;

public interface ReplyBbsDAO {

  //댓글등록
  Long save(ReplyBbs replyBbs);

  //댓글목록
  List<ReplyBbs> listAll();

  //댓글조회
  Optional<ReplyBbs> findById(Long replyId);

  //댓글삭제
  int deleteById(Long replyId);

  //댓글수정
  int updateById(Long replyId, ReplyBbs replyBbs);
}
