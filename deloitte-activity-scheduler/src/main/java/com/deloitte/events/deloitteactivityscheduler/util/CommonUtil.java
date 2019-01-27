package com.deloitte.events.deloitteactivityscheduler.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Stack;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import com.deloitte.events.deloitteactivityscheduler.constants.Constants;
import com.deloitte.events.deloitteactivityscheduler.model.Activity;

public class CommonUtil {

	static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * This method is called from Service class to process InputFile passed through command line argument
	 * and populate all activities to stack
	 * @param inputActivitiesTextPath
	 * @return
	 */
	public static Stack<Activity> processInputActivitiesFile(String inputActivitiesTextPath) {
		File file = new File(inputActivitiesTextPath);
		if (null == file || !file.exists()) {
			logger.info("No such file exists " + inputActivitiesTextPath);
			return null;
		}

		Stack<Activity> activityStack = new Stack<Activity>();

		try {
			List<String> contents = FileUtils.readLines(file);
			// Iterate the result to print each line of the file.
			for (String line : contents) {
				logger.debug(line);
				if (null != line && !line.isEmpty()) {
					Activity activity = retrieveActivityFromLine(line);
					if (!StringUtils.isEmpty(activity.getName()) && activity.getDuration() > 0) {
						activityStack.push(activity);
					} else {
						// Skipping activity with Duration 0 or not given in text file
						logger.info(line + " is in wrong format or missing some data, hence skipping");
						continue;
					}

				}

			}
		} catch (NumberFormatException ne) {
			ne.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return activityStack;
	}

	/**
	 * Passing each line read from input text file and creating Activity Object
	 * @param line
	 * @return
	 */
	private static Activity retrieveActivityFromLine(String line) {
		long duration = 0;
		String activityName = null;
		Activity activityItem = null;
		if (line.lastIndexOf(" ") > 0 && line.lastIndexOf(Constants.MINUTES) > 0) {
			String durationString = line.substring(line.lastIndexOf(" "), line.lastIndexOf(Constants.MINUTES)).trim();
			duration = Integer.parseInt(!"".equals(durationString) ? durationString : "0");
			activityName = line.substring(0, line.lastIndexOf(" ")).trim();
		} else if (line.lastIndexOf(" ") > 0 && line.lastIndexOf(Constants.SPRINT) > 0) {
			duration = 15;
			activityName = line.substring(0, line.lastIndexOf(" ")).trim();
		}
		logger.debug(activityName + " " + duration);
		activityItem = new Activity(activityName, duration, null);
		return activityItem;
	}

	/**
	 * 
	 * @param classPathFile
	 * @return
	 */
	public static String getClassPathInputFile(String classPathFile) {
		ClassPathResource classPathResource = new ClassPathResource(classPathFile);
		InputStream inputStream = null;
		String classPathFileAbsolutePath = null;

		try {
			inputStream = classPathResource.getInputStream();
			File inputFile = File.createTempFile("activitiesTemp", ".txt");
			FileUtils.copyInputStreamToFile(inputStream, inputFile);
			classPathFileAbsolutePath = inputFile.getAbsolutePath();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}

		return classPathFileAbsolutePath;
	}

}
