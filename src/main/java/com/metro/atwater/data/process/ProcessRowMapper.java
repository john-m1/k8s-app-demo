package com.metro.atwater.data.process;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ProcessRowMapper implements RowMapper<Process> {

	public Process mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Process result = new Process();
		
		result.setServer(resultSet.getString(1));
		result.setProcess(resultSet.getString(2));
		result.setDate(resultSet.getString(3));
		result.setCpu(new Float(resultSet.getString(4)));
		result.setThread(new Integer(resultSet.getString(5)));
		result.setVsz(new Float(resultSet.getString(6)));
		result.setRss(new Float(resultSet.getString(7)));

		return result;
	}	

}
