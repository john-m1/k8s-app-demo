package com.metro.atwater.data.process;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IndexRowMapper implements RowMapper<String> {

	public String mapRow(ResultSet resultSet, int arg1) throws SQLException {
		return resultSet.getString(1);
	}	

}
