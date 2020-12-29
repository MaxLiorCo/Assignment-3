package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessagingProtocol;

import java.io.Serializable;


public class BGRSProtocol<T> implements MessagingProtocol<Serializable> {

    //T will be Database
    private T arg;

    public BGRSProtocol(T arg) {
        this.arg = arg;
    }

    @Override
    public Serializable process(Serializable msg) {
        return ((Command) msg).execute(arg);
    }

    @Override
    public boolean shouldTerminate() {
        return false; // is taken care of by reactor
    }
    /*   private Short opcode;
    private int msgIndex;
    private String response;
    private Database db;
    private String[] msgArray;

    public BGRSProtocol(Database _db){
        Short opcode = null;
        int msgIndex = 0; //opcode is first
        String response = null;
        db = _db;
        msgArray = new String[3]; //Max possible message partitions
    }
    @Override
    public String process(Command msg) {

        //beginning new request from client
        if( opcode == null){
            opcode = Short.parseShort(msg);
            response = null;
        }

        switch(opcode) {
            case 1:
                response = ADMINREG();
            case 2:
                response = STUDENTREG();
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
        }
        return response;
    }

    private String ADMINREG(){

        msgIndex ++ ;
        return response;
    }

    private String STUDENTREG(){
        return response;
    }*/

}