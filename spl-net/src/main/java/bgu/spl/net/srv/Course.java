package bgu.spl.net.srv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course {
    private int courseNum;
    private String courseName;
    //private Map<Integer, Boolean> kdamCourses;
    private int[] kdamCourses;
    private int numOfMaxStudents;
    private int numOfRegisteredStudents;


    public Course(int courseNum, String courseName, List<Integer> kdam, int numOfMaxStudents) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        this.kdamCourses = new int[kdam.size()];
        int index = 0;
        for (Integer id : kdam) {
            kdamCourses[index] = id;
            index++;
        }

        //this.kdamCourses = new HashMap<>();
        this.numOfMaxStudents = numOfMaxStudents;
        this.numOfRegisteredStudents = 0;
    }


    /**
     *  This function is called when we want to register a student.
     *  <p>
     *  !!!Note: the function assumes the student has all kdam courses!!!
     * @returns true if successfully registered, otherwise false.
     */
    public synchronized boolean registerStudent(User student){
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


}
