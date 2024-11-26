package com.example.demo.domain.bbs.svc;

import com.example.demo.domain.entity.Bbs;

import java.util.List;
import java.util.Optional;

public interface BbsSVC {

//  //등록
  Long save(Bbs bbs);

//목록
List<Bbs> listAll();

//페이지 당 목록
List<Bbs> listAll(int reqPage, int reqRec);

//  //조회
  Optional<Bbs> listById(Long bbsId);
//
//  //삭제
  int deleteById(Long bbsId);
//
//  //수정
  int updateById(Long bbsId, Bbs bbs);

  //여러건 삭제
  int deleteByIds(List<Long> bbsIds);

  int getTotalRecords();
}
