package remoting.api.transport.codec;

import common.io.UnsafeByteArrayOutputStream;
import remoting.api.Channel;
import remoting.api.Codec;
import remoting.api.buffer.ChannelBuffer;

import java.io.IOException;

public class CodecAdapter implements Codec {
    private final Codec codec;

    public CodecAdapter (Codec codec) {
        this.codec = codec;
    }
    @Override
    //TODO
    public void encode(Channel channel, ChannelBuffer channelBuffer, Object message) throws IOException {
        UnsafeByteArrayOutputStream os = new UnsafeByteArrayOutputStream(1024);

    }

    @Override
    //TODO
    public Object decode(Channel channel, ChannelBuffer channelBuffer) throws IOException {
        return null;
    }
}
