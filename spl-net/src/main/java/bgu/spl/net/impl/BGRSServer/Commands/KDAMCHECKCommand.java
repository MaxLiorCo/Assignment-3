package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.*;

import java.io.Serializable;
import java.util.Arrays;

public class KDAMCHECKCommand implements Command<BGRSProtocol<?>> {
    short courseNum;

    public KDAMCHECKCommand(short courseNum) {
        this.courseNum = courseNum;

    }

    @Override
    public Serializable execute(BGRSProtocol<?> protocol) {
        User user = protocol.getUser();
        if (user == null || user.isAdmin()) // client not logged in or command was sent by an admin
            return new ERRMessage().sendERR("6");
        Database db = Database.getInstance();
        Course course = (db.getCourses()).get(courseNum);
        if (course == null) // such course doesn't exist
            return new ERRMessage().sendERR("6");
        int[] kdam = course.getKdamCourses();
        String arr = Arrays.toString(kdam);
        arr = arr.replaceAll("\\s+", ""); // remove all spaces
        return new ACKMessage().sendACK("6", arr);
    }
}
