package com.deloitte.events.deloitteactivityscheduler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deloitte.events.deloitteactivityscheduler.constants.Constants;
import com.deloitte.events.deloitteactivityscheduler.model.Activity;
import com.deloitte.events.deloitteactivityscheduler.service.ActivityScheduleServiceImpl;
import com.deloitte.events.deloitteactivityscheduler.util.CommonUtil;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
public class ActivityScheduleServiceImplTest {

	String properInputFilePath;
	String invalidFilePath;
	String lessActivitiesFilePath;
	Stack<Activity> sampleActivityStack;

	@InjectMocks
	private ActivityScheduleServiceImpl activityScheduleService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		lessActivitiesFilePath = loadTestFile("activities_smallno.txt");
		properInputFilePath = loadTestFile("activities.txt");
		invalidFilePath = loadTestFile("activitiesInvalid.txt");
		sampleActivityStack = loadTestActivities();
	}

	private Stack<Activity> loadTestActivities() {
		// TODO Auto-generated method stub
		return CommonUtil.processInputActivitiesFile(properInputFilePath);
	}

	private String loadTestFile(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		return file.getAbsolutePath();

	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testProcessInputFileWithProperInputActivities() throws Exception {
		Stack<Activity> activityStack = CommonUtil.processInputActivitiesFile(properInputFilePath);
		assertNotNull(activityStack);
		Assert.assertTrue(activityStack.size() > 0);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testProcessInputFileWithInvalidInputActivities() throws Exception {
		Stack<Activity> activityStack = CommonUtil.processInputActivitiesFile(invalidFilePath);
		Assert.assertTrue(activityStack.size() == 0);

	}

	@Test
	public void testProcessInputFileWithNoInput() throws Exception {
		Stack<Activity> activityStack = CommonUtil.processInputActivitiesFile("");
		assertNull(activityStack);

	}

	@Test
	public void testScheduleActivitiesWithProperInput() throws Exception {
		try {
			Map<String, List<Activity>> scheduleMap = activityScheduleService.scheduleActivities(properInputFilePath);
			assertTrue(scheduleMap.size() > 0);
			assertTrue(scheduleMap.get("Team 1").size() > 0);
		} catch (Exception e) {
			assertFalse(true);
		}
	}

	@Test
	public void testLastEventIsPresentationForMultipleTeamSchedule() throws Exception {
		try {
			Map<String, List<Activity>> scheduleMap = activityScheduleService.scheduleActivities(properInputFilePath);
			assertTrue(scheduleMap.size() > 0);
			assertTrue(scheduleMap.get("Team 1").size() > 0);
			for (Entry<String, List<Activity>> schedule : scheduleMap.entrySet()) {
				List<Activity> activityList = schedule.getValue();
				Activity activity = activityList.stream().max(Comparator.comparing(Activity::getStartTime)).get();
				assertTrue(activity.getStartTime().equals(Constants.END_MAX_TIME));
				assertTrue(activity.getName().equals(Constants.STAFF_PRESENTATION));
			}
		} catch (Exception e) {
			assertFalse(true);
		}
	}

	@Test
	public void testLucnhBreakInSchedule() throws Exception {
		try {
			Map<String, List<Activity>> scheduleMap = activityScheduleService.scheduleActivities(properInputFilePath);
			assertTrue(scheduleMap.size() > 0);
			assertTrue(scheduleMap.get("Team 1").size() > 0);
			for (Entry<String, List<Activity>> schedule : scheduleMap.entrySet()) {
				List<Activity> activityList = schedule.getValue();
				List<Activity> activities = activityList.stream()
						.filter(act -> act.getName().equals(Constants.LUNCH_BREAK)).collect(Collectors.toList());
				assertTrue(activities != null && activities.size() == 1
						&& activities.get(0).getName().equals(Constants.LUNCH_BREAK)
						&& activities.get(0).getStartTime().isBefore(Constants.LUNCH_BREAK_TIME));
			}
		} catch (Exception e) {
			assertFalse(true);
		}
	}
	
	@Test
	public void testForSmallNoOfActivitiesInInput() throws Exception {
		try {
			Map<String, List<Activity>> scheduleMap = activityScheduleService.scheduleActivities(lessActivitiesFilePath);
			assertTrue(scheduleMap.size() > 0);
			assertTrue(scheduleMap.get("Team 1").size() > 0);
			for (Entry<String, List<Activity>> schedule : scheduleMap.entrySet()) {
				List<Activity> activityList = schedule.getValue();
				Activity activity = activityList.stream().max(Comparator.comparing(Activity::getStartTime)).get();
				assertTrue(activity.getStartTime().equals(Constants.EARLY_PRESENTATION_START_TIME));
				assertTrue(activity.getName().equals(Constants.STAFF_PRESENTATION));
			}
		} catch (Exception e) {
			assertFalse(true);
		}
	}

}
