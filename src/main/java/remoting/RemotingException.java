package remoting;

import remoting.transport.AbstractChannel;

public class RemotingException extends Exception{

    private static final long serialVersionUID = -3081947414580364215L;


    public RemotingException(Channel abstractChannel, String failed_to_send_message) {
    }
}
