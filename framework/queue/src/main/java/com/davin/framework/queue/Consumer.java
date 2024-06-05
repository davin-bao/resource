package com.davin.framework.queue;

/**
 * Consumer handle
 * @author davin.bao
 * @date 2024/6/5
 */
public interface Consumer<V> {
    /**
     * consume payload
     * @param payload Payload
     */
    public void handler(V payload);
}
