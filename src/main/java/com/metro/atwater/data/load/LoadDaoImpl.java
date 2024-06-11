package com.metro.atwater.data.load;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import com.metro.atwater.stats.CountRowMapper;
import com.metro.atwater.stats.ResultStats;
import com.metro.atwater.stats.ResultStatsRowMapper;

public class LoadDaoImpl implements LoadDao {
	private static final Logger logger = LoggerFactory.getLogger(LoadDaoImpl.class);

	DataSource dataSource;
	
	@Required
	public void setDataSource(DataSource dataSource){
		logger.info("set datasource");
		this.dataSource = dataSource;
	}

	@Override
	public void addTuple(Load tuple) {
		JdbcTemplate addResult = new JdbcTemplate(dataSource);
		addResult.update("INSERT INTO LOADMETRICS (server,ts,1min,5min,15min) VALUES (?,?,?,?,?)",
				new Object[]{tuple.getServer(),tuple.getTs(),tuple.getOne(),tuple.getFive(),tuple.getFifteen()});
	}

	@Override
	public List<Load> selectAll() {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		return selectResult.query("SELECT server,ts,1min,5min,15min FROM LOADMETRICS",
				new LoadRowMapper());
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
		deleteAll.update("DELETE FROM LOADMETRICS");
	}

	@Override
	public List<Integer> counts(String server) {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		List<Integer> rc = null;
		
		if (server != null) {
			rc = selectResult.query("SELECT COUNT(server) FROM LOADMETRICS WHERE server = '" + server + "'",
					new CountRowMapper());
		} else {
			rc = selectResult.query("SELECT COUNT(server) FROM LOADMETRICS",
					new CountRowMapper());
		}
		return rc;
	}

	@Override
	public List<ResultStats> stats(String server, String column) {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		List<ResultStats> rc = null;
		
		if (server != null) {
			rc = selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM LOADMETRICS WHERE server = '" + server + "'",
					new ResultStatsRowMapper());
		} else {
			rc = selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM LOADMETRICS",
					new ResultStatsRowMapper());
		}
		
		return rc;
	}

}
