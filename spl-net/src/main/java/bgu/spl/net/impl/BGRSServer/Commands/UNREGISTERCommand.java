package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.*;

import java.io.Serializable;

public class UNREGISTERCommand implements Command<BGRSProtocol<?>> {
    String message;

    public UNREGISTERCommand(String _message) {
        message = _message;

    }

    @Override
    public Serializable execute(BGRSProtocol<?> protocol) {
        User user = protocol.getUser();
        if (user == null || user.isAdmin()) // client not logged in or no an admin
            return new ERRMessage().sendERR("10");
        Database db = Database.getInstance();
        Course courseToRemove = db.getCourses().get(getCourseNum(message));
        if (courseToRemove == null || !user.removeCourse(courseToRemove)) // such course doesn't exist, or user isn't registered to this course
            return new ERRMessage().sendERR("10");
        return new ACKMessage().sendACK("10", "");
    }
}