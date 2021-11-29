package rpc;

public class DefaultResponse implements Response{

    private Object value;
    private long requestId;

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public long getRequestId() {
        return requestId;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
}
