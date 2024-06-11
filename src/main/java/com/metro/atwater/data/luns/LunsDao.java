package com.metro.atwater.data.luns;

import java.util.List;

import com.metro.atwater.data.luns.Luns;
import com.metro.atwater.stats.ResultStats;

public interface LunsDao {
	/*
	 * addTuple(...) - add a cpu result row
	 */
	public void addTuple(Luns tuple);
	
	/*
	 * selectAll() - return all rows from database
	 */
	public abstract List<Luns> selectAll();
	
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
	public abstract List<Integer> counts(String name);
	
	/*
	 * stats(...) - return statistic array
	 */
	public abstract List<ResultStats> stats(String name,String column);

}
