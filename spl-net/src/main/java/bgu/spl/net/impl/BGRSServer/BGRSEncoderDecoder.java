package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BGRSEncoderDecoder implements MessageEncoderDecoder<String> {
    private byte[] bytes = new byte[1 << 10];
    int counter = 0;
    int len = 0;

    /**
     * Decodes the message sent by user.
     * After 2 iterations, will return a string represents operation ID was requested by user.
     * (Assuming operation ID is represented in 2 bytes).
     * After that will decode (if necessary) the next bytes.
     * @param nextByte the next byte to consider for the currently decoded message.
     * @return String if finished, or NULL otherwise.
     */

    @Override
    public String decodeNextByte(byte nextByte) {
        if (nextByte == '\0'){ //next byte equals null
            return popString();
        }
        if (counter == 1) {
            counter++;
            pushByte(nextByte);
            return popOperationId();
        }
        pushByte(nextByte);
        return null;
    }

    private String popOperationId() {
        String op = new String(bytes, 0, 2, StandardCharsets.UTF_8);
        len = 0;
        return op;
    }

    private void pushByte(byte nextByte) {
        if (len == bytes.length)
            bytes = Arrays.copyOf(bytes, len * 2);
        bytes[len++] = nextByte;
        counter++;
    }

    private String popString() {
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }


    /**
     * Encodes message sent by the Server.
     * @param message the message to encode
     * @return Array of bytes represent the message.
     */
    @Override
    public byte[] encode(String message) {
        return message.getBytes(StandardCharsets.UTF_8);
    }
}
