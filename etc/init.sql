use study;
/* 테스트 테이블 구성 */
 CREATE TABLE `test` (
  `TEST_SEQ` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '테스트 고유번호',
  `TITLE` varchar(200) NOT NULL COMMENT '제목',
  `CONTENTS` text NOT NULL COMMENT '내용',
   PRIMARY KEY (`TEST_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='테스트 테이블';
 
 DELETE FROM test;
 SELECT * FROM test;
 
/* 회원 테이블 구성 */
 CREATE TABLE `user` (
  `USER_SEQ` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '회원 고유번호',
  `USER_ID` varchar(20) NOT NULL COMMENT '회원 아이디',
  `USER_PW` varchar(200) NOT NULL COMMENT '회원 비밀번호',
  `NAME` varchar(50) NOT NULL COMMENT '회원 이름',
  `EMAIL` varchar(200) DEFAULT NULL COMMENT '메일주소',
  `REG_SEQ` int(11) NOT NULL COMMENT '등록자 고유번호',
  `REG_DT` varchar(14) NOT NULL COMMENT '등록일시',
  `MOD_SEQ` int(11) NOT NULL COMMENT '수정자 고유번호',
  `MOD_DT` varchar(14) NOT NULL COMMENT '수정일시',
   PRIMARY KEY (`USER_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='회원 마스터';
 
 INSERT INTO user (USER_ID, USER_PW, NAME, EMAIL, REG_SEQ, REG_DT, MOD_SEQ, MOD_DT)
		VALUES('test', 'test', 'testName', 'test@crewmate.co.kr', 0, '20180402', 0, '20180402');
 
 SELECT * FROM user;
 
 DELETE FROM user WHERE user_seq=16;
 
/* 인증이력 테이블 구성 */
CREATE TABLE `auth_hist` (
  `AUTH_HIST_SEQ` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '인증이력고유번호',
  `USER_SEQ` int(11) unsigned DEFAULT NULL COMMENT '회원고유번호',
  `USER_ID` varchar(20) NOT NULL,
  `REG_DT` varchar(14) NOT NULL COMMENT '등록일시',
  `AUTH_TYPE` varchar(20) NOT NULL COMMENT '인증유형 - LOGIN, LOGOUT',
  `SUCCESS_YN` varchar(1) NOT NULL COMMENT '성공여부(Y/N)',
  `IP` varchar(100) NOT NULL COMMENT '요청 아이피',
  `USER_AGENT` varchar(500) NOT NULL COMMENT '접속환경',
  PRIMARY KEY (`AUTH_HIST_SEQ`),
  KEY `FK_USER_AUTH_HIST_idx` (`USER_SEQ`),
  CONSTRAINT `FK_USER_AUTH_HIST` FOREIGN KEY (`USER_SEQ`) REFERENCES `user` (`USER_SEQ`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='인증이력';


SELECT * FROM auth_hist;
DELETE FROM AUTH_HIST;