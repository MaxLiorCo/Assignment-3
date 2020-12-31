package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.User;

import java.io.Serializable;

public class LOGOUTCommand implements Command<BGRSProtocol<?>> {
    @Override
    public Serializable execute(BGRSProtocol<?> protocol) {
        User user = protocol.getUser();
        if (user == null)
            return new ERRMessage().sendERR("4");
        user.setLoggedIn(false);
        protocol.setUser(null);
        protocol.setTerminate(true); // TODO i dont really know if this is right
        return new ACKMessage().sendACK("4", "");
    }
}