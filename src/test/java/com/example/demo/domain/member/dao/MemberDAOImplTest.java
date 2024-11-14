package com.example.demo.domain.member.dao;

import com.example.demo.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
class MemberDAOImplTest {

  @Autowired
  MemberDAO memberDAO;

  @Test
  @DisplayName("회원가입")
  void insertMember() {
    Member member = new Member();
    member.setEmail("test10@kh.com");
    member.setPasswd("1234");
    member.setNickname("별칭테스트");
    Member insertedMember = memberDAO.insertMember(member);
    log.info("insertedMember={}", insertedMember);

  }

  @Test
  @DisplayName("회원존재여부")
  void isExist() {
    boolean exist = memberDAO.isExist("test1@kh.com");
    Assertions.assertThat(exist).isEqualTo(true);
  }

  @Test
  void findByMemberId() {
    Optional<Member> optionalMember = memberDAO.findByMemberId(1L);
    if (optionalMember.isPresent()) {
      Member member = optionalMember.get();
      log.info("member={}", member);
    }
    optionalMember = memberDAO.findByMemberId(0L);
    if (!optionalMember.isPresent()) {
      log.info("회원없음");
    }
  }

  @Test
  void findByEmail() {
    Optional<Member> optionalMember = memberDAO.findByEmail("test1@kh.com");
    if (optionalMember.isPresent()) {
      Member member = optionalMember.get();
      log.info("member={}", member);
    }
    optionalMember = memberDAO.findByEmail("test1@naver.com");
    if (!optionalMember.isPresent()) {
      log.info("회원없음");
    }
  }
}