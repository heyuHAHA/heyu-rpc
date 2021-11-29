package transport;

public interface EndPointManager {
    void init();
    void destroy();
    void addEndpoint(EndPoint endPoint);
    void removeEndPoint(EndPoint endPoint);
}
