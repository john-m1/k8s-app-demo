package com.metro.atwater.data.luns;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Luns {
	private static final Logger logger = LoggerFactory.getLogger(Luns.class);

	private String name;
	private String date;
	private float freespace;
	private float capacity;
	Long epoch;
	
	public String getName() {
		return name;
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

	public void setName(String name) {
		this.name = name;
	}

	public float getFreeSpace() {
		return freespace;
	}
	
	public void setFreeSpace(float freeSpace) {
		this.freespace = freeSpace;
	}
	
	public float getCapacity() {
		return capacity;
	}
	
	public void setCapacity(float capacity) {
		this.capacity = capacity;
	}
	
	public Timestamp getTs() {
		return new Timestamp(this.epoch);
	}


}
