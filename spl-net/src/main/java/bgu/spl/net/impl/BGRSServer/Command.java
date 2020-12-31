package bgu.spl.net.impl.BGRSServer;

import java.io.Serializable;

public interface Command<T> extends Serializable {
    Serializable execute(T protocol);
    default String getUserName(String message){
        String userName;
        int ind = message.indexOf('\0');
        return message.substring(0, ind);
    }
    default String getPassword(String message){
        int ind = message.indexOf('\0');
        return message.substring(ind + 1, message.length() - 1);
    }
    default Integer getCourseNum(String message){
        return Integer.decode(message);
    }

}
