create database ABAL;
use ABAL;
-- solaris
create table DATASOURCE ( name VARCHAR(20), ts DATETIME, freespace FLOAT(10,2), capacity FLOAT(10,2), PRIMARY KEY(name,ts));
create table ESXISRV ( server VARCHAR(20), ts DATETIME, memusage_gb FLOAT(10,2), cpuusage_mhz FLOAT(10,2), PRIMARY KEY(server,ts) );
create table ESXISRVLIMITS ( server VARCHAR(20), memlimit_gb FLOAT(10,2), cpulimit_mhz FLOAT(10,2), PRIMARY KEY(server) );

create table SERVERS ( server VARCHAR(20), ipaddr VARCHAR(18), vm BIT(1),  owner VARCHAR(100), jira VARCHAR(20), db VARCHAR(20), PRIMARY KEY(server) );

CREATE USER 'labauto'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'labauto';

-- TODO
-- AUTOMATE THIS INSERT FROM PROCESSING JSON CONFIG FILE 
-- 
