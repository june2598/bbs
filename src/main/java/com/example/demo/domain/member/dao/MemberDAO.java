package com.example.demo.domain.member.dao;

import com.example.demo.domain.entity.Member;

import java.util.Optional;

public interface MemberDAO {
  //가입
  Member insertMember(Member member);

  //회원 존재 유무
  boolean isExist(String email);

  //회원조회
  Optional<Member> findByMemberId(Long memberId);
  Optional<Member> findByEmail(String email);
}
