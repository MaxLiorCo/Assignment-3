package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Database;

import java.io.Serializable;

public class STUDENTREGCommand  implements Command<BGRSProtocol> {
    String message;
    public STUDENTREGCommand(String _message){
        message = _message;

    }
    @Override
    public Serializable execute(BGRSProtocol protocol) {
        return null;
    }
}