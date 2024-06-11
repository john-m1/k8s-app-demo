package com.metro.atwater.data.esxi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class EsxiRowMapper implements RowMapper<Esxi> {
	public Esxi mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Esxi result = new Esxi();
		
		result.setServer(resultSet.getString(1));
		result.setDate(resultSet.getString(2));
		result.setMemoryUsed(new Float(resultSet.getString(3)));
		result.setCpuUsageMhz(new Float(resultSet.getString(4)));

		return result;
	}	

}
