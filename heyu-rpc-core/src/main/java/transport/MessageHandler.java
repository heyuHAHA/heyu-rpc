package transport;

public interface MessageHandler {
    Object handle(Channel channel, Object message);
}
