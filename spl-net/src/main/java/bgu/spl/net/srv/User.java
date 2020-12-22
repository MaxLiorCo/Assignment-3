package bgu.spl.net.srv;

import java.io.*;
import java.util.ArrayList;
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
}
