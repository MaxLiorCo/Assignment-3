package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.Commands.KdamCheckCommand;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class BGRSEncoderDecoder implements MessageEncoderDecoder<Command> {
    private byte[] bytes = new byte[1 << 10];
    //private byte[] op_bytes = new byte[2];
    int counter = 0;
    int len = 0;
    Short op_code = null;

    /**
     * Decodes the message sent by user.
     * After 2 iterations, will return a string represents operation ID was requested by user.
     * (Assuming operation ID is represented in 2 bytes).
     * After that will decode (if necessary) the next bytes.
     * @param nextByte the next byte to consider for the currently decoded message.
     * @return String if finished, or NULL otherwise.
     */

    @Override
    public Command decodeNextByte(byte nextByte) {
/*        if (nextByte == '\0'){ //next byte equals null
            return popString();
        }
        if (counter == 1) {
            counter++;
            pushByte(nextByte);
            return popOperationId();
        }
        pushByte(nextByte);*/
        pushByte(nextByte);
        if (bytes.length == 2 & op_code == null){
            op_code = bytesToShort(bytes);
        } else {
                switch(op_code) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        //return new LogoutCommand();
                    case 5:
                        if (len == 4){
                            return new KdamCheckCommand();
                        }

                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                }
        }
        return null;
    }


    private void pushByte(byte nextByte) {
        if (len == bytes.length)
            bytes = Arrays.copyOf(bytes, len * 2);
        bytes[len++] = nextByte;
        counter++;
    }

    private String popString() {
        String result = new String(bytes, 2, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }


    /**
     * Encodes message sent by the Server.
     * @param message the message to encode
     * @return Array of bytes represent the message.
     */
    @Override
    public byte[] encode(Command message) {
        return null;
    }

    private short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
