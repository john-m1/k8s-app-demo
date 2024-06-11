package com.metro.atwater.data.jmeter;

import java.util.List;

import com.metro.atwater.stats.ResultStats;


public  interface  JmeterDao {
		
	/*
	 * addTuple(...) - add a jmeter result row
	 */
	public void addTuple(Jmeter tuple);
	
	/*
	 * selectAll() - return all rows from database
	 */
	public abstract List<Jmeter> selectAll();
	
	/*
	 * delTuple(...) - delete a result row(s) based on epoch
	 */
	public abstract void delTuple(Long epoch);
	
	/*
	 * deleteAll() - delete all rows
	 */
	public abstract void deleteAll();
	
	/*
	 * counts() - return number of rows
	 */
	public abstract List<Integer> counts();
	
	/*
	 * stats(...) - return statistic array
	 */
	public abstract List<ResultStats> stats(String column);
	
	/*
	 * isAlive(...) - return true if db is alive else return false
	 */
	public abstract boolean isAlive();

}
