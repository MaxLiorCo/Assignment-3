package bgu.spl.net.srv;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String user;
    private String password;
    private static int[] courseArray;
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
            courseArray = new int[listCourse.size()];
            int currIndex = 0;
            for (Integer course : listCourse){
                courseArray[currIndex] = course;
                currIndex++;
            }
        }
    catch (FileNotFoundException ex){}
    catch (IOException ex) { }
    }

    //register students to course
    public void registerToCourse(Course course) throws Error{
        synchronized(course) {
            boolean registered = false;
            if (course.getNumOfRegisteredStudents() == course.getNumOfMaxStudents())
                throw new Error("course is full");

            int courseNum = course.getCourseNum();
            for (int indexOfCourse = 0; indexOfCourse < courseArray.length && !registered; indexOfCourse++) {
                if (courseArray[indexOfCourse] == courseNum) {
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
            if (registered)
                course.registerStudent(this);
            else
                throw new Error("no such course");
        }
    }

    public Integer[] getNumCourseArray(){
        return (Integer[])courseIndex.toArray();
    }

    public boolean isAdmin(){
        return isAdmin;
    }

    public List<Integer> getListOfCourseIndex(){
        return courseIndex;
    }

}
