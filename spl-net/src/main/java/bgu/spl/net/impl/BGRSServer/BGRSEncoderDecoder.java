package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.Commands.*;


import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;



public class BGRSEncoderDecoder implements MessageEncoderDecoder<Serializable> {
    private byte[] bytes = new byte[1 << 10];
    int counter = 0;
    int len = 0;
    Short op_code = null;

    @Override
    public Command decodeNextByte(byte nextByte) {
       // System.out.println(Integer.toBinaryString((nextByte+256)%256));
        if (nextByte == '\0') { //next byte equals null
            counter++;
        }
        pushByte(nextByte);
        if (op_code == null & len == 2)
            op_code = bytesToShort(bytes);
        if (op_code != null) {
            switch (op_code) {
                case 1:
                    if (counter == 3)
                        return new ADMINREGCommand(popString());
                    break;
                case 2:
                    if (counter == 3)
                        return new STUDENTREGCommand(popString());
                    break;
                case 3:
                    if (counter == 3)
                        return new LOGINCommand(popString());
                    break;
                case 4:
                    reset();
                    return new LOGOUTCommand();
                case 5:
                    if (len == 4) {
                        return new COURSEREGCommand(bytesToShort(getBytes()));
                    }
                    break;
                case 6:
                    if (len == 4) {
                        return new KDAMCHECKCommand(bytesToShort(getBytes()));
                    }
                    break;
                case 7:
                    if (len == 4) {
                        return new COURSESTATCommand(bytesToShort(getBytes()));
                    }
                    break;
                case 8:
                    if (counter == 2)
                        return new STUDENTSTATCommand(popString());
                    break;
                case 9:
                    if (len == 4) {
                        return new ISREGISTEREDCommand(bytesToShort(getBytes()));
                    }
                    break;
                case 10:
                    if (len == 4) {
                        return new UNREGISTERCommand(bytesToShort(getBytes()));
                    }
                    break;
                case 11:
                    reset();
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

    private byte[] getBytes() {
        byte[] temp = new byte[2];
        temp[0] = bytes[2];
        temp[1] = bytes[3];
        return temp;
    }

    private String popString() {
        String result = new String(bytes, 2, len, StandardCharsets.UTF_8);
        reset();
        return result;
    }


    /**
     * Encodes message sent by the Server.
     * @param message the message to encode
     * @return Array of bytes represent the message.
     */
    @Override
    public byte[] encode(Serializable message) {
        return (byte[])message;
    }

    private short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }

    private void reset(){
        len = 0;
        op_code = null;
        counter = 0;
    }
}
