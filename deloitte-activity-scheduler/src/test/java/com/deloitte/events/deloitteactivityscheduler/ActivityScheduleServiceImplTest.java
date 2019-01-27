package com.deloitte.events.deloitteactivityscheduler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.deloitte.events.deloitteactivityscheduler.model.Activity;
import com.deloitte.events.deloitteactivityscheduler.service.ActivityScheduleServiceImpl;
import com.deloitte.events.deloitteactivityscheduler.util.CommonUtil;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
public class ActivityScheduleServiceImplTest {

	String properInputFilePath;
	String invalidFilePath;
	Stack<Activity> sampleActivityStack;

	@InjectMocks
	private ActivityScheduleServiceImpl activityScheduleService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
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
		try
		{
			Map<String, List<Activity>> scheduleMap=activityScheduleService.scheduleActivities(properInputFilePath);
			assertTrue(scheduleMap.size()>0);
			assertTrue(scheduleMap.get("Team 1").size()>0);
		}
		catch(Exception e)
		{
			assertFalse(true);
		}
	}

}
