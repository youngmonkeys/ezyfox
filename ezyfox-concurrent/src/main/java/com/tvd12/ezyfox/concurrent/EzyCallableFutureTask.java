package com.tvd12.ezyfox.concurrent;

import com.tvd12.ezyfox.concurrent.callback.EzyResultCallback;

@SuppressWarnings({"rawtypes", "unchecked"})
public class EzyCallableFutureTask extends EzyFutureTask {

    protected final EzyResultCallback callback;

    public EzyCallableFutureTask() {
        this(null);
    }

    public EzyCallableFutureTask(EzyResultCallback callback) {
        this.callback = callback;
    }

    @Override
    public void setResult(Object result) {
        if(result == null)
            throw new NullPointerException("result is null");
        if(callback != null) {
            synchronized (this) {
                if(this.result == null) {
                    this.result = result;
                    this.callback.onResponse(result);
                }
            }
        }
        else {
            synchronized (this) {
                if(this.result == null)
                    this.result = result;
                notify();
            }
        }
    }

    @Override
    public void setException(Exception exception) {
        if(exception == null)
            throw new NullPointerException("exception is null");
        if(callback != null) {
            synchronized (this) {
                if(this.exception == null) {
                    this.exception = exception;
                    this.callback.onException(exception);
                }
            }
        }
        else {
            synchronized (this) {
                if(this.exception == null)
                    this.exception = exception;
                notify();
            }
        }
    }
}