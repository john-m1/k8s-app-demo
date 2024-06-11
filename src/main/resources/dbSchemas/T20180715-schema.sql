create database testdb;
use testdb;
-- solaris
create table CPUMETRICS ( server VARCHAR(20), ts DATETIME, usr FLOAT(5,2), sys FLOAT(5,2), wio FLOAT(5,2), idle FLOAT(5,2), PRIMARY KEY(server,ts));
create table LOADMETRICS ( server VARCHAR(20), ts DATETIME, 1min FLOAT(5,2), 5min FLOAT(5,2), 15min FLOAT(5,2), PRIMARY KEY(server,ts) );
create table PROCESSMETRICS ( server VARCHAR(20), process VARCHAR(20), ts DATETIME, cpu FLOAT(5,2), thread INT, vszmb FLOAT(5,2), rssmb FLOAT(5,2), PRIMARY KEY(server,process,ts) );
create table SUT ( server VARCHAR(20), ipaddress VARCHAR(20), description VARCHAR(40), PRIMARY KEY(server) );
create table METRICS ( metric VARCHAR(20), description VARCHAR(40), PRIMARY KEY(metric) );

-- timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,Latency,IdleTime,Connect,"NPANXX","LINE"
create table JMETERSTATS (server VARCHAR(20), epoch BIGINT(20), ts DATETIME, elapsed INT, name VARCHAR(100), responseCode VARCHAR(40), responseMsg VARCHAR(40), success BOOLEAN, latency INT, idle INT, connectTime INT);
create index id on JMETERSTATS(epoch) using HASH;


CREATE USER 'monitor'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'monitor';

-- TODO
-- AUTOMATE THIS INSERT FROM PROCESSING JSON CONFIG FILE 
-- 
INSERT INTO SUT VALUES ('v911r-adm1','','');
INSERT INTO SUT VALUES ('v911r-adm2','','');
INSERT INTO SUT VALUES ('v911r-covgapp1','','');
INSERT INTO SUT VALUES ('v911r-covgapp2','','');
INSERT INTO SUT VALUES ('v911s-adm1','','');
INSERT INTO SUT VALUES ('v911s-adm2','','');
INSERT INTO SUT VALUES ('v911s-covgapp1','','');
INSERT INTO SUT VALUES ('v911s-covgapp2','','');

INSERT INTO METRICS VALUES ('CPU', 'CPU TIME STATISTICS');
INSERT INTO METRICS VALUES ('LOAD', 'LOAD AVG STATISTICS');
INSERT INTO METRICS VALUES ('PROCESS', 'PROCESS STATISTICS');
