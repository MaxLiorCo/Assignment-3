package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;

import java.io.Serializable;
import java.util.Arrays;

public class MYCOURSESCommand implements Command<BGRSProtocol<?>> {
    @Override
    public Serializable execute(BGRSProtocol<?> protocol) {
        User user = protocol.getUser();
        if (user == null || user.isAdmin()) // client not logged in or is an admin
            return new ERRMessage().sendERR("11");
        Integer[] registeredCourses = user.getRegisteredCoursesArray(); // array of registered courses
        String arrToString = Arrays.toString(registeredCourses); // convert array to string
        arrToString = arrToString.replaceAll("\\s+", ""); // remove all spaces from string
        return new ACKMessage().sendACK("11", arrToString);
    }
}
