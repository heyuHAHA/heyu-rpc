package test;



public class MyService implements Service {
    @Override
    public String testHello(String str) {
        return "hello " +str;
    }

    @Override
    public Object get(Object o) {
        return null;
    }
}
