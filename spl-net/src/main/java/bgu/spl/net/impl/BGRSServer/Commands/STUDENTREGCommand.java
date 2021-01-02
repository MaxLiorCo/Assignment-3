package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.User;

import java.io.Serializable;

public class STUDENTREGCommand implements Command<BGRSProtocol<?>> {
    String message;

    public STUDENTREGCommand(String _message) {
        message = _message;

    }

    @Override
    public Serializable execute(BGRSProtocol<?> protocol) {
        Database db = Database.getInstance();
        if (protocol.getUser() != null) // client already logged in
            return new ERRMessage().sendERR("2");
        String userName = getUserName(message);
        String password = getPassword(message);
        boolean wasRegistered = db.registerUser(new User(userName, password, false));
        if (wasRegistered) {
            return new ACKMessage().sendACK("2", "");
        }
        else
            return new ERRMessage().sendERR("2");
    }
}