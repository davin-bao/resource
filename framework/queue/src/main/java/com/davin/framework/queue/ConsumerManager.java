package com.davin.framework.queue;

import com.davin.framework.queue.config.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Consumer manager
 *
 * @author davin.bao
 * @date 2024/6/5
 */
public class ConsumerManager<V> extends Thread {
    private final Logger log = LoggerFactory.getLogger(ConsumerManager.class);
    private final BlockingQueue<V> queue;
    private final Consumer<V> consumer;
    private final QueueConfig config;
    private List<ConsumerThread<V>> threads = new ArrayList<>();
    public ConsumerManager(BlockingQueue<V> queue, Consumer<V> consumer, QueueConfig config) {
        this.queue = queue;
        this.consumer = consumer;
        this.config = config;
    }

    @Override
    public void run() {
        for (int i = 0; i < config.getMinConsumerCount(); i++) {
            addConsumer();
        }
        while (true) {
            int remaining = queue.remainingCapacity();
            if (remaining < config.getRemainingLowerPercent() * config.getCapacity() && threads.size() < config.getMaxConsumerCount()) {
                log.debug("Queue fulled");
                addConsumer();
                continue;
            }

            if (remaining > config.getRemainingUpperPercent() * config.getCapacity() && threads.size() > config.getMinConsumerCount()) {
                log.debug("Queue empty");
                removeConsumer();
            }
        }
    }

    private void addConsumer() {
        ConsumerThread<V> thread = new ConsumerThread<>(queue, consumer, config);
        threads.add(thread);
        long alive = threads.stream().filter(Thread::isAlive).count();
        log.debug("【{}】Add consumer, alive: {}", thread.getId(), alive);
        thread.start();
    }

    private void removeConsumer() {
        ConsumerThread<V> thread = threads.get(0);
        thread.stopConsumer();
        threads.remove(thread);
        long alive = threads.stream().filter(Thread::isAlive).count();
        log.debug("【{}】Remove consumer, alive: {}", thread.getId(), alive);
    }
}
