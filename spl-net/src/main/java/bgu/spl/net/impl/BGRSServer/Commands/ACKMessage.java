package bgu.spl.net.impl.BGRSServer.Commands;

import sun.nio.cs.UTF_8;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class ACKMessage {
    public Serializable sendACK(String commandOpCode, String printMessage){
        String result = "12" + commandOpCode + printMessage + "\0";
        return  result;//UTF-16 by default
    }
}
