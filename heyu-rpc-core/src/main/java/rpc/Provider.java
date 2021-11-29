package rpc;

import java.lang.reflect.Method;

public interface Provider<T> extends Caller<T> {

    Class<T> getInterface();

    Method lookupMethod(String methodName, String methodDesc);

    T getImpl();
}
