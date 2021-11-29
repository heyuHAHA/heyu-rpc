package rpc;

public abstract class AbstractNode implements Node{

    protected URL url;

    public AbstractNode(URL url) {
        this.url = url;
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public String desc() {
        return null;
    }

    @Override
    public URL getUrl() {
        return null;
    }
}
