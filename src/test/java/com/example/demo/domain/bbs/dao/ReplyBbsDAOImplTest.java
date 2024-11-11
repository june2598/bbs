package com.example.demo.domain.bbs.dao;

import com.example.demo.domain.entity.ReplyBbs;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class ReplyBbsDAOImplTest {

  @Autowired
  ReplyBbsDAO replyBbsDAO;

  @Test
  @DisplayName("댓글등록")
  void save() {
    log.info("save()호출됨");
    ReplyBbs replyBbs = new ReplyBbs();
    replyBbs.setComments("댓글내용2");
    replyBbs.setWriter("댓글작성자1");
    Long rid = replyBbsDAO.save(replyBbs);
    log.info("댓글아이디={}", rid);
  }


  @Test
  @DisplayName("댓글목록")
  void listAll() {
    List<ReplyBbs> list = replyBbsDAO.listAll();
    for (ReplyBbs replyBbs : list) {
      log.info("replybbs={}", replyBbs);
    }
  }

  @Test
  @DisplayName("댓글 삭제")
  void deleteById() {
    Long replyId = 21L;
    int rows = replyBbsDAO.deleteById(replyId);
    log.info("rows={}", rows);
    Assertions.assertThat(rows).isEqualTo(1);
  }

  @Test
  @DisplayName("댓글 수정")
  void updateById() {
    Long replyId = 1L;
    ReplyBbs replyBbs = new ReplyBbs();
    replyBbs.setComments("댓글2422");
    int rows = replyBbsDAO.updateById(replyId, replyBbs);
    log.info("rows={}", rows);
    Assertions.assertThat(rows).isEqualTo(1);

  }

  @Test
  @DisplayName("댓글 조회")
  void findById() {
    Long replyId = 1L;
    Optional<ReplyBbs> replyBbs = replyBbsDAO.findById(replyId);
    ReplyBbs findedReplyBbs = replyBbs.orElseThrow();
    log.info("findedReplyBbs={}", findedReplyBbs);
    Assertions.assertThat(findedReplyBbs.getComments()).isEqualTo("댓글2422");
    Assertions.assertThat(findedReplyBbs.getWriter()).isEqualTo("작성자1");
  }
}