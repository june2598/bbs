package com.example.demo.domain.bbs.svc;

import com.example.demo.domain.bbs.dao.ReplyBbsDAO;
import com.example.demo.domain.entity.ReplyBbs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyBbsSVCImpl implements ReplyBbsSVC{

  private final ReplyBbsDAO replyBbsDAO;

  @Override
  public Long save(ReplyBbs replyBbs) {

    return replyBbsDAO.save(replyBbs);
  }

  @Override
  public List<ReplyBbs> listAll(int page, Long bbsId) {
    return replyBbsDAO.listAll(page, bbsId);
  }

  @Override
  public int deleteById(Long replyId) {
    return replyBbsDAO.deleteById(replyId);
  }

  @Override
  public int updateById(Long replyId, ReplyBbs replyBbs) {
    return replyBbsDAO.updateById(replyId,replyBbs);
  }

  @Override
  public Optional<ReplyBbs> findById(Long replyId) {
    return replyBbsDAO.findById(replyId);
  }

  @Override
  public int getTotalReplyRecord(Long bbsId) {
    return replyBbsDAO.getTotalReplyRecord(bbsId);
  }
}
