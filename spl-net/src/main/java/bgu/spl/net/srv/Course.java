package bgu.spl.net.srv;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private int courseNum;
    private String courseName;
    private Map<Integer, Boolean> kdamCourses;
    private int numOfMaxStudents;
    private int numOfRegisteredStudents;


    public Course(int courseNum, String courseName, int numOfMaxStudents) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.kdamCourses = new HashMap<>();
        this.numOfMaxStudents = numOfMaxStudents;
        this.numOfRegisteredStudents = 0;
    }


    public void addKdamCourse(int num){
        kdamCourses.put(num, true);
    }

    /**
     *  This function is called when we want to register a student.
     *  <p>
     *  !!!Note: the function assumes the student has all kdam courses!!!
     * @returns true if successfully registered, otherwise false.
     */
    public synchronized boolean registerStudent(){
        if (numOfRegisteredStudents < numOfMaxStudents){
            numOfRegisteredStudents++;
            return true;
        }
        return false;
    }

    public int getCourseNum(){
        return courseNum;
    }
    public int getNumOfMaxStudents(){
        return numOfMaxStudents;
    }
    public int getNumOfRegisteredStudents(){
        return numOfRegisteredStudents;
    }
    public void registereStudent(){
        numOfRegisteredStudents++;
    }


}
