package bgu.spl.net.impl.BGRSServer.Commands;

import bgu.spl.net.impl.BGRSServer.Command;
import bgu.spl.net.impl.BGRSServer.Database;

import java.io.Serializable;

public class MYCOURSESCommand  implements Command<Database> {
    @Override
    public Serializable execute(Database db) {
        return null;
    }
}
