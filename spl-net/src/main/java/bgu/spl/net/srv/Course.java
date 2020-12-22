package bgu.spl.net.srv;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private int courseNum;
    private String courseName;
    Map<Integer, Boolean> kdamCourses;
    int numOfMaxStudents;
    int numOfRegisteredStudents;


    public Course(int courseNum, String courseName, int numOfMaxStudents) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.kdamCourses = new HashMap<>();
        this.numOfMaxStudents = numOfMaxStudents;
        this.numOfRegisteredStudents = 0;
    }

    /**
     *  This function is called when we want to register a student.
     *  <p>
     *  !!!Note:
      * @returns true if successfully registered, otherwise false.
     */
    public synchronized boolean register(){
        if (numOfMaxStudents < numOfMaxStudents){
            numOfRegisteredStudents++;
            return true;
        }
        return false;
    }


}
