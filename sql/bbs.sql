--bbs 테이블 삭제
drop table bbs;
--bbs_id 시퀀스 삭제
drop sequence bbs_bbs_id_seq;

--bbs 테이블 생성
CREATE TABLE BBS (
    bbs_id number(10),
    writer varchar2(30) not null,
    title varchar2(100) not null,
    contents clob not null,
    cdate timestamp,
    udate timestamp,
    primary key (bbs_id)
);

--시퀀스 생성
create sequence bbs_bbs_id_seq;

insert into bbs (bbs_id,writer,title,contents,cdate)
            values(bbs_bbs_id_seq.nextval,'작성자1','제목1','내용1',sysdate);
insert into bbs (bbs_id,writer,title,contents,cdate)
            values(bbs_bbs_id_seq.nextval,'작성자2','제목2','내용2',sysdate);
insert into bbs (bbs_id,writer,title,contents,cdate)
            values(bbs_bbs_id_seq.nextval,'작성자3','제목3','내용3',sysdate);
insert into bbs (bbs_id,writer,title,contents,cdate)
            values(bbs_bbs_id_seq.nextval,'작성자4','제목4','내용4',sysdate);
insert into bbs (bbs_id,writer,title,contents,cdate)
            values(bbs_bbs_id_seq.nextval,'작성자5','제목5','내용5',sysdate);

--수정
set title = '제목2', contents = '내용2', udate = sysdate
where bbs_id = 1;

--조회
select bbs_id,writer,title,contents,cdate,udate
from bbs
order by bbs_id;

--삭제
delete from bbs where bbs_id = 1;


commit;

select * from bbs;


