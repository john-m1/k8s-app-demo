package com.metro.atwater.data.esxi;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Esxi {
	private static final Logger logger = LoggerFactory.getLogger(Esxi.class);

	private String server;
	private String date;
	private float memoryUsed;
	private float cpuUsageMhz;
	Long epoch;
	
	public String getServer() {
		return server;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date javaDate = null;
		try {
			javaDate = df.parse(this.date);
		} catch (ParseException e) {
			this.epoch = System.currentTimeMillis();
			logger.error("failed to convert provided string " + this.date + " into a java date object, using system time - " + this.epoch);
			e.printStackTrace();
		}
		this.epoch = javaDate.getTime();
	}

	public void setServer(String server) {
		this.server = server;
	}

	public float getMemoryUsed() {
		return memoryUsed;
	}
	
	public void setMemoryUsed(float memoryUsed) {
		this.memoryUsed = memoryUsed;
	}
	
	public float getCpuUsageMhz() {
		return cpuUsageMhz;
	}
	
	public void setCpuUsageMhz(float cpuUsageMhz) {
		this.cpuUsageMhz = cpuUsageMhz;
	}
	
	public Timestamp getTs() {
		return new Timestamp(this.epoch);
	}


}
