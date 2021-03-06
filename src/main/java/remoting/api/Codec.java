package remoting.api;

import remoting.api.buffer.ChannelBuffer;

import java.io.IOException;

public interface Codec {
    void encode(Channel channel, ChannelBuffer channelBuffer, Object message) throws IOException;

    Object decode (Channel channel,ChannelBuffer channelBuffer) throws IOException;
}
