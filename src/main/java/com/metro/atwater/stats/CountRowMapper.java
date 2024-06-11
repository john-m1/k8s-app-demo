package com.metro.atwater.stats;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CountRowMapper implements RowMapper<Integer> {
	public Integer mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Integer result = new Integer(resultSet.getString(1));		
		return result;
	}
}
