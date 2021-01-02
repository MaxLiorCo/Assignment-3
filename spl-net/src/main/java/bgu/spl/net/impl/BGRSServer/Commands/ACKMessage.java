package bgu.spl.net.impl.BGRSServer.Commands;



import java.io.Serializable;
import java.nio.charset.StandardCharsets;


public class ACKMessage {
    Serializable sendACK(String commandOpCode, String printMessage){
        byte[] stringMessage = printMessage.getBytes(StandardCharsets.UTF_8);
        byte [] result = new byte[4+stringMessage.length + 1];

        //insert Opcode
        byte[] twoByte = shortToBytes((short)12);
        result[0]= twoByte[0];
        result[1]= twoByte[1];
        //insert Opcode message
        twoByte = shortToBytes(Short.parseShort(commandOpCode));
        result[2]= twoByte[0];
        result[3]= twoByte[1];

        //insert message to client
        System.arraycopy(stringMessage, 0, result, 4, result.length - 1 - 4); //copy string into result
        result[result.length-1] = '\0';

        return result;
    }
    byte[] shortToBytes(short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }
}

