--replybbs 테이블 삭제
drop table replybbs;
--reply_id 시퀀스 삭제
drop sequence replybbs_reply_id_seq;

--replybbs 테이블 생성
CREATE TABLE ReplyBBS (
    reply_id NUMBER(10) PRIMARY KEY,
    bbs_id NUMBER(10),
    comments CLOB,
    writer VARCHAR2(30),
    cdate TIMESTAMP,
    udate TIMESTAMP,
    FOREIGN KEY(bbs_id) REFERENCES BBS(bbs_id)
);

--reply_id 시퀀스 생성
create sequence replybbs_reply_id_seq;

insert into replybbs (reply_id,bbs_id,comments,writer,cdate)
            values(replybbs_reply_id_seq.nextval,43,'댓글내용1','작성자1',sysdate);
insert into replybbs (reply_id,bbs_id,comments,writer,cdate)
            values(replybbs_reply_id_seq.nextval,43,'댓글내용2','작성자2',sysdate);
insert into replybbs (reply_id,bbs_id,comments,writer,cdate)
            values(replybbs_reply_id_seq.nextval,141,'댓글내용3','작성자3',sysdate);
insert into replybbs (reply_id,bbs_id,comments,writer,cdate)
            values(replybbs_reply_id_seq.nextval,141,'댓글내용4','작성자4',sysdate);

commit;
--조회
select *
from replybbs;

--수정
set comments = '내용2', udate = sysdate
where reply_id = 1;



