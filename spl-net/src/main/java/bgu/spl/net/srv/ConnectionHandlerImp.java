package bgu.spl.net.srv;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.MessagingProtocol;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

//TODO delete this shit
public class ConnectionHandlerImp implements ConnectionHandler<String>{
    private final MessagingProtocol<String> protocol;
    private final MessageEncoderDecoder<String> encdec;
    private Socket sock;

    public ConnectionHandlerImp(MessagingProtocol _protocol, MessageEncoderDecoder _encdec, Socket _sock){
        protocol = _protocol;
        encdec = _encdec;
        sock = _sock;
    }
    public void run() {
        try (Socket sock = this.sock; //added "this"
             BufferedInputStream in = new BufferedInputStream(sock.getInputStream());
             BufferedOutputStream out = new BufferedOutputStream(sock.getOutputStream())) {

            int read;
            while (!protocol.shouldTerminate() && (read = in.read()) >= 0) {
                String nextMessage = encdec.decodeNextByte((byte) read);
                if (nextMessage != null) {
                    String response = protocol.process(nextMessage);
                    if (response != null) {
                        out.write(encdec.encode(response));
                        out.flush();
                    }
                }
            }
        } catch (IOException ex) { ex.printStackTrace(); }
    }
    @Override
    public void close(){
        try {sock.close();}
        catch (IOException ex){

        }
    }
}
