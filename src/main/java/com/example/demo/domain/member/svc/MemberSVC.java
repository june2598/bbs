package com.example.demo.domain.member.svc;

import com.example.demo.domain.entity.Member;

import java.util.Optional;

public interface MemberSVC {
  //가입
  Member join(Member member);

  //회원존재유무
  boolean isMember(String email);

  //회원조회
  Optional<Member> findByMemberId(Long memberId);
  Optional<Member> findByEmail(String email);


}
