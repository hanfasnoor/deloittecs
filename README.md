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
   Jar will be provided as part of this repo [here](runnable_jar)    
    Run below command after downloading . 
    
    java -jar deloitte-activity-scheduler-0.0.1-SNAPSHOT.jar --activityListTextPath= <Your Local Input File Path>     
    
   Please note **activityListTextPath** is a command line argument expected by the program.
   
  Jar can be also genrated by running command   
  
  *mvn clean install* on project root folder [deloitte-activity-scheduler](deloitte-activity-scheduler) and jar will be created in /target 
   
     Then run below command in deloitte-activity-scheduler/target 
     java -jar deloitte-activity-scheduler-0.0.1-SNAPSHOT.jar --activityListTextPath=<Your Local Input File Path> 
    
### If running as jar (without any input file)
   *java -jar deloitte-activity-scheduler-0.0.1-SNAPSHOT.jar*
    
### Running as Spring Boot Application with Input File
    mvn spring-boot:run -Dspring-boot.run.arguments=--activityListTextPath=<Your Local Input File Path> 
    
  For Eg: cd [deloitte-activity-scheduler](deloitte-activity-scheduler)   
   *mvn spring-boot:run -Dspring-boot.run.arguments=--activityListTextPath=/Users/admin/Desktop/activities.txt*
    
### Running as Spring Boot Application without Input File
     mvn spring-boot:run
   
 For Eg:  cd [deloitte-activity-scheduler](deloitte-activity-scheduler) .  
     *mvn spring-boot:run* 
    
    
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
   
   [ActivityScheduleServiceImpl.java](deloitte-activity-scheduler/src/main/java/com/deloitte/events/deloitteactivityscheduler/service/)
    
  ### Unit Test Cases
    This Project has some unit test cases written using Junit covering test cases like
    -No Input File passed
    -Proper Input file passed
    -End Time of Each Schedule
    -Is Lunch break available for a schedule etc

### Some Test Evidences
 ###### Input 1
      Duck Herding 60min
      Archery 45min
      Learning Magic Tricks 40min
      Laser Clay Shooting 60min
      Human Table Football 30min
      Buggy Driving 30min
      Salsa & Pickles sprint
      2-wheeled Segways 45min
      Viking Axe Throwing 60min
      Giant Puzzle Dinosaurs 30min
      Giant Digital Graffiti 60min
      Cricket 2020 60min
      Wine Tasting sprint
      Arduino Bonanza 30min
      Digital Tresure Hunt 60min
      Enigma Challenge 45min
      Monti Carlo or Bust 60min
      New Zealand Haka 30min
      Time Tracker sprint
      Indiano Drizzle 45min
      
      mvn spring-boot:run -Dspring-boot.run.arguments=--activityListTextPath=/Users/admin/Desktop/activities.txt
      
 ###### Output 1
 
       Team 1
      -------------------------
      09:00 : Indiano Drizzle 45min
      09:45 : Time Tracker 15min
      10:00 : New Zealand Haka 30min
      10:30 : Monti Carlo or Bust 60min
      11:30 : Lunch Break 60min
      12:30 : Enigma Challenge 45min
      13:15 : Digital Tresure Hunt 60min
      14:15 : Arduino Bonanza 30min
      14:45 : Wine Tasting 15min
      15:00 : Cricket 2020 60min
      16:00 : Giant Digital Graffiti 60min
      17:00 : Staff Motivation Presentation 60min

      Team 2
      -------------------------
      09:00 : Giant Puzzle Dinosaurs 30min
      09:30 : Viking Axe Throwing 60min
      10:30 : 2-wheeled Segways 45min
      11:15 : Salsa & Pickles 15min
      11:30 : Lunch Break 60min
      12:30 : Buggy Driving 30min
      13:00 : Human Table Football 30min
      13:30 : Laser Clay Shooting 60min
      14:30 : Learning Magic Tricks 40min
      15:10 : Archery 45min
      15:55 : Duck Herding 60min
      17:00 : Staff Motivation Presentation 60min
 
###### Input 2
      Duck Herding 60min
      Archery 45min
      Learning Magic Tricks 40min
      Laser Clay Shooting 60min
      Human Table Football 30min
      Buggy Driving 30min
      Salsa & Pickles sprint
      2-wheeled Segways 45min
      Viking Axe Throwing 60min
      
      mvn spring-boot:run -Dspring-boot.run.arguments=--activityListTextPath=/Users/admin/Desktop/activities2.txt
      
###### Output 2
      Team 1
      -------------------------
      09:00 : Viking Axe Throwing 60min
      10:00 : 2-wheeled Segways 45min
      10:45 : Salsa & Pickles 15min
      11:00 : Buggy Driving 30min
      11:30 : Lunch Break 60min
      12:30 : Human Table Football 30min
      13:00 : Laser Clay Shooting 60min
      14:00 : Learning Magic Tricks 40min
      14:40 : Archery 45min
      15:25 : Duck Herding 60min
      16:25 : Staff Motivation Presentation 60min
  

    
    
    
    

    
