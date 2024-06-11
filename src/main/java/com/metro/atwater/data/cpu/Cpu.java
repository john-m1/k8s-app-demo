package com.metro.atwater.data.cpu;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cpu {
	
	private static final Logger logger = LoggerFactory.getLogger(Cpu.class);

	private String server;
	private String date;
	private Float usr;
	private Float sys;
	private Float wio;
	private Float idle;
	
	Long epoch;
	
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
	
	public Float getUsr() {
		return usr;
	}
	public void setUsr(Float usr) {
		this.usr = usr;
	}
	public Float getSys() {
		return sys;
	}
	public void setSys(Float sys) {
		this.sys = sys;
	}
	public Float getWio() {
		return wio;
	}
	public void setWio(Float wio) {
		this.wio = wio;
	}
	public Float getIdle() {
		return idle;
	}
	public void setIdle(Float idle) {
		this.idle = idle;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	
	public Timestamp getTs() {
		return new Timestamp(this.epoch);
	}
	
	
}
