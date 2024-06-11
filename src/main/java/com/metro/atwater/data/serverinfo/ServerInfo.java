package com.metro.atwater.data.serverinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerInfo {
	private static final Logger logger = LoggerFactory.getLogger(ServerInfo.class);
	
	private String server;
	private String ipaddr;
	private boolean vm;
	private String owner;
	private String jira;
	private String db;
	
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public String getServer() {
		return this.server;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		// TODO: verify hostname and IP Address resolution
		logger.info("IP Address not verified");
		this.ipaddr = ipaddr;
	}

	public boolean isVm() {
		return vm;
	}

	public void setVm(boolean vm) {
		this.vm = vm;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getJira() {
		return jira;
	}

	public void setJira(String jira) {
		this.jira = jira;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}
	
	
}
