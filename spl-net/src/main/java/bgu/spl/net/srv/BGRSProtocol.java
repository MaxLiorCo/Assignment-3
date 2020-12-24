package bgu.spl.net.srv;

import bgu.spl.net.api.MessagingProtocol;

public class BGRSProtocol implements MessagingProtocol<String> {


    @Override
    public String process(String msg) {
        return null;
    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }
}
