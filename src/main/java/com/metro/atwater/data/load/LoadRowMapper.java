package com.metro.atwater.data.load;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.metro.atwater.data.load.Load;

public class LoadRowMapper implements RowMapper<Load> {

	public Load mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Load result = new Load();
		
		result.setServer(resultSet.getString(1));
		result.setDate(resultSet.getString(2));
		result.setOne(new Float(resultSet.getString(3)));
		result.setFive(new Float(resultSet.getString(4)));
		result.setFifteen(new Float(resultSet.getString(5)));

		return result;
	}	


}
