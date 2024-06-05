package com.davin.framework.queue.config;

import java.util.concurrent.TimeUnit;

/**
 * Queue config
 * @author davin.bao
 * @date 2024/6/5
 */
public class QueueConfig {
    /** Capacity */
    private int capacity;
    /** Poll data timeout */
    private long pollTimeout;
    /** Poll data timeout unit */
    private TimeUnit pollTimeoutUnit;
    /** Min consumer count */
    private long minConsumerCount;
    /** Max consumer count */
    private long maxConsumerCount;
    /** Remaining upper percent, remaining capacity >= capacity * remainingUpperPercent, need add consumer */
    private float remainingUpperPercent;
    /** Remaining lower percent, remaining capacity <= capacity * remainingLowerPercent, need remove consumer */
    private float remainingLowerPercent;

    public int getCapacity() {
        return capacity;
    }

    public long getPollTimeout() {
        return pollTimeout;
    }

    public TimeUnit getPollTimeoutUnit() {
        return pollTimeoutUnit;
    }

    public long getMinConsumerCount() {
        return minConsumerCount;
    }

    public long getMaxConsumerCount() {
        return maxConsumerCount;
    }

    public float getRemainingUpperPercent() {
        return remainingUpperPercent;
    }

    public float getRemainingLowerPercent() {
        return remainingLowerPercent;
    }

    private QueueConfig(Builder builder) {
        this.capacity = builder.capacity;
        this.pollTimeout = builder.pollTimeout;
        this.pollTimeoutUnit = builder.pollTimeoutUnit;
        this.minConsumerCount = builder.minConsumerCount;
        this.maxConsumerCount = builder.maxConsumerCount;
        this.remainingUpperPercent = builder.remainingUpperPercent;
        this.remainingLowerPercent = builder.remainingLowerPercent;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        public Builder() {
        }

        private int capacity;
        private long pollTimeout;
        private TimeUnit pollTimeoutUnit;
        private long minConsumerCount;
        private long maxConsumerCount;
        private float remainingUpperPercent;
        private float remainingLowerPercent;

        public Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder pollTimeout(long pollTimeout) {
            this.pollTimeout = pollTimeout;
            return this;
        }

        public Builder pollTimeoutUnit(TimeUnit pollTimeoutUnit) {
            this.pollTimeoutUnit = pollTimeoutUnit;
            return this;
        }

        public Builder minConsumerCount(long minConsumerCount) {
            this.minConsumerCount = minConsumerCount;
            return this;
        }

        public Builder maxConsumerCount(long maxConsumerCount) {
            this.maxConsumerCount = maxConsumerCount;
            return this;
        }

        public Builder remainingUpperPercent(float remainingUpperPercent) {
            this.remainingUpperPercent = remainingUpperPercent;
            return this;
        }

        public Builder remainingLowerPercent(float remainingLowerPercent) {
            this.remainingLowerPercent = remainingLowerPercent;
            return this;
        }

        public QueueConfig build() {
            return new QueueConfig(this);
        }
    }
}
