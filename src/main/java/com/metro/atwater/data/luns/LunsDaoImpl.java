package com.metro.atwater.data.luns;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import com.metro.atwater.data.luns.LunsDaoImpl;
import com.metro.atwater.stats.ResultStats;
import com.metro.atwater.stats.ResultStatsRowMapper;

public class LunsDaoImpl implements LunsDao {
	private static final Logger logger = LoggerFactory.getLogger(LunsDaoImpl.class);

	DataSource dataSource;
	
	@Required
	public void setDataSource(DataSource dataSource){
		logger.info("set datasource");
		this.dataSource = dataSource;
	}
	
	
	@Override
	public void addTuple(Luns tuple) {
		// TODO Auto-generated method stub
		JdbcTemplate addResult = new JdbcTemplate(dataSource);
		addResult.update("INSERT INTO DATASOURCE (name,ts,freespace,capacity) VALUES (?,?,?,?)",
				new Object[]{tuple.getName(),tuple.getTs(),tuple.getFreeSpace(),tuple.getCapacity()});		
	}

	
	@Override
	public List<Luns> selectAll() {
		// TODO Auto-generated method stub
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		return selectResult.query("SELECT server,ts,freespace,capacity FROM DATASOURCE",
				new LunsRowMapper());
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
		deleteAll.update("DELETE FROM DATASOURCE");
	}

	
	@Override
	public List<Integer> counts(String name) {
		// TODO Auto-generated method stub
		throw new java.lang.UnsupportedOperationException("counts");		
	}

	
	@Override
	public List<ResultStats> stats(String name, String column) {
		// TODO Auto-generated method stub
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		List<ResultStats> rc = null;
		if ( name != null) {
			rc = selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM DATASOURCE WHERE name = '" + name + "'",
					new ResultStatsRowMapper());
		} else {
			rc = selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM DATASOURCE",
					new ResultStatsRowMapper());			
		}
		return rc;
	}

}
