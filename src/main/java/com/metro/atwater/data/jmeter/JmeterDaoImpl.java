package com.metro.atwater.data.jmeter;

import java.util.List;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import com.metro.atwater.stats.CountRowMapper;
import com.metro.atwater.stats.ResultStats;
import com.metro.atwater.stats.ResultStatsRowMapper;


public class JmeterDaoImpl implements JmeterDao {
	
	private static final Logger logger = LoggerFactory.getLogger(JmeterDaoImpl.class);

	DataSource dataSource;
	
	@Required
	public void setDataSource(DataSource dataSource){
		logger.info("set datasource");
		this.dataSource = dataSource;
	}
	

	public void addTuple(Jmeter tuple) {
		JdbcTemplate addResult = new JdbcTemplate(dataSource);
		addResult.update("INSERT INTO JMETERSTATS (server,epoch,ts,elapsed,name,responseCode,responseMsg,success,latency,idle,connectTime) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
				new Object[]{tuple.getServer(),tuple.getEpoch() ,tuple.getTs(),tuple.getElapsed(),tuple.getName(),tuple.getResponseCode(),tuple.getResponseMsg(),tuple.isSuccess(),tuple.getLatency(),tuple.getIdle(),tuple.getConnectTime()});
	}

//	public List<Jmeter> selectPerson(String name, String surname) {
//		JdbcTemplate selectPerson = new JdbcTemplate(dataSource);
//		return selectPerson.query("SELECT NAME,SURNAME FROM PERSON_RECORD WHERE NAME=? AND SURNAME=?",
//				new Object[] {name,surname},
//				new JmeterRowMapper());
//	}

	public List<Jmeter> selectAll() {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		return selectResult.query("SELECT server,epoch,ts,elapsed,name,responseCode,responseMsg,success,latency,idle,connectTime FROM JMETERSTATS",
				new JmeterRowMapper());
	}
	
	public List<Integer> counts() {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		return selectResult.query("SELECT COUNT(success) FROM JMETERSTATS",
				new CountRowMapper());
	}

	public List<ResultStats> stats(String column) {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		return selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM JMETERSTATS",
				new ResultStatsRowMapper());
	}

	public void delTuple(Long epoch) {
		JdbcTemplate delResult = new JdbcTemplate(dataSource);
		delResult.update("DELETE FROM JMETERSTATS WHERE epoch=? ",
				new Object[]{epoch.toString()});		
	}

	public void deleteAll() {
		JdbcTemplate deleteAll = new JdbcTemplate(dataSource);
		deleteAll.update("DELETE FROM JMETERSTATS");
	}
	
	public boolean isAlive() {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		return (selectResult.query("SELECT 1 FROM JMETERSTATS",
				new CountRowMapper()) != null);
	}

}