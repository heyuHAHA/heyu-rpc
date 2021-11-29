package transport;

/**
 *
 * @param <T>
 */
public interface SharedObjectFactory<T> {
    /**
     * 创建对象
     *
     * @return
     */
    T makeObject();

    /**
     * 重建对象
     *
     * @param obj
     * @param async
     * @return
     */
    boolean rebuildObject(T obj, boolean async);
}
