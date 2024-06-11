package com.metro.atwater.data.process;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Process {
	private static final Logger logger = LoggerFactory.getLogger(Process.class);

	private String server;
	private String process;
	private String date;
	private Float cpu;
	private Integer thread;
	private Float vsz;
	private Float rss;
	
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
	
	public Timestamp getTs() {
		return new Timestamp(this.epoch);
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

	public Float getCpu() {
		return cpu;
	}

	public void setCpu(Float cpu) {
		this.cpu = cpu;
	}

	public Integer getThread() {
		return thread;
	}

	public void setThread(Integer thread) {
		this.thread = thread;
	}

	public Float getVsz() {
		return vsz;
	}

	public void setVsz(Float vsz) {
		this.vsz = vsz;
	}

	public Float getRss() {
		return rss;
	}

	public void setRss(Float rss) {
		this.rss = rss;
	}
}
