package com.davin.service;

/**
 * @author davin.bao
 * @date 2024/6/4
 */
public interface RedisQueueService {
    /**
     * 初始化
     */
    void init();
    /**
     * 生产消息
     */
    void produce();
}
