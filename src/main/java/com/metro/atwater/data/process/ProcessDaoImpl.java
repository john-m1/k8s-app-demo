package com.metro.atwater.data.process;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import com.metro.atwater.stats.CountRowMapper;
import com.metro.atwater.stats.ResultStats;
import com.metro.atwater.stats.ResultStatsRowMapper;

public class ProcessDaoImpl implements ProcessDao {
	private static final Logger logger = LoggerFactory.getLogger(ProcessDaoImpl.class);

	DataSource dataSource;
	
	@Required
	public void setDataSource(DataSource dataSource){
		logger.info("set datasource");
		this.dataSource = dataSource;
	}

	@Override
	public void addTuple(Process tuple) {
		JdbcTemplate addResult = new JdbcTemplate(dataSource);
		addResult.update("INSERT INTO PROCESSMETRICS (server,process,ts,cpu,thread,vszmb,rssmb) VALUES (?,?,?,?,?,?,?)",
				new Object[]{tuple.getServer(),tuple.getProcess(),tuple.getTs(),tuple.getCpu(),tuple.getThread(),tuple.getVsz(),tuple.getRss()});
	}

	@Override
	public List<Process> selectAll() {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		return selectResult.query("SELECT server,process,ts,cpu,thread,vszmb,rssmb FROM PROCESSMETRICS",
				new ProcessRowMapper());
	}

	@Override
	public void delTuple(Long epoch) {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException("delTuple");
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		JdbcTemplate deleteAll = new JdbcTemplate(dataSource);
		deleteAll.update("DELETE FROM PROCESSMETRICS");

	}

	@Override
	public HashMap<String,List<String>> index() {
		HashMap<String,List<String>> rc = new HashMap<String,List<String>>();
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		List<String> distinctServer = null;
		List<String> distinctProcess = null;

		distinctServer = selectResult.query("SELECT DISTINCT server FROM PROCESSMETRICS",
				new IndexRowMapper());
		
		for (String s : distinctServer) {
			distinctProcess = selectResult.query("SELECT DISTINCT process FROM PROCESSMETRICS WHERE server = '" + s + "'",
					new IndexRowMapper());
			rc.put(s, distinctProcess);
		}
		return rc;
	}
	
	@Override
	public List<Integer> counts(String server) {
		// TODO Auto-generated method stub
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		List<Integer> rc = null;
		
		if (server != null) {
			rc = selectResult.query("SELECT COUNT(server) FROM PROCESSMETRICS WHERE server = '" + server + "'",
					new CountRowMapper());
		} else {
			rc = selectResult.query("SELECT COUNT(server) FROM PROCESSMETRICS",
					new CountRowMapper());
		}
		return rc;
	}

	@Override
	public List<ResultStats> stats(String server, String process, String column) {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		List<ResultStats> rc = null;
		//logger.info("server: " + server + " process: " + process + " column: " + column);
		
		if (server != null) {
			if (process != null) {
				rc = selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM PROCESSMETRICS WHERE server = '" + server + "' AND process = '" + process + "'",
						new ResultStatsRowMapper());
			} else {
				rc =  selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM PROCESSMETRICS WHERE server = '" + server + "'",
						new ResultStatsRowMapper());
			}
		} else {
			rc = selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM PROCESSMETRICS",
					new ResultStatsRowMapper());
		}
		
		return rc;
	}

}
