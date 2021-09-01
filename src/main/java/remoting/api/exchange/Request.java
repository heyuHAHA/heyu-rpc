package remoting.api.exchange;

import java.util.concurrent.atomic.AtomicLong;

public class Request {

    private final long mId;
    private static final AtomicLong INVOKE_ID = new AtomicLong(0);

    public Request() {
        mId = newId();
    }

    private static long newId() {
        return INVOKE_ID.getAndIncrement();
    }

    public long getId() {
        return mId;
    }
}
