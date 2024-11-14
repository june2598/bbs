--테이블 삭제
drop table member;
--시퀀스 삭제
drop sequence member_member_id_seq;

create table member (
    member_id   number,         --내부 관리 아이디
    email       varchar2(50),   --로그인 아이디
    passwd      varchar2(12),   --로그인 비밀번호
    nickname    varchar2(30)   --별칭
);

--기본키생성
alter table member add Constraint member_member_id_pk primary key (member_id);

--제약조건
alter table member modify email constraint member_email_uk unique;
alter table member modify email constraint member_email_nn not null;

--시퀀스
create sequence member_member_id_seq;

--샘플 데이터
insert into member (member_id, email, passwd, nickname)
values(member_member_id_seq.nextval, 'test1@kh.com', 12341, '별칭1');
insert into member (member_id, email, passwd, nickname)
values(member_member_id_seq.nextval, 'test2@kh.com', 12342, '별칭2');
insert into member (member_id, email, passwd, nickname)
values(member_member_id_seq.nextval, 'test3@kh.com', 12343, '별칭3');
insert into member (member_id, email, passwd, nickname)
values(member_member_id_seq.nextval, 'test4@kh.com', 12344, '별칭4');
insert into member (member_id, email, passwd, nickname)
values(member_member_id_seq.nextval, 'test5@kh.com', 12345, '별칭5');

commit;


select *
from member;

select count(*)
from member
where email = 'test1@kh.com';

select member_id,email,passwd,nickname
from member
where member_id = 1;