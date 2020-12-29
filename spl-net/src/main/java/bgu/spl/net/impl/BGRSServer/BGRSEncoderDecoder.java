package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.Commands.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class BGRSEncoderDecoder implements MessageEncoderDecoder<Command> {
    private byte[] bytes = new byte[1 << 10];
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
        if (nextByte == '\0') { //next byte equals null
            counter++;
        }
        pushByte(nextByte);
        if (op_code == null & bytes.length == 2)
            op_code = bytesToShort(bytes);
        if (op_code != null) {
            switch (op_code) {
                case 1:
                    if (counter == 2)
                        return new ADMINREGCommand(popString());
                case 2:
                    if (counter == 2)
                        return new STUDENTREGCommand(popString());
                case 3:
                    if (counter == 2)
                        return new LOGINCommand(popString());
                case 4:
                    return new LOGOUTCommand();
                case 5:
                    if (len == 4) {
                        return new COURSEREGCommand(popString());
                    }
                case 6:
                    if (len == 4) {
                        return new KDAMCHECKCommand(popString());
                    }
                case 7:
                    if (len == 4) {
                        return new COURSESTATCommand(popString());
                    }
                case 8:
                    if (counter == 1)
                        return new STUDENTSTATCommand(popString());
                case 9:
                    if (len == 4) {
                        return new ISREGISTEREDCommand(popString());
                    }
                case 10:
                    if (len == 4) {
                        return new UNREGISTERCommand(popString());
                    }
                case 11:
                    return  new MYCOURSESCommand();
            }
        }
        return null;
    }


    private void pushByte(byte nextByte) {
        if (len == bytes.length)
            bytes = Arrays.copyOf(bytes, len * 2);
        bytes[len++] = nextByte;
    }

    private String popString() {
        String result = new String(bytes, 2, len, StandardCharsets.UTF_8);
        len = 0;
        op_code = null;
        counter = 0;
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
