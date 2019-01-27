# Deloitte Events: Activity Scheduling App

This is a standalone Java Application built using Spring boot 2.1.2, Java 8 , Maven(3.5.x) and Eclipse IDE(4.7.2).

## Features
- Application is packaged to a jar by default.
- User can pass Input file(.txt) of Activities through command line
- If no arguments are passed, to demonstrate App, will read a sample activities
   file from classPath and run the Program
- Output is logged to console with Team Name and Schedule of Activities displayed

## How to run

   You should have Java 8, Maven to run this application.*activityListTextPath* is a commandLine arg 
   which carries your Input File Path.
    
### For Opening project in Eclipse
   
   -Open eclipse.  
   -Click File > Import.  
   -Type Maven in the search box under Select an import source:  
   -Select Existing Maven Projects.  
   -Click Next.  
   -Click Browse and select the [folder](deloitte-activity-scheduler) that is the root of the Maven project .
   -Click Next .  
   -Click Finish
   
   This is the [Root Springboot Application](deloitte-activity-scheduler/src/main/java/com/deloitte/events/deloitteactivityscheduler/DeloitteActivitySchedulerApplication.java)  

### If running as jar (with Input file)
    java -jar deloitte-activity-scheduler-0.0.1-SNAPSHOT.jar --activityListTextPath=<Your Local Input File Path>     
    activityListTextPath is a command line argument expected by the program.
  Jar will be provided as part of this Git hub Project [here](runnable_jar)  
   Jar can be also genrated by running below command
    
   *mvn clean install* on project root folder [deloitte-activity-scheduler](deloitte-activity-scheduler) and jar will be created in /target
   
   Then run below command in deloitte-activity-scheduler/target
   *java -jar deloitte-activity-scheduler-0.0.1-SNAPSHOT.jar --activityListTextPath=/Users/admin/Desktop/activities.txt*
    
### If running as jar (without any input file)
   *java -jar deloitte-activity-scheduler-0.0.1-SNAPSHOT.jar*
    
### Running as Spring Boot Application with Input File
    mvn spring-boot:run -Dspring-boot.run.arguments=--activityListTextPath=<Your Local Input File Path> . 
    
  cd [deloitte-activity-scheduler](deloitte-activity-scheduler)   
 Eg  *mvn spring-boot:run -Dspring-boot.run.arguments=--activityListTextPath=/Users/admin/Desktop/activities.txt*
    
### Running as Spring Boot Application without Input File
   cd [deloitte-activity-scheduler](deloitte-activity-scheduler) .  
 Eg    *mvn spring-boot:run* 
    
    
## Implementation Logic
    When we run the Program by passing input text file as Command Line Argument, it will read the file 
    and parse Activity Name and Duration and create Activity Object out of it and push to a Stack. 
    All the Activities will be   stored in Stack<Activity>. 
    It will do some validations like File exists or not and arg is passed or not etc. 
    For demonstration purpose I have put a sample file in classpath and it will read from that if no args are passed. 
    It will skip the lines which are not in proper format like duration missing , 
    name misisng etc and process remaining lines which are valid.
    
    Program will iterate through the stack of Activities and pop one by one, 
    check for the mandatory breaks like lunch, Staff Presentation etc and create schedule for 
    each activity without any gap in between.As we dont have 
    no of teams and employees as Input, Teams will be dynamically created based on no of activities and  
    their duration.Also will schedule the lunch and Staff Presentation accordingly when time is up for those.
    
    Each schedule is stored inside a HashMap with Team Name as key and List<Activity> as value.
    And finally it will iterate through this map and print the Schedule in the pattern.If activities are less in the 
    input file, Teams will be less.As Presentation is a single event for all Teams logic will make sure all 
    are attending it at the same time.Also we have logic for early start time of Presentation as 4 and late 
    start time as 5.
   
   Main business logic for scheduling is written in
   
   [ActivityScheduleServiceImpl.java](deloittecs/deloitte-activity-scheduler/src/main/java/com/deloitte/events/deloitteactivityscheduler/service/ActivityScheduleServiceImpl.java)
    
  ### Unit Test Cases
    This Project has some unit test cases written using Junit covering test cases like
    -No Input File passed
    -Proper Input file passed
    -End Time of Each Schedule
    -Is Lunch break available for a schedule etc
    
      
  

    
    
    
    

    
