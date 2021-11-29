package rpc;

import java.util.concurrent.Future;

public interface ResponseFuture extends Future,Response{
    void onSuccess(Response response);

    void onFailure(Response response);

    long getCreateTime();

    void setReturnType(Class<?> clazz);


}
