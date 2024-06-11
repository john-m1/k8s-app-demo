package com.metro.atwater.data.process;

import java.util.HashMap;

import com.metro.atwater.stats.ResultStats;

public class ProcessResultSummary {
	private String server;
	private String process;
	private HashMap<String,ResultStats> results;
	
	public ProcessResultSummary() {
		this.results = new HashMap<String,ResultStats>();
	}
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
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
