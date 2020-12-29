package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Database;

import java.io.Serializable;

public class UNREGISTERCommand  implements Command<Database> {
    String message;
    public UNREGISTERCommand(String _message){
        message = _message;

    }
    @Override
    public Serializable execute(Database db) {
        return null;
    }
}