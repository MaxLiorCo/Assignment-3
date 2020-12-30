package bgu.spl.net.impl.BGRSServer.Commands;

import java.io.Serializable;

public class ERRMessage {
    public Serializable sendERR(String commandOpCode){
        String result = "13" + commandOpCode;
        return  result; //UTF-16 by default
    }
}
