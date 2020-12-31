package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;

import java.io.Serializable;

public class ISREGISTEREDCommand implements Command<BGRSProtocol<?>> {
    String message;

    public ISREGISTEREDCommand(String _message) {
        message = _message;

    }

    @Override
    public Serializable execute(BGRSProtocol<?> protocol) {
        User user = protocol.getUser();
        if (user == null || user.isAdmin()) // if client isn't connected or is an admin
            return new ERRMessage().sendERR("9");
        Integer[] registeredCourses = user.getRegisteredCoursesArray();
        int courseToCheck = getCourseNum(message);
        for (Integer courseNum : registeredCourses) {
            if (courseNum == courseToCheck)
                return new ACKMessage().sendACK("9", "REGISTERED");
        }
        return new ACKMessage().sendACK("9", "NOT REGISTERED");
    }
}