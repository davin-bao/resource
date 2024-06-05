package com.davin.vm;

import com.davin.framework.queue.Consumer;

/**
 * @author davin.bao
 * @date 2024/6/5
 */
public class ConsumerImpl<String> implements Consumer<String> {
    /**
     * 消费消息
     *
     * @param payload 消息
     */
    @Override
    public void handler(String payload) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
