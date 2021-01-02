package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.User;

import java.io.Serializable;
import java.util.Arrays;

public class STUDENTSTATCommand implements Command<BGRSProtocol<?>> {
    String message;

    public STUDENTSTATCommand(String _message) {
        message = _message;

    }

    @Override
    public Serializable execute(BGRSProtocol<?> protocol) {
        User user = protocol.getUser();
        if (user == null || !user.isAdmin()) // client not logged in or no an admin
            return new ERRMessage().sendERR("8");
        String userName = getUserName(message);
        Database db = Database.getInstance();
        User toStat = db.getRegisteredUsers().get(userName);
        if (toStat == null || toStat.isAdmin()) // such student doesn't exist, or it is an admin
            return new ERRMessage().sendERR("8");
        String studentData = "";
        Integer[] registeredCourses = toStat.getRegisteredCoursesArray(); // array of registered courses
        String arrToString = Arrays.toString(registeredCourses); // convert array to string
        arrToString = arrToString.replaceAll("\\s+", ""); // remove all spaces from string
        studentData += "Student: " + toStat.getUserName() + "\n";
        studentData += "Courses: " + arrToString;
        return new ACKMessage().sendACK("8", studentData);
    }
}