drop database if exists mldn;
create database mldn character set utf8;
use mldn;
create table member(
 mid varchar(50),
 name varchar(50),
 age int,
 salary double,
 birthday datetime,
 note text,
 constraint pk_mid primary key(mid)
)engine=innodb;