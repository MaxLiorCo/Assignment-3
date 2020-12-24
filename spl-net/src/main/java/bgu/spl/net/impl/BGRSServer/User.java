package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.BGRSServer.Course;

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

    public User(String _user, String _password, boolean _isAdmin){
        user = _user;
        password =_password;
        isAdmin = _isAdmin;
        courseIndex = new ArrayList<>();
    }

    // courses ordered by Course.txt
    public static void initializeCourseArray(String coursesFilePath){
        File file = new File(coursesFilePath);
        List<Integer> listCourse = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String courseLine = "";
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
    public void registerToCourse(Course course) throws Error{
        synchronized(course) {
            boolean registered = false;
            if(course == null)
                throw new Error("no such course");

            //should take care of "course is full" & "no relevant kdam courses"
            course.registerStudent(this);

            int courseNum = course.getCourseNum();
            for (int indexOfCourse = 0; indexOfCourse < allCourses.length && !registered; indexOfCourse++) {
                if (allCourses[indexOfCourse] == courseNum) {
                    //insert into list by index size
                    int insertIndex = 0;
                    Iterator<Integer> it = courseIndex.iterator();
                    if (!it.hasNext()) {
                        courseIndex.add(insertIndex, indexOfCourse);
                        registered = true;
                    } else {
                        int listIndexPrevValue = -1;
                        while (it.hasNext() & !registered) {
                            int listIndexValue = it.next();
                            if (indexOfCourse == listIndexValue)
                                throw new Error("already registered");
                            else if (listIndexPrevValue < indexOfCourse && indexOfCourse < listIndexValue) {
                                courseIndex.add(insertIndex, indexOfCourse);
                                registered = true;
                            }
                            listIndexPrevValue = listIndexValue;
                            insertIndex++;
                        }
                    }
                }
            }
        }
    }

    public Integer[] getRegisteredCoursesArray(){
        Integer[] array = new Integer[courseIndex.size()];
        int i = 0;
        for(int indexInAllCourses : courseIndex){
            array[i] = allCourses[indexInAllCourses];
            i++;
        }
        return array;
    }

    public boolean isAdmin(){
        return isAdmin;
    }

/*    public List<Integer> getListOfCourseIndexInAllCoursesArray(){
        return courseIndex;
    }*/

}
