package com.davin.service.impl;

import com.davin.framework.queue.ElasticQueue;
import com.davin.framework.queue.config.QueueConfig;
import com.davin.service.RedisQueueService;
import com.davin.vm.ConsumerImpl;
import org.redisson.api.RBoundedBlockingQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;


/**
 * @author davin.bao
 * @date 2024/6/4
 */
@Service
public class RedisQueueServiceImpl implements RedisQueueService {
    private final static Logger log = LoggerFactory.getLogger(RedisQueueServiceImpl.class);

    @Autowired
    private RedissonClient redissonClient;
    private ElasticQueue<String> elasticQueue;

    /**
     * 初始化
     */
    @Override
    public void init() {
        QueueConfig config = new QueueConfig.Builder()
                .capacity(10)
                .minConsumerCount(2)
                .maxConsumerCount(150)
                .pollTimeout(1)
                .pollTimeoutUnit(TimeUnit.SECONDS)
                .remainingUpperPercent(0.5f)
                .remainingLowerPercent(0.3f)
                .build();
        RBoundedBlockingQueue<String> queue = redissonClient.getBoundedBlockingQueue("test-queue");
        queue.trySetCapacity(config.getCapacity());
        elasticQueue = new ElasticQueue<>(queue, new ConsumerImpl<>(), config);
    }

    private ThreadPoolExecutor getExecutor() {
        return new ThreadPoolExecutor(
                5, 10,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 生产消息
     */
    @Override
    public void produce() {
        getExecutor().execute(() -> {
            long msgId = 0L;
            int sleep = 1000;
            boolean plus = false;
            while (true) {
                try {
                    msgId ++;
                    sleep = plus ? sleep + 20 : sleep - 20;

                    if (sleep <= 10) {
                        plus = true;
                    } else if(sleep > 1000) {
                        plus = false;
                    }

                    String msg = String.format("%s - msg", msgId);
                    boolean res = elasticQueue.produce(msg, 1, TimeUnit.SECONDS);
                    log.debug("Produce：{}, {}", msg, res);
                    Thread.sleep(sleep);
                } catch (Exception e) {
                    log.error("Produce exception", e);
                    break;
                }
            }
            log.info("Exit Produce");
        });
    }
}
