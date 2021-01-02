package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessagingProtocol;

import java.io.Serializable;


public class BGRSProtocol<Database> implements MessagingProtocol<Serializable> {

    //T will be Database
    private Database arg;
    private User user;
    private boolean terminate;

    public BGRSProtocol(Database arg) {
        this.arg = arg;
        user = null;
        terminate = false;
    }

    @Override
    public Serializable process(Serializable msg) {
        return ((Command)msg).execute(this);
    }

    @Override
    public boolean shouldTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
