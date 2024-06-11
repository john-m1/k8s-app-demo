package com.metro.atwater.data.jmeter;

import java.sql.Timestamp;

public class Jmeter {
	//private Long id;
	private String server;
	private Long epoch; // ms
	private String name;
	private Integer elapsed; // ms
	private String responseCode; 
	private String responseMsg;
	private String success; 
	private Integer latency; // ms
	private Integer idle;  // ms
	private Integer connectTime; //ms
	
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public Long getEpoch() {
		return epoch;
	}
	
	public void setEpoch(Long epoch) {
		this.epoch = epoch;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getElapsed() {
		return elapsed;
	}
	public void setElapsed(Integer elapsed) {
		this.elapsed = elapsed;
	}
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	
	public Boolean isSuccess() {
		return (this.success == "true");
	}
	public void setSuccess(String success) {
		if ( success == "true" || success == "false") {
			this.success = success;
		}
		// TODO: handle error 
	}
	
	public int getLatency() {
		return latency;
	}
	public void setLatency(Integer latency) {
		this.latency = latency;
	}
	
	public int getIdle() {
		return idle;
	}
	public void setIdle(Integer idle) {
		this.idle = idle;
	}
	
	public int getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(Integer connectTime) {
		this.connectTime = connectTime;
	}

	public Timestamp getTs() {
		return new Timestamp(this.epoch);
	}

	
}