package com.davin.framework.queue;

import com.davin.framework.queue.config.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/**
 * @author davin.bao
 * @date 2024/6/3
 */
public class ConsumerThread<V> extends Thread {
    private final Logger log = LoggerFactory.getLogger(ConsumerThread.class);
    private boolean stopSignal = false;
    private final BlockingQueue<V> queue;
    private final Consumer<V> consumer;
    private final QueueConfig config;

    public ConsumerThread(BlockingQueue<V> queue, Consumer<V> consumer, QueueConfig config) {
        this.queue = queue;
        this.consumer = consumer;
        this.config = config;
    }

    public void stopConsumer() {
        stopSignal = true;
    }

    @Override
    public void run() {
        while (true) {
            try {
                V item = queue.poll(config.getPollTimeout(), config.getPollTimeoutUnit());
                if (item == null) {
                    log.debug("【{}】Waiting ...", getId());
                    if (stopSignal) {
                        break;
                    }
                    continue;
                }

                try {
                    // 消息消费
                    consumer.handler(item);
                    log.debug("【{}】Consuming {}", getId(), item.toString());
                } catch (Exception e) {
                    log.error("【{}】Consuming exception：{}", getId(), e.getMessage());
                }
            } catch (Exception e) {
                log.error("【{}】Get payload from queue exception", getId(), e);
                break;
            }
        }
        log.info("【{}】Exit consume", getId());
    }
}
