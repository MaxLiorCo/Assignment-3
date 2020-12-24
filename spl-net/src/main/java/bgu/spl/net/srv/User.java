package bgu.spl.net.srv;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String user;
    private String password;
    static int[] courseArray;
    List<Integer> courseIndex;

    public User(String _user, String _password){
        user = _user;
        password =_password;
        courseIndex = new ArrayList<>();
    }

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

    //throws exception if no such course
    public void registerCourse(Course course) throws Error{
        boolean registered = false;
        if(course.getNumOfRegisteredStudents() == course.getNumOfMaxStudents())
            throw new Error ("course is full");

        int courseNum = course.getCourseNum();
        for(int indexOfCourse = 0; indexOfCourse<courseArray.length && !registered ; indexOfCourse++){
            if(courseArray[indexOfCourse] == courseNum){
                //insert into list by index size
                int insertIndex = 0 ;
                Iterator<Integer> it = courseIndex.iterator();
                if(!it.hasNext()) {
                    courseIndex.add(insertIndex, indexOfCourse);
                    registered = true;
                }
                else{
                    int listIndexPrevValue = -1;
                    while (it.hasNext()){
                        int listIndexValue = it.next();
                        if(indexOfCourse == listIndexValue)
                            throw new Error("already registered");
                        else if(listIndexPrevValue < indexOfCourse && indexOfCourse < listIndexValue) {
                            courseIndex.add(insertIndex, indexOfCourse);
                        }
                        listIndexPrevValue = listIndexValue;
                        insertIndex++;
                    }
                }
            }
        }
        if(!registered)
            throw new Error(" no such course");
    }

    public Integer[] getCourseArray(){
        return (Integer[])courseIndex.toArray();
    }

}
