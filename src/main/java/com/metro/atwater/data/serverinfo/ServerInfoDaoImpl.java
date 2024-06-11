package com.metro.atwater.data.serverinfo;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import com.metro.atwater.stats.CountRowMapper;
import com.metro.atwater.stats.ResultStats;

public class ServerInfoDaoImpl implements ServerInfoDao {
	private static final Logger logger = LoggerFactory.getLogger(ServerInfoDaoImpl.class);

	DataSource dataSource;
	
	@Required
	public void setDataSource(DataSource dataSource){
		logger.info("set datasource");
		this.dataSource = dataSource;
	}

	@Override
	public void addTuple(ServerInfo tuple) {
		JdbcTemplate addResult = new JdbcTemplate(dataSource);
		addResult.update("INSERT INTO SERVERS (server,ipaddr,vm,owner,jira,db) VALUES (?,?,?,?,?,?)",
				new Object[]{tuple.getServer(),tuple.getIpaddr(),tuple.isVm(),tuple.getOwner(),tuple.getJira(),tuple.getDb()});
		
	}

	@Override
	public List<ServerInfo> selectAll() {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		return selectResult.query("SELECT server,ipaddr,vm,owner,jira,db FROM SERVERS",
				new ServerInfoRowMapper());
	}

	@Override
	public void delTuple(String server) {
		throw new java.lang.UnsupportedOperationException("delTuple");
		
	}

	@Override
	public void deleteAll() {
		JdbcTemplate deleteAll = new JdbcTemplate(dataSource);
		deleteAll.update("DELETE FROM SERVERS");		
	}

	@Override
	public HashMap<String, List<String>> index() {
		throw new java.lang.UnsupportedOperationException("index");
	}

	@Override
	public List<Integer> counts(String server) {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		List<Integer> rc = null;
		
		if (server != null) {
			// TODO: this is not necessary
			rc = selectResult.query("SELECT COUNT(server) FROM SERVERS WHERE server = '" + server + "'",
					new CountRowMapper());
		} else {
			rc = selectResult.query("SELECT COUNT(server) FROM SERVERS",
					new CountRowMapper());
		}
		return rc;
	}

	@Override
	public List<ResultStats> stats(String server) {
		throw new java.lang.UnsupportedOperationException("stats");
	}

}
