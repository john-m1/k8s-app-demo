package com.metro.atwater.data.serverinfo;

import java.util.HashMap;
import java.util.List;

import com.metro.atwater.stats.ResultStats;

public interface ServerInfoDao {
	/*
	 * addTuple(...) - add a cpu result row
	 */
	public void addTuple(ServerInfo tuple);
	
	/*
	 * selectAll() - return all rows from database
	 */
	public abstract List<ServerInfo> selectAll();
	
	/*
	 * delTuple(...) - delete a result row(s) based on epoch
	 */
	public abstract void delTuple(String server);
	
	/*
	 * deleteAll() - delete all rows
	 */
	public abstract void deleteAll();
	
	/*
	 * index() - return a list of k,v 
	 */
	public abstract HashMap<String,List<String>> index();
	
	/*
	 * counts() - return number of samples entered for server
	 */
	public abstract List<Integer> counts(String server);
	
	/*
	 * stats(...) - return statistic array
	 */
	public abstract List<ResultStats> stats(String server);
}
