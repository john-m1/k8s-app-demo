package com.metro.atwater.data.jmeter;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class JmeterRowMapper implements RowMapper<Jmeter> {

	public Jmeter mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Jmeter result = new Jmeter();
		
		result.setServer(resultSet.getString(1));
		result.setEpoch(new Long(resultSet.getString(2)));
		//result.setTs(resultSet.getString(3));
		result.setElapsed(new Integer(resultSet.getString(4)));
		result.setName(resultSet.getString(5));
		result.setResponseCode(resultSet.getString(6));
		result.setResponseMsg(resultSet.getString(7));
		result.setSuccess(resultSet.getString(8));
		result.setLatency(new Integer(resultSet.getString(9)));
		result.setIdle(new Integer(resultSet.getString(10)));
		result.setConnectTime(new Integer(resultSet.getString(11)));
		
		return result;
	}	
}