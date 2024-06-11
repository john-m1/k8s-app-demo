 package com.metro.atwater.data.cpu;

import java.util.List;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import com.metro.atwater.stats.CountRowMapper;
import com.metro.atwater.stats.ResultStats;
import com.metro.atwater.stats.ResultStatsRowMapper;

public class CpuDaoImpl implements CpuDao {

	private static final Logger logger = LoggerFactory.getLogger(CpuDaoImpl.class);

	DataSource dataSource;
	
	@Required
	public void setDataSource(DataSource dataSource){
		logger.info("set datasource");
		this.dataSource = dataSource;
	}

	@Override
	public void addTuple(Cpu tuple) {
		JdbcTemplate addResult = new JdbcTemplate(dataSource);
		addResult.update("INSERT INTO CPUMETRICS (server,ts,usr,sys,wio,idle) VALUES (?,?,?,?,?,?)",
				new Object[]{tuple.getServer(),tuple.getTs(),tuple.getUsr(),tuple.getSys(),tuple.getWio(),tuple.getIdle()});
	}

	@Override
	public List<Cpu> selectAll() {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		return selectResult.query("SELECT server,ts,usr,sys,wio,idle FROM CPUMETRICS",
				new CpuRowMapper());
	}

	@Override
	public void delTuple(Long epoch) {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException("delTuple");
	}

	@Override
	public void deleteAll() {
		JdbcTemplate deleteAll = new JdbcTemplate(dataSource);
		deleteAll.update("DELETE FROM CPUMETRICS");
	}

	@Override
	public List<Integer> counts(String server) {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		List<Integer> rc = null;
		if ( server != null) {
			rc = selectResult.query("SELECT COUNT(usr) FROM CPUMETRICS WHERE server = '" + server + "'",
					new CountRowMapper());
		} else {
			rc = selectResult.query("SELECT COUNT(usr) FROM CPUMETRICS",
					new CountRowMapper());
		}
		return rc;
				
	}

	@Override
	public List<ResultStats> stats(String server, String column) {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		List<ResultStats> rc = null;
		if ( server != null) {
			rc = selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM CPUMETRICS WHERE server = '" + server + "'",
					new ResultStatsRowMapper());
		} else {
			rc = selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM CPUMETRICS",
					new ResultStatsRowMapper());			
		}
		return rc;
				
	}

}
