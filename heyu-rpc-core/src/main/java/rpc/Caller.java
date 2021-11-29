package rpc;

public interface Caller<T>  extends Node{
    Response call(Request request);
}
