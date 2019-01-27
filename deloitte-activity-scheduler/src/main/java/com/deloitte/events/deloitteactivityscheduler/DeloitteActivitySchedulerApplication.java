package com.deloitte.events.deloitteactivityscheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.deloitte.events.deloitteactivityscheduler.service.ActivityScheduleService;
import com.deloitte.events.deloitteactivityscheduler.util.CommonUtil;

@SpringBootApplication
@EnableAutoConfiguration
public class DeloitteActivitySchedulerApplication implements ApplicationRunner {

	Logger logger = LoggerFactory.getLogger(DeloitteActivitySchedulerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DeloitteActivitySchedulerApplication.class, args);
	}

	@Autowired
	private ActivityScheduleService activityScheduleServiceImpl;

	@Value("activityListTextPath")
	private String activityListTextPath;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		String inputActivitiesFilePath = null;
		
		/*Expecting a commandLine argument with name as activityListTextPath*/
		if (args != null && args.getOptionNames().contains("activityListTextPath")) {
			List<String> inpuTextPath = args.getOptionValues("activityListTextPath");
			inputActivitiesFilePath = inpuTextPath.get(0);
			activityScheduleServiceImpl.scheduleActivities(inputActivitiesFilePath);
			
		/* If No Input file is passed in arguments just taking a sample file from classpath and running the Program*/
		} else {
			logger.info("No Input file passed,loading activities from a default file");
			String fileName = CommonUtil.getClassPathInputFile("inputData/activities.txt");
			activityScheduleServiceImpl.scheduleActivities(fileName);

		}

	}

}
