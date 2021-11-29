package rpc;

public interface Referer<T> extends Caller<T>, Node {
    /**
     * 当前使用该referer的调用数
     *
     * @return
     */
    int activeRefererCount();
}
