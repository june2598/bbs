package com.example.demo.domain.bbs.dao;

import com.example.demo.domain.entity.Bbs;
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
@RequiredArgsConstructor
public class BbsDAOImpl implements BbsDAO {

  private final NamedParameterJdbcTemplate template;
  @Override
  public Long save(Bbs bbs) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into bbs(bbs_id,writer,title,contents,cdate,udate) ");
    sql.append("values(bbs_bbs_id_seq.nextval, :writer, :title, :contents, sysdate, sysdate)");

    SqlParameterSource param = new BeanPropertySqlParameterSource(bbs);
    KeyHolder keyholder = new GeneratedKeyHolder();
    long rows = template.update(sql.toString(), param, keyholder, new String[]{"bbs_id"});
    Number bidNumber = (Number)keyholder.getKeys().get("bbs_id");
    long bid = bidNumber.longValue();
    return bid;
  }

  @Override
  public List<Bbs> listAll() {
    //sql
    StringBuffer sql = new StringBuffer();
    sql.append("select bbs_id,writer,title,contents,cdate,udate " );
    sql.append(" from bbs " );
    sql.append("order by bbs_id desc ");

    //db요청
    List<Bbs> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Bbs.class));
    return list;
  }

  @Override
  public Optional<Bbs> findById(Long bbsId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select bbs_id,writer,title,contents,cdate,udate " );
    sql.append(" from bbs ");
    sql.append(" where bbs_id = :bbsId ");

    SqlParameterSource param = new MapSqlParameterSource()
                  .addValue("bbsId", bbsId);

    Bbs bbs = null;
    try {
      bbs = template.queryForObject(
          sql.toString(),
          param,
          BeanPropertyRowMapper.newInstance(Bbs.class));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
    return Optional.of(bbs);
  }

  @Override
  public int deleteById(Long bbsId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from bbs ");
    sql.append("where bbs_id = :bbsId ");

    Map<String, Long> param = Map.of("bbsId", bbsId);
    int rows = template.update(sql.toString(), param);
    return rows;
  }

  @Override
  public int updateById(Long bbsId, Bbs bbs) {
    StringBuffer sql = new StringBuffer();
    sql.append("update bbs ");
    sql.append("set title = :title, contents = :contents, udate = sysdate ");
    sql.append("where bbs_id = :bbsId ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("title",bbs.getTitle())
        .addValue("contents",bbs.getContents())
        .addValue("bbsId",bbsId);



    int rows = template.update(sql.toString(), param);

    return rows;

  }

  @Override
  public int deleteByIds(List<Long> bbsIds) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from bbs ");
    sql.append(" where bbs_id in (:bbsIds) ");

    Map<String, List<Long>> param = Map.of("bbsIds", bbsIds);
    int rows = template.update(sql.toString(),param);

    return rows;
  }
}
