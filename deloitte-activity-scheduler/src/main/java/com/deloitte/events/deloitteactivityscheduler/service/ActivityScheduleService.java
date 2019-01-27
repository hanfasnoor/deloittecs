package com.deloitte.events.deloitteactivityscheduler.service;

import java.util.List;
import java.util.Map;

import com.deloitte.events.deloitteactivityscheduler.model.Activity;

public interface ActivityScheduleService {
	
	public Map<String,List<Activity>>  scheduleActivities(String inputActivitiesTextPath);

}
