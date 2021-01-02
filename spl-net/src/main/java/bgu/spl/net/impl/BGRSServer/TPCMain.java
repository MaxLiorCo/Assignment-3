package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Server;

public class TPCMain {
    public static void main(String[] args) {
        Database db = Database.getInstance();//one shared object
        boolean initialized = db.initialize("./Courses.txt");

        if (initialized) {
            Server.threadPerClient(
                    Integer.decode(args[0]), //port
                    () -> new BGRSProtocol(db), //protocol factory
                    BGRSEncoderDecoder::new //message encoder decoder factory
            ).serve();
        }
        //TODO: this part is for the the TESTERS of the program, decide if you want to check it
        /*
        else {
            System.out.println("Couldn't initialize database, Exiting...");
            System.exit(1);
        }
        */
    }
}
