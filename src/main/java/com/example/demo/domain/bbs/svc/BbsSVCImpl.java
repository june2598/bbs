package com.example.demo.domain.bbs.svc;

import com.example.demo.domain.bbs.dao.BbsDAO;
import com.example.demo.domain.entity.Bbs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class BbsSVCImpl implements BbsSVC{

  private final BbsDAO bbsDAO;

  @Override
  public Long save(Bbs bbs) {
    return bbsDAO.save(bbs);
  }

  @Override
  public List<Bbs> listAll() {
    return bbsDAO.listAll();
  }

  @Override
  public Optional<Bbs> listById(Long bbsId) {
    return bbsDAO.findById(bbsId);
  }

  @Override
  public int deleteById(Long bbsId) {
    return bbsDAO.deleteById(bbsId);
  }

  @Override
  public int updateById(Long bbsId, Bbs bbs) {
    return bbsDAO.updateById(bbsId,bbs);
  }

  @Override
  public int deleteByIds(List<Long> bbsIds) {
    return bbsDAO.deleteByIds(bbsIds);
  }
}
