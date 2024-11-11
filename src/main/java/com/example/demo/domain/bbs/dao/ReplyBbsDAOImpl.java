package com.example.demo.domain.bbs.dao;


import com.example.demo.domain.entity.ReplyBbs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor  //final 필드를 매개값으로 갖는 생성자 자동생성

public class ReplyBbsDAOImpl implements ReplyBbsDAO{

  private final NamedParameterJdbcTemplate template;

  @Override
  public Long save(ReplyBbs replybbs) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into replybbs (reply_id,bbs_id,comments,writer,cdate,udate) ");
    sql.append("values(replybbs_reply_id_seq.nextval,43, :writer, :comments, sysdate, sysdate)");

    SqlParameterSource param = new BeanPropertySqlParameterSource(replybbs);
    KeyHolder keyholder = new GeneratedKeyHolder();
    long rows = template.update(sql.toString(), param, keyholder, new String[]{"reply_id"});
    Number ridNumber = (Number) keyholder.getKeys().get("reply_id");
    long rid = ridNumber.longValue();
    return rid;
  }

  @Override
  public List<ReplyBbs> listAll() {
    //sql
    StringBuffer sql = new StringBuffer();
    sql.append("select reply_id, comments, writer, cdate, udate " );
    sql.append(" from replybbs " );
    sql.append("order by reply_id asc ");

    List<ReplyBbs> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(ReplyBbs.class));
    return list;
  }

  @Override
  public int deleteById(Long replyId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from replybbs ");
    sql.append("where reply_id = :replyId ");

    Map<String, Long> param = Map.of("replyId", replyId);
    int rows = template.update(sql.toString(), param);
    return rows;
  }

  @Override
  public int updateById(Long replyId, ReplyBbs replyBbs) {
    StringBuffer sql = new StringBuffer();
    sql.append("update replybbs ");
    sql.append("set comments = :comments, udate = sysdate ");
    sql.append("where reply_id = :replyId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("comments",replyBbs.getComments())
        .addValue("replyId",replyId);

    int rows = template.update(sql.toString(), param);
    return rows;
  }

  @Override
  public Optional<ReplyBbs> findById(Long replyId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select reply_id, comments, writer, cdate, udate ");
    sql.append(" from replybbs ");
    sql.append(" where reply_id = :replyId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("replyId", replyId);

    ReplyBbs replyBbs = null;

    try{
      replyBbs = template.queryForObject(
          sql.toString(),
          param,
          BeanPropertyRowMapper.newInstance(ReplyBbs.class));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
    return Optional.of(replyBbs);
  }
}
