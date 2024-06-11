package com.metro.atwater.data.cpu;

import java.util.List;

import com.metro.atwater.stats.ResultStats;

public interface CpuDao {

	/*
	 * addTuple(...) - add a cpu result row
	 */
	public void addTuple(Cpu tuple);
	
	/*
	 * selectAll() - return all rows from database
	 */
	public abstract List<Cpu> selectAll();
	
	/*
	 * delTuple(...) - delete a result row(s) based on epoch
	 */
	public abstract void delTuple(Long epoch);
	
	/*
	 * deleteAll() - delete all rows
	 */
	public abstract void deleteAll();
	
	/*
	 * counts() - return number of samples entered for server
	 */
	public abstract List<Integer> counts(String server);
	
	/*
	 * stats(...) - return statistic array
	 */
	public abstract List<ResultStats> stats(String server,String column);

}
