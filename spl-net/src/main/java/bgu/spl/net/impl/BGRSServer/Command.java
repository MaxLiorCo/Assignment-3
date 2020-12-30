package bgu.spl.net.impl.BGRSServer;

import java.io.Serializable;

public interface Command<T> extends Serializable {
    Serializable execute(T protocol);
}
