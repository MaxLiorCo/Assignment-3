package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.*;

import java.io.Serializable;

public class COURSEREGCommand implements Command<BGRSProtocol<?>> {
    String message;

    public COURSEREGCommand(String _message) {
        message = _message;

    }

    @Override
    public Serializable execute(BGRSProtocol<?> protocol) {
        Database db = Database.getInstance();
        User user = protocol.getUser();
        if (user == null || user.isAdmin()) // client didn't login or user is admin thus cannot do this command
            return new ERRMessage().sendERR("5");
        int courseNum = getCourseNum(message);
        Course course = db.getCourses().get(courseNum);
        if (course == null) // such course doesn't exist
            return new ERRMessage().sendERR("5");
        try {
            user.registerToCourse(course);
        } catch (Error err) { // occurs when no places left in this course, or when the student doesn't have all kdamCourses
            return new ERRMessage().sendERR("5");
        }
        return new ACKMessage().sendACK("5", ""); // no catch, so no error, and all of the above doesn't happen, thus student registered successfully.
    }
}
