package bgu.spl.net.impl.BGRSServer;

import java.util.*;

public class Course {
    private int courseNum;
    private String courseName;
    private Integer[] kdamCourses;
    private int numOfMaxStudents;
    private int numOfRegisteredStudents;
    private List<String> registeredStudentList;


    public Course(int courseNum, String courseName, List<Integer> kdam, int numOfMaxStudents) {
        this.courseNum = courseNum;
        this.courseName = courseName;
        kdamCourses = new Integer[kdam.size()];
        //Arrays.fill(kdamCourses, 0);
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
    public void registerStudent(User student) throws Error {
        if (!registeredStudentList.contains(student.getUserName()) && (kdamCourses.length == 0 || assertKdam(student.getRegisteredCoursesArray())))
            synchronized (this) { //synchronized is necessary in situations where 2 different students try to register to the same course
                if (numOfRegisteredStudents < numOfMaxStudents) {
                    registeredStudentList.add(student.getUserName());
                    numOfRegisteredStudents++;
                    return;
                }
            }
        throw new Error("No place left in this course");
    }

    private boolean assertKdam(Integer[] registeredCoursesArray) throws Error {
        List<Integer> coursesOfStudent = Arrays.asList(registeredCoursesArray);
        List<Integer> kdamCoursesList =Arrays.asList(kdamCourses);
        if(!(coursesOfStudent.containsAll(kdamCoursesList)))
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
    public Integer[] getKdamCourses() {
        return kdamCourses;
    }
    public String getCourseName() {
        return courseName;
    }
    public List<String> getRegisteredStudentList() {
        return registeredStudentList;
    }
    public boolean removeStudent(String username){
        numOfRegisteredStudents--;
        return registeredStudentList.remove(username);
    }
}
