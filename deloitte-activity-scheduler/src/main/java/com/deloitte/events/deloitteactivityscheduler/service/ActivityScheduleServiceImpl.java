package com.deloitte.events.deloitteactivityscheduler.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.deloitte.events.deloitteactivityscheduler.DeloitteActivitySchedulerApplication;
import com.deloitte.events.deloitteactivityscheduler.constants.Constants;
import com.deloitte.events.deloitteactivityscheduler.model.Activity;
import com.deloitte.events.deloitteactivityscheduler.util.CommonUtil;

@Component
public class ActivityScheduleServiceImpl implements ActivityScheduleService {

	Logger logger = LoggerFactory.getLogger(ActivityScheduleServiceImpl.class);

	/**
	 * Main public method for scheduling activities
	 */
	@Override
	public Map<String, List<Activity>> scheduleActivities(String inputActivitiesTextPath) {
		Stack<Activity> activityStack = CommonUtil.processInputActivitiesFile(inputActivitiesTextPath);
		logger.info("No of activities " + (activityStack != null ? activityStack.size() : 0));
		return scheduleActivitiesToTeams(activityStack);

	}

	/**
	 * This method will run through the stack of Activities and dynamically creates
	 * Teams and assign activity List
	 * 
	 * @param activityStack
	 * @return Map of Teams vs ActivityList
	 */
	private Map<String, List<Activity>> scheduleActivitiesToTeams(Stack<Activity> activityStack) {

		if (null == activityStack || activityStack.isEmpty()) {
			logger.info("No Activities to schedule");
			return null;
		}

		Map<String, List<Activity>> teamActivityMap;
		LocalTime presentationStartTime = null;

		teamActivityMap = new LinkedHashMap<String, List<Activity>>();
		int teamCounter = 1;
		while (!activityStack.isEmpty()) {

			boolean maxEndTimeReached = false;
			LocalTime starTime = Constants.START_TIME;
			List<Activity> activitiesForTeam = new ArrayList<Activity>();
			boolean isLunchOver = false;

			String teamName = "Team " + teamCounter;
			while (!maxEndTimeReached && !activityStack.isEmpty()) { // This while loop exit when max end time (5:00 PM)
																		// is reached or activities are over.It will
																		// create a map of Team Vs ActivitySchedule
																		// before exiting.
				Activity activityItem = activityStack.pop();
				long durationOfCurrentActivity = activityItem.getDuration();

				LocalTime endTimeOfCurrentActivity = starTime.plusMinutes(durationOfCurrentActivity);
				if (endTimeOfCurrentActivity.compareTo(Constants.END_MAX_TIME) <= 0) {

					// This is to schedule Lunch break, it will happen only once per Team using
					// boolean isLunchOver
					if (endTimeOfCurrentActivity.compareTo(Constants.LUNCH_BREAK_TIME) >= 0 && !isLunchOver) {
						activityStack.push(activityItem);
						isLunchOver = true;
						durationOfCurrentActivity = Constants.LUNCH_BREAK_DURATION;
						activitiesForTeam.add(new Activity(Constants.LUNCH_BREAK, durationOfCurrentActivity, starTime));
						endTimeOfCurrentActivity = starTime.plusMinutes(durationOfCurrentActivity);
						starTime = endTimeOfCurrentActivity;

					} else {
						activitiesForTeam
								.add(new Activity(activityItem.getName(), durationOfCurrentActivity, starTime));
						starTime = endTimeOfCurrentActivity;
					}

					// Activities can be finished before evening 5:00 PM, this is to handle that
					// case
					if (activityStack.isEmpty()) {
						activitiesForTeam
								.add(new Activity(Constants.STAFF_PRESENTATION, Constants.STAFF_PRESENTATION_DURATION,
										(Constants.END_MAX_TIME.equals(presentationStartTime) ? Constants.END_MAX_TIME
												: (starTime.isAfter(Constants.EARLY_PRESENTATION_START_TIME) ? starTime
														: Constants.EARLY_PRESENTATION_START_TIME))));
						teamActivityMap.put(teamName, activitiesForTeam);
						break;
					}

				} else { // Once max end time 5:00 PM is reached exiting the loop and schedule Staff
							// Presentation and putting all activities to map for a Team
					maxEndTimeReached = true;
					presentationStartTime = Constants.END_MAX_TIME;
					activitiesForTeam.add(new Activity(Constants.STAFF_PRESENTATION,
							Constants.STAFF_PRESENTATION_DURATION, Constants.END_MAX_TIME));
					teamActivityMap.put(teamName, activitiesForTeam);
					activityStack.push(activityItem);
					teamCounter++;
					break;
				}
			}
			logger.info("Team size " + teamActivityMap.size());
		}

		printScheduleToConsole(teamActivityMap);

		return teamActivityMap;

	}

	/**
	 * Helper method to iterate over final map and print schedule in pattern
	 * 
	 * @param teamActivityMap
	 */
	private void printScheduleToConsole(Map<String, List<Activity>> teamActivityMap) {
		for (Entry<String, List<Activity>> schedule : teamActivityMap.entrySet()) {
			logger.info(schedule.getKey());
			logger.info("-------------------------");
			for (Activity activity : schedule.getValue()) {
				logger.info(activity.getStartTime() + " : " + activity.getName() + " " + activity.getDuration()
						+ Constants.MINUTES);
			}
			System.out.println();
		}
	}

}
