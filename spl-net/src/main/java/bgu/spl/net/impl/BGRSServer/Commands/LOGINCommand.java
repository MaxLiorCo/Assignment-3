package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.User;

import java.io.Serializable;
import java.util.Map;

/**
 * Login command sent by client.
 * If client didn't login already, proceed to check if username and password matching to the ones store in the database.
 *
 * @return {@link ACKMessage} message upon successful login, and {@link ERRMessage} message if one of the above happens.
 */
public class LOGINCommand implements Command<BGRSProtocol<?>> {
    String message;

    public LOGINCommand(String _message) {
        message = _message;

    }

    @Override
    public Serializable execute(BGRSProtocol<?> protocol) {
        if (protocol.getUser() != null) // user already logged in
            return new ERRMessage().sendERR("3");
        Database db = Database.getInstance();
        String userName = getUserName(message);
        String password = getPassword(message);
        Map<String, User> users = db.getRegisteredUsers();
        User user = users.get(userName);
        if (user == null || (!(password.equals(user.getPassword())) | user.isLoggedIn())) // such user doesn't exist or password doesn't match or this user already logged-in by another client
            return new ERRMessage().sendERR("3");
        // All of the above doesn't happen, means that: username and password given by the client are correct, and no-one uses the user right now.
        //TODO maybe this is unnecessary?
        synchronized (user) { // synchronized to prevent situations where 2 different clients try to login concurrently to the same user.
            if (!user.isLoggedIn()) {
                user.setLoggedIn(true);
                protocol.setUser(user);
            }
            else
                return new ERRMessage().sendERR("3");
        }
        return new ACKMessage().sendACK("3", "");
    }
}