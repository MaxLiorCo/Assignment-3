package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class COURSESTATCommand implements Command<BGRSProtocol<?>> {
    short courseNum;

    public COURSESTATCommand(short courseNum) {
        this.courseNum = courseNum;

    }

    @Override
    public Serializable execute(BGRSProtocol<?> protocol) {
        User user = protocol.getUser();
        if (user == null || !user.isAdmin())
            return new ERRMessage().sendERR("7");
        Database db = Database.getInstance();
        Course course = db.getCourses().get(courseNum);
        if (course == null) // such course doesn't exist
            return new ERRMessage().sendERR("7");
        String courseData = "";
        int totalSeats = course.getNumOfMaxStudents();
        int numOfRegistered = course.getNumOfRegisteredStudents();
        List<String> registeredUsers = course.getRegisteredStudentList();
        Collections.sort(registeredUsers); // ordering alphabetically
        String[] array = registeredUsers.toArray(new String[0]); //converting list to array
        String arrToString = Arrays.toString(array); //converting array to string
        arrToString = arrToString.replaceAll("\\s+", ""); // removes all spaces
        courseData += "Course: (" + course.getCourseNum() + ") " + course.getCourseName() + "\n";
        courseData += "Seats Available: " + (course.getNumOfMaxStudents()-course.getNumOfRegisteredStudents()) + "/" + totalSeats + "\n";
        courseData += "Students Registered: " + arrToString;
        return new ACKMessage().sendACK("7", courseData);


    }
}
