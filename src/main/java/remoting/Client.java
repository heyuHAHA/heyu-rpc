package remoting;

public interface Client extends EndPoint, IdleSensible{

    void reconnect() throws RemotingException;

    void reset(Object parameters);
}
