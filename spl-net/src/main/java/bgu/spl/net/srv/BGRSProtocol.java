package bgu.spl.net.srv;

import bgu.spl.net.api.MessagingProtocol;

public class BGRSProtocol implements MessagingProtocol<String> {
    private Short opcode;
    private int currParam;
    private String response;
    private Database db;

    public BGRSProtocol(){
        Short opcode = null;
        int currParam = 0;
        String response = null;
        db = Database.getInstance();
    }
    @Override
    public String process(String msg) {

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
        return response;
    }

    private String STUDENTREG(){
        return response;
    }
    @Override
    public boolean shouldTerminate() {
        return false; // TODO: change
    }
}
