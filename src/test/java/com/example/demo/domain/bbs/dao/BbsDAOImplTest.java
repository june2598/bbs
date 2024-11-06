package com.example.demo.domain.bbs.dao;


import com.example.demo.domain.entity.Bbs;
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

public class BbsDAOImplTest {

  @Autowired  //해당타입의 객체를 찾아서 자동으로 주입
  BbsDAO bbsDAO;

  @Test
  @DisplayName("게시글등록")
  void save() {
    log.info("save()호출됨");
    Bbs bbs = new Bbs();
    bbs.setTitle("제목224");
    bbs.setWriter("작성자24");
    bbs.setContents("내용4334");
    Long bid = bbsDAO.save(bbs);
    log.info("게시글아이디={}", bid);
  }

  @Test
  @DisplayName("게시글목록")
  void listAll() {
    List<Bbs> list = bbsDAO.listAll();
    for (Bbs bbs : list) {
      log.info("bbs={}", bbs);
    }
  }

  @Test
  @DisplayName("게시글조회")
  void findById() {
    //조회할 게시글 ID를 변수로 저장
    Long bbsId = 81L;
    //bbsDAO의 findbyId를 호출, bbsId에 해당하는 게시글을 조회함
    //결과는 존재할수도 있고, 존재하지 않을수도있음
    Optional<Bbs> bbs = bbsDAO.findById(bbsId);
    //optionalBbs 게시글을 가져오는데, 존재하지 않으면 NoSuchElementException발생 -> 업데이트 성공여부 체크
    Bbs findedBbs = bbs.orElseThrow();
    //존재한다면 로그에 조회한 데이터가 찍힘
    log.info("findedBbs={}", findedBbs);
    //조회한 Id의 데이터가 예상과 일치하는지 체크
    Assertions.assertThat(findedBbs.getTitle()).isEqualTo("제목225");
    Assertions.assertThat(findedBbs.getWriter()).isEqualTo("작성자3");
  }

  @Test
  @DisplayName("게시글단건삭제")
  void deleteById() {
    //삭제하고자 하는 게시글 ID를 변수로 저장
    Long bbsId = 101L;
    //bbsDAO객체의 deleteById 메소드를 호출해 101L에 해당하는 게시글을 삭제후 삭제한 행의 수를 rows 변수에 저장
    int rows = bbsDAO.deleteById(bbsId);
    //1건 삭제 했기때문에 1이 로그에 출력되어야 함
    log.info("rows={}", rows);
    Assertions.assertThat(rows).isEqualTo(1); // 잘돌아가면 1, 아니면 0

  }

  @Test
  @DisplayName("게시글수정")
  void updateById(){
    //수정하고자 하는 게시글ID를 변수로 저장
    Long bbsId = 81L;
    //새로운 Bbs 객체를 생성해 bbs변수에 저장, 이 객체는 업데이트할 게시글의 정보를 담을 예정
    Bbs bbs = new Bbs();
    //게시글 제목 및 내용을 수정하는 부분
    bbs.setTitle("제목225");
    bbs.setContents("내용321");
    //bbsDAO객체의 updateById 메소드를 호출해 bbsId(81L)에 해당하는 게시글을 위의 새로운 bbs 객체의 정보로 업데이트
    //업데이트된 행의 수를 rows 변수에 저장 ->성공하면 rows=1
    int rows = bbsDAO.updateById(bbsId, bbs);

    //bbsDAO의 findbyId를 호출, bbsId에 해당하는 게시글을 조회함
    //결과는 존재할수도 있고, 존재하지 않을수도있음
    Optional<Bbs> optionalBbs = bbsDAO.findById(bbsId);
    //결과가 존재한다면 게시글의 정보가 로그로 출력될것
    log.info("udatedBbs={}", optionalBbs);
    //optionalBbs 게시글을 가져오는데, 존재하지 않으면 NoSuchElementException발생 -> 업데이트 성공여부 체크
    Bbs findedBbs = optionalBbs.orElseThrow();
    //수정된 제목과 내용이 예상과 일치하는지 테스트
    Assertions.assertThat(findedBbs.getTitle()).isEqualTo("제목225");
    Assertions.assertThat(findedBbs.getContents()).isEqualTo("내용321");
  }

  @Test
  @DisplayName("게시글 여러건 삭제")
  void deleteByIds(){
    //삭제할 게시글의 id가 여러건이기 때문에, 리스트에 저장. List.of()를 사용해 불변 리스트생성
    List<Long> bbsIds = List.of(61L, 62L);
    //deleteByIds 호출해 bbsIds에 포함된 id를 기반으로 여러게시글 삭제, 삭제된 행의 수를 rows에 저장
    int rows = bbsDAO.deleteByIds((bbsIds));
    //기대치와 이론값 비교 : 2면 삭제가 잘된것 (아이디 두개를 담았기때문)
    Assertions.assertThat(rows).isEqualTo(2);
    //rows의 값(삭제된 행의 수) 확인
    log.info("rows={}", rows);
  }

}
