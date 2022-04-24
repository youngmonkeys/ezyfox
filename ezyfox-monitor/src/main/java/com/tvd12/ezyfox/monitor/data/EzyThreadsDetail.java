package com.tvd12.ezyfox.monitor.data;

import lombok.Getter;

import java.util.List;

@Getter
public class EzyThreadsDetail {

    protected final long totalThreadsCpuTime;
    protected final List<EzyThreadDetail> threads;

    protected EzyThreadsDetail(Builder builder) {
        this.threads = builder.threads;
        this.totalThreadsCpuTime = builder.totalThreadsCpuTime;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        protected long totalThreadsCpuTime;
        protected List<EzyThreadDetail> threads;

        public Builder totalThreadsCpuTime(long totalThreadsCpuTime) {
            this.totalThreadsCpuTime = totalThreadsCpuTime;
            return this;
        }

        public Builder threads(List<EzyThreadDetail> threads) {
            this.threads = threads;
            return this;
        }

        public EzyThreadsDetail build() {
            return new EzyThreadsDetail(this);
        }
    }
}
