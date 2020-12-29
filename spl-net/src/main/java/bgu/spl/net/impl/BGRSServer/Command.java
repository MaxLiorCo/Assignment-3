package bgu.spl.net.impl.BGRSServer;

import java.io.Serializable;

public interface Command<T> extends Serializable {
    //arg will be database
    Serializable execute(T arg);
}