package com.metro.atwater.data.luns;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.metro.atwater.data.luns.Luns;

public class LunsRowMapper implements RowMapper<Luns> {
	public Luns mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Luns result = new Luns();
		
		result.setName(resultSet.getString(1));
		result.setDate(resultSet.getString(2));
		result.setFreeSpace(new Float(resultSet.getString(3)));
		result.setCapacity(new Float(resultSet.getString(4)));

		return result;
	}	

}
