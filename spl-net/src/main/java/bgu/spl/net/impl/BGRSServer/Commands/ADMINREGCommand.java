package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.User;

import java.io.Serializable;

public class ADMINREGCommand implements Command<BGRSProtocol<?>> {
    String message;

    public ADMINREGCommand(String _message) {
        message = _message;
    }

    /**
     * Registers client as Admin.
     * @param protocol
     * @return ACKMessage upon successful registration, ERRMessage if user is already taken, or client
     * have already logged in to a user.
     */
    @Override
    public Serializable execute(BGRSProtocol<?> protocol) {
        Database db = Database.getInstance();
        if (protocol.getUser() != null)
            return new ERRMessage().sendERR("1");
        String userName = getUserName(message);
        String password = getPassword(message);
        boolean wasRegistered = db.registerUser(new User(userName, password, true));
        if (wasRegistered)
            return new ACKMessage().sendACK("1", "");
        else
            return new ERRMessage().sendERR("1");
    }
}
