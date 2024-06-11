package com.metro.atwater.data.serverinfo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ServerInfoRowMapper implements RowMapper<ServerInfo> {

	public ServerInfo mapRow(ResultSet resultSet, int arg1) throws SQLException {
		ServerInfo result = new ServerInfo();
		
		result.setServer(resultSet.getString(1));
		result.setIpaddr(resultSet.getString(2));
		result.setVm(resultSet.getBoolean("vm"));
		result.setOwner(resultSet.getString(4));
		result.setJira(resultSet.getString(5));
		result.setDb(resultSet.getString(6));

		return result;
	}	
}
