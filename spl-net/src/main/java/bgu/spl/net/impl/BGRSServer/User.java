package bgu.spl.net.impl.BGRSServer;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String user;
    private String password;
    private static int[] allCourses;
    private List<Integer> courseIndex;
    private boolean isAdmin;
    private boolean loggedIn;



    public User(String _user, String _password, boolean _isAdmin){
        user = _user;
        password =_password;
        isAdmin = _isAdmin;
        courseIndex = new ArrayList<>(); //stores the indexes of courses in allCourses array
        loggedIn = false;
    }

    // courses ordered by "Course.txt". called from database
    public static void initializeCourseArray(String coursesFilePath){
        File file = new File(coursesFilePath);
        List<Integer> listCourse = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String courseLine;
            while ((courseLine = br.readLine()) != null) {
                int currIndex = 0;
                while (!(courseLine.charAt(currIndex) == '|')){
                    currIndex++;
                }
                int courseNum = Integer.decode(courseLine.substring(0,currIndex));
                listCourse.add(courseNum);
            }
            allCourses = new int[listCourse.size()];
            int currIndex = 0;
            for (Integer course : listCourse){
                allCourses[currIndex] = course;
                currIndex++;
            }
        }
    catch (FileNotFoundException ex){ ex.printStackTrace(); }
    catch (IOException ex) { ex.printStackTrace(); }
    }

    //register course to student
    public void registerToCourse(Course course) throws Error {
        boolean registered = false;
        if (course == null)
            throw new Error("no such course");

        course.registerStudent(this);

        int courseNum = course.getCourseNum();
        for (int indexOfCourse = 0; indexOfCourse < allCourses.length && !registered; indexOfCourse++) {
            if (allCourses[indexOfCourse] == courseNum) {
                //insert into list by index Of Course in allCourses array

                int insertIndex = 0;
                Iterator<Integer> it = courseIndex.iterator();
                if (!it.hasNext()) {
                    courseIndex.add(insertIndex, indexOfCourse);
                    registered = true;
                } else {
                    //we need to find a place for this index amongst indexes of already registered courses
                    while (it.hasNext() & !registered) {
                        int listIndexValue = it.next();
                        if (indexOfCourse == listIndexValue)
                            throw new Error("already registered");
                        if (indexOfCourse < listIndexValue) {
                            courseIndex.add(insertIndex, indexOfCourse);
                            registered = true;
                        }
                        insertIndex++;
                    }
                    //true when indexOfCourse is larger than that of any existing courses
                    if (!registered)
                    {
                        courseIndex.add(insertIndex, indexOfCourse);
                        registered = true;
                    }
                }
            }
        }
    }
    //an array of courses the user is registered to
    public Integer[] getRegisteredCoursesArray(){
        Integer[] array = new Integer[courseIndex.size()];
        int i = 0;
        for(int indexInAllCourses : courseIndex){
            array[i] = allCourses[indexInAllCourses];
            i++;
        }
        return array;
    }

    //try to remove user from course & course from user, both must happen
    public boolean removeCourse(Course courseToRemove) {
        int courseNum = courseToRemove.getCourseNum();
        int indexOfCourse = 0;
        while (indexOfCourse < allCourses.length) {
            if (allCourses[indexOfCourse] == courseNum)
                break;
            indexOfCourse++;
        }
        return courseToRemove.removeStudent(this.user) && courseIndex.remove((Integer)indexOfCourse);
    }
    public boolean isAdmin(){
        return isAdmin;
    }
    public String getUserName(){
        return this.user;
    }
    public String getPassword(){
        return password;
    }
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}
