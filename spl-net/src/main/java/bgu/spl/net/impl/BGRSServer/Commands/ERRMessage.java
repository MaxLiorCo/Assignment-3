package bgu.spl.net.impl.BGRSServer.Commands;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class ERRMessage {
    public Serializable sendERR(String commandOpCode){
        byte [] result = new byte[4];

        //insert Opcode
        byte[] twoByte = shortToBytes((short)13);
        result[0]= twoByte[0];
        result[1]= twoByte[1];
        //insert Opcode message
        twoByte = shortToBytes(Short.parseShort(commandOpCode));
        result[2]= twoByte[0];
        result[3]= twoByte[1];

        return result;
    }

    public byte[] shortToBytes(short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }
}
