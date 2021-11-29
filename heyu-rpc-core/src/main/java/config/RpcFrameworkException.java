package config;

public class RpcFrameworkException extends RuntimeException{
    public RpcFrameworkException() {
        super();
    }

    public RpcFrameworkException(String message) {
        super(message);
    }

    public RpcFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcFrameworkException(Throwable cause) {
        super(cause);
    }

    protected RpcFrameworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
