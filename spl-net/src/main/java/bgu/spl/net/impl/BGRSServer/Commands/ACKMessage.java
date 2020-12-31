package bgu.spl.net.impl.BGRSServer.Commands;



import java.io.Serializable;


public class ACKMessage {
    public Serializable sendACK(String commandOpCode, String printMessage){
        String result = "12" + commandOpCode + printMessage + "\0";
        return  result;//UTF-16 by default
    }
}
