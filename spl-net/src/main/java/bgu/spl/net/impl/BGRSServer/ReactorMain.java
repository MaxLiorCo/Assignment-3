package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.rci.ObjectEncoderDecoder;
import bgu.spl.net.impl.rci.RemoteCommandInvocationProtocol;
import bgu.spl.net.srv.Server;

public class ReactorMain {
    public static void main(String[] args) {
        Database db = Database.getInstance();//one shared object
        boolean initialized = db.initialize("spl-net/Courses.txt"); //TODO how to actually initialize
        if (!initialized) {
            System.out.println("Couldn't initialize database, Exiting...");
            System.exit(1);
        }
        Server.reactor(
                Integer.decode(args[1]),
                Integer.decode(args[0]), //port
                () ->  new BGRSProtocol(db), //protocol factory
                BGRSEncoderDecoder::new //message encoder decoder factory
        ).serve();

    }
}
