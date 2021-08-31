package remoting.transport;

import common.URL;
import remoting.ChannelHandler;
import remoting.Codec;
import remoting.transport.codec.CodecAdapter;

public abstract class AbstractEndpoint extends AbstractPeer {

    private Codec codec;



    public AbstractEndpoint(URL url, ChannelHandler handler) {
        super(url, handler);
        this.codec = getChannelCodec(url);
    }

    /**
     * 从url里获取编解码方式
     * @param url
     * @return
     */
    protected Codec getChannelCodec(URL url) {
        String codecName = url.getProtocol();
        return new CodecAdapter(getCodecByCodecName(codecName));
    }

    //TODO 不能从高层直接New一个底层类，要用依赖倒置的想法把这个Codec对象注入进来
    protected abstract Codec getCodecByCodecName(String codecName);

    protected Codec getCodec() {
        return codec;
    }
}
