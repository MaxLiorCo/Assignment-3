package bgu.spl.net.impl.BGRSServer;

import java.util.*;

public class Course {
    private int courseNum;
    private String courseName;
    private int[] kdamCourses;
    private int numOfMaxStudents;
    private int numOfRegisteredStudents;
    private List<User> registeredStudentList;


    public Course(int courseNum, String courseName, List<Integer> kdam, int numOfMaxStudents) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        kdamCourses = new int[kdam.size()];
        int index = 0;
        for (Integer id : kdam) {
            kdamCourses[index] = id;
            index++;
        }
        this.numOfMaxStudents = numOfMaxStudents;
        numOfRegisteredStudents = 0;
        registeredStudentList = new LinkedList<>();
    }


    /**
     *  This function is called when we want to register a student.
     * @throws Error exception upon failed registration attempt.
     */
    public synchronized boolean registerStudent(User student) throws Error { //TODO synchronized may be unnecessary
        if (numOfRegisteredStudents < numOfMaxStudents && assertKdam(student.getRegisteredCoursesArray())){
            registeredStudentList.add(student);
            numOfRegisteredStudents++;
            return true;
        }
        else
            throw new Error("No place left in this course");
    }

    private boolean assertKdam(Integer[] registeredCoursesArray) throws Error{
        if(!(Arrays.asList(registeredCoursesArray)).containsAll(Arrays.asList(kdamCourses)))
            throw new Error("Haven't done all kdam courses needed for this course");
        return true;
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
