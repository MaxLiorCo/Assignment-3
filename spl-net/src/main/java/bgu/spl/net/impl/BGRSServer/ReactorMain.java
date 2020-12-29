package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.rci.ObjectEncoderDecoder;
import bgu.spl.net.impl.rci.RemoteCommandInvocationProtocol;
import bgu.spl.net.srv.Server;

public class ReactorMain {
    public static void main(String[] args) {
        Database db = Database.getInstance();//one shared object
        db.initialize("/Courses.txt"); //TODO how to actually initialize

        Server.reactor(
                Runtime.getRuntime().availableProcessors(),
                7777, //port
                () ->  new BGRSProtocol(db), //protocol factory
                BGRSEncoderDecoder::new //message encoder decoder factory
        ).serve();

    }
}
