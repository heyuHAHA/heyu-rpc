package rpc;

public interface Protocol {

   <T> Exporter<T> export(Provider<T> provider, URL url);


   <T> Referer<T> refer(Class<T> clz, URL url, URL serviceUrl);


   void destroy();
}
