package com.deloitte.events.deloitteactivityscheduler.constants;

import java.time.LocalTime;

public class Constants {
	public static final LocalTime START_TIME = LocalTime.of(9, 0);
	public static final LocalTime END_MAX_TIME = LocalTime.of(17, 0);
	public static final LocalTime LUNCH_BREAK_TIME = LocalTime.NOON;
	public static final LocalTime EARLY_PRESENTATION_START_TIME = LocalTime.of(16, 0);
	public static final String STAFF_PRESENTATION ="Staff Motivation Presentation";
	public static final long STAFF_PRESENTATION_DURATION = 60;
	public static final long LUNCH_BREAK_DURATION = 60;
	public static final String LUNCH_BREAK = "Lunch Break";
	
	public static final String MINUTES = "min";
	public static final String SPRINT = "sprint";
	
}
