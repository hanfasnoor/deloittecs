package com.deloitte.events.deloitteactivityscheduler.model;


import java.time.LocalTime;

public class Activity {
	
	@Override
	public String toString() {
		return "Activity [name=" + name + ", duration=" + duration + ", startTime=" + startTime + "]";
	}

	private String name;
	
	private long duration;
	
	private LocalTime startTime;
	
	public Activity(String name, long duration, LocalTime startTime) {
		super();
		this.name = name;
		this.duration = duration;
		this.startTime = startTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

}
