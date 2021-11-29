package exception;

public class RpcServiceException extends RuntimeException{

    public RpcServiceException(String message) {
        super(message);
    }
}
