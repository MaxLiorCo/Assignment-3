package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Server;

public class ReactorMain {
    public static void main(String[] args) {
        Database db = Database.getInstance();//one shared object


        Server.reactor(
                Integer.decode(args[1]),
                Integer.decode(args[0]), //port
                () -> new BGRSProtocol(db), //protocol factory
                BGRSEncoderDecoder::new //message encoder decoder factory
        ).serve();

    }
}
