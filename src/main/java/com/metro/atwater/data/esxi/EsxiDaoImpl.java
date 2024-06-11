package com.metro.atwater.data.esxi;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import com.metro.atwater.data.esxi.EsxiRowMapper;
import com.metro.atwater.stats.ResultStats;
import com.metro.atwater.stats.ResultStatsRowMapper;

public class EsxiDaoImpl implements EsxiDao {

	private static final Logger logger = LoggerFactory.getLogger(EsxiDaoImpl.class);

	DataSource dataSource;
	
	@Required
	public void setDataSource(DataSource dataSource){
		logger.info("set datasource");
		this.dataSource = dataSource;
	}

	@Override
	public void addTuple(Esxi tuple) {
		JdbcTemplate addResult = new JdbcTemplate(dataSource);
		addResult.update("INSERT INTO ESXISRV (server,ts,memusage_gb,cpuusage_mhz) VALUES (?,?,?,?)",
				new Object[]{tuple.getServer(),tuple.getTs(),tuple.getMemoryUsed(),tuple.getCpuUsageMhz()});		
	}

	@Override
	public List<Esxi> selectAll() {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		return selectResult.query("SELECT server,ts,memusage_gb,cpuusage_mhz FROM ESXISRV",
				new EsxiRowMapper());
	}

	@Override
	public void delTuple(Long epoch) {
		throw new java.lang.UnsupportedOperationException("delTuple");		
	}

	@Override
	public void deleteAll() {
		JdbcTemplate deleteAll = new JdbcTemplate(dataSource);
		deleteAll.update("DELETE FROM ESXISRV");
	}

	@Override
	public List<Integer> counts(String name) {
		throw new java.lang.UnsupportedOperationException("counts");		
	}

	@Override
	public List<ResultStats> stats(String server, String column) {
		JdbcTemplate selectResult = new JdbcTemplate(dataSource);
		List<ResultStats> rc = null;
		if ( server != null) {
			rc = selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM ESXISRV WHERE name = '" + server + "'",
					new ResultStatsRowMapper());
		} else {
			rc = selectResult.query("SELECT min("+column+"),max("+column+"),avg("+column+"),0 FROM ESXISRV",
					new ResultStatsRowMapper());			
		}
		return rc;
	}

}
