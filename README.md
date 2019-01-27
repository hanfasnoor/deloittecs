# Deloitte Events: Activity Scheduling App

This is a standalone Java Application built using Spring boot 2.1.2, Java 8 , Maven(3.5.x) and Eclipse IDE(4.7.2).

## Features
- Application is packaged to a jar by default.
- User can pass Input file(.txt) of Activities through command line
- If no arguments are passed, to demonstrate App, will read a sample activities
   file from classPath and run the Program
- Output is logged to console with Team Name and Schedule of Activities displayed

## How to run
     
     You should have Java 8, Maven to run this application.
    

### If running as jar (with Input file)
    java -jar deloitte-activity-scheduler-0.0.1-SNAPSHOT.jar --activityListTextPath=<Your Local Input File Path>    activityListTextPath is a command line argument expected by the program.Jar will be  
    provided as part of this Git hub Project.
    
    Eg: java -jar deloitte-activity-scheduler-0.0.1-SNAPSHOT.jar --activityListTextPath=/Users/admin/Desktop/activities.txt
    
### If running as jar (without any input file)
    java -jar deloitte-activity-scheduler-0.0.1-SNAPSHOT.jar
    
### From Eclipse with Input Activities file
    mvn spring-boot:run -Dspring-boot.run.arguments=--activityListTextPath=<Your Local Input File Path> 
    
    Eg:mvn spring-boot:run -Dspring-boot.run.arguments=--activityListTextPath=/Users/admin/Desktop/activities.txt
    
### From Eclipse without Activities file
    mvn spring-boot:run  
    
    
## Implementation Logic
    When we run the Program by passing input text file as Command Line Argument, it will read the file 
    and parse Activity Name and Duration and create Activity Object out of it and push to a Stack. 
    All the Activities will be   stored as Stack<Activity>. It will do some validations like File exists or not and arg name is wrong or not. 
    For demonstration purpose I have put a sample file in classpath and it will read from that if no input file found. 
    It will skip the lines which are not in proper format like duration missing , name misisng etc and process remaining ones which are valid.
    
    Program will iterate through the stack of Activities and pop one by one, 
    check for the mandatory breaks like lunch, Staff Presentation etc and create schedule for 
    each activity without any gap in between.As we dont have 
    no of teams and employees as Input, Teams will be dynamically created based on no of activities and  
    their duration.Also will schedule the lunch and Staff Presentation accordingly when time is up for those.
    
    Each schedule is stored inside a HashMap with Team Name as key and List<Activity> as value.
    And finally will iterate through this map and print the Schedule in the pattern.
    
    

    
