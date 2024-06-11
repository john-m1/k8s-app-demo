package com.metro.atwater.data.cpu;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CpuRowMapper implements RowMapper<Cpu> {

	public Cpu mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Cpu result = new Cpu();
		
		result.setServer(resultSet.getString(1));
		result.setDate(resultSet.getString(2));
		result.setUsr(new Float(resultSet.getString(3)));
		result.setSys(new Float(resultSet.getString(4)));
		result.setWio(new Float(resultSet.getString(5)));
		result.setIdle(new Float(resultSet.getString(6)));

		return result;
	}	
}
