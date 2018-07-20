package com.trs.model;

/**
 * Created on 16/1/21.
 */

public class Greeting {

    private long status;
    private Object content;

    public Greeting(long status, Object content) {
        this.status = status;
        this.content = content;
    }

    public long getStatus() {
        return status;
    }

    public Object getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "status=" + status +
                ", content=" + content +
                '}';
    }
}
