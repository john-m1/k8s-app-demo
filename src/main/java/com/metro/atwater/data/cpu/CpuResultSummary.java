package com.metro.atwater.data.cpu;

import java.util.HashMap;

import com.metro.atwater.stats.ResultStats;

public class CpuResultSummary {
	private String server;
	private HashMap<String,ResultStats> results;
	
	public CpuResultSummary() {
		this.setResults(new HashMap<String,ResultStats>());
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public HashMap<String,ResultStats> getResults() {
		return results;
	}

	public void setResults(HashMap<String,ResultStats> results) {
		this.results = results;
	}

	public void add(String field, ResultStats rs) {
		this.results.put(field, rs);
	}
	
	public void del(String field) {
		this.results.remove(field);
	}

}
