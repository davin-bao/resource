package com.davin.framework.queue;

import com.davin.framework.queue.config.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Elastic queue
 * @author davin.bao
 * @date 2024/6/5
 */
public class ElasticQueue<V> {
    private final static Logger log = LoggerFactory.getLogger(ElasticQueue.class);
    private BlockingQueue<V> queue = null;
    private Consumer<V> consumer = null;
    private QueueConfig config = null;
    private ConsumerManager<V> consumerManager = null;

    public ElasticQueue(BlockingQueue<V> queue, Consumer<V> consumer, QueueConfig config) {
        this.queue = queue;
        this.consumer = consumer;
        this.config = config;
        this.consumerManager = new ConsumerManager<>(queue, consumer, config);
        this.consumerManager.start();
    }

    public boolean produce(V payload) {
        return queue.offer(payload);
    }

    public boolean produce(V payload, long timeout, TimeUnit unit) throws InterruptedException {
        return queue.offer(payload, timeout, unit);
    }
}
