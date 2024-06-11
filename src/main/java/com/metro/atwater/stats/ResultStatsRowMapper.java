package com.metro.atwater.stats;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ResultStatsRowMapper implements RowMapper<ResultStats> {
	public ResultStats mapRow(ResultSet resultSet, int arg1) throws SQLException {
		ResultStats result = new ResultStats();
				
		result.setMinima(new Float(resultSet.getString(1)));
		result.setMaxima(new Float(resultSet.getString(2)));
		result.setAverage(new Double(resultSet.getString(3)));
		result.setStddev(new Double(resultSet.getString(4)));
		
		return result;
	}
}
