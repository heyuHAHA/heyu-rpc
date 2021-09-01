package remoting.api.transport.dispatcher;

import remoting.api.Channel;
import remoting.api.ChannelHandler;
import remoting.api.RemotingException;

public class ChannelEventRunnable  implements Runnable{

    private final ChannelHandler handler;
    private final Channel channel;
    private final ChannelState state;
    private final Throwable exception;
    private final Object message;

    public ChannelEventRunnable(Channel channel, ChannelHandler handler, ChannelState state) {
        this(channel, handler, state, null);
    }

    public ChannelEventRunnable(Channel channel, ChannelHandler handler, ChannelState state, Object message) {
        this(channel, handler, state, message, null);
    }

    public ChannelEventRunnable(Channel channel, ChannelHandler handler, ChannelState state, Throwable t) {
        this(channel, handler, state, null, t);
    }

    public ChannelEventRunnable(Channel channel, ChannelHandler handler, ChannelState state, Object message, Throwable exception) {
        this.channel = channel;
        this.handler = handler;
        this.state = state;
        this.message = message;
        this.exception = exception;
    }

    @Override
    public void run() {
        if (state == ChannelState.RECEIVED) {
            try {
                handler.received(channel,message);
            } catch (Exception e) {

            }
        } else {
            switch (state) {
                case CONNECTED:
                    try {
                        handler.connected(channel);
                    } catch (RemotingException e) {
                        //log
                    }
                    break;
                case DISCONNECTED:
                    try {
                        handler.disconnected(channel);
                    } catch (RemotingException e) {
                        e.printStackTrace();
                    }
                    break;
                case SENT:
                    try {
                        handler.sent(channel,message);
                    } catch (RemotingException e) {
                        e.printStackTrace();
                    }
                    break;
                case CAUGHT:
                    try {
                        handler.caught(channel,exception);
                    } catch (RemotingException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    //log
                    System.out.println("unknow state");

            }
        }
    }


    public enum ChannelState {
        CONNECTED,
        DISCONNECTED,
        SENT,
        RECEIVED,
        CAUGHT
    }
}
