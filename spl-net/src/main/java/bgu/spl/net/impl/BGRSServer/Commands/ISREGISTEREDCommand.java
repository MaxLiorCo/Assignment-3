package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Database;

import java.io.Serializable;

public class ISREGISTEREDCommand  implements Command<Database> {
    String message;
    public ISREGISTEREDCommand(String _message){
        message = _message;

    }
    @Override
    public Serializable execute(Database db) {
        return null;
    }
}