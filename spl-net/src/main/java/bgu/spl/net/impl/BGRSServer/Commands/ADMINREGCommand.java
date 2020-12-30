package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.User;

import java.io.Serializable;

public class ADMINREGCommand implements Command<BGRSProtocol> {
    String message;

    public ADMINREGCommand(String _message){
        message = _message;
    }

    /**
     * Registers client as Admin.
     * @param protocol
     * @return ACKMessage apuon successfull registeration, false if user is already taken.
     */
    @Override
    public Serializable execute(BGRSProtocol protocol) {
        String userName = "";
        String password = "";
        int ind = message.indexOf('\0');
        userName = message.substring(0, ind);
        password = message.substring(ind + 1, message.length() - 1);
     // boolean wasRegistered = db.registerUser(new User(userName, password, true));
        if (wasRegistered)
           return new ACKMessage().sendACK("1", "Registered successfully");
        else
            return new ERRMessage().sendERR("1");
    }
}
