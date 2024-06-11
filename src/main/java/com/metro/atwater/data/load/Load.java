package com.metro.atwater.data.load;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Load {
	private static final Logger logger = LoggerFactory.getLogger(Load.class);

	private String server;
	private String date;
	private Float one;
	private Float five;
	private Float fifteen;
	
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
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	
	public Timestamp getTs() {
		return new Timestamp(this.epoch);
	}

	public Float getOne() {
		return one;
	}

	public void setOne(Float one) {
		this.one = one;
	}

	public Float getFive() {
		return five;
	}

	public void setFive(Float five) {
		this.five = five;
	}

	public Float getFifteen() {
		return fifteen;
	}

	public void setFifteen(Float fifteen) {
		this.fifteen = fifteen;
	}
	
}
