package codec;

import transport.Channel;

import java.io.IOException;

public interface Codec {
    byte[] encode(Channel channel, Object message) throws IOException;

    Object decode(Channel channel, String remoteIp, byte[] buffer) throws IOException;
}
