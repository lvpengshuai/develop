package com.trs.model;

/**
 * Created by 李春雨 on 2017/3/21.
 */
public class Progress {

    private long bytesRead;
    private long contentLength;
    private long items;
    public long getBytesRead() {
        return bytesRead;
    }
    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }
    public long getContentLength() {
        return contentLength;
    }
    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }
    public long getItems() {
        return items;
    }
    public void setItems(long items) {
        this.items = items;
    }
    @Override
    public String toString() {
        return "Progress [bytesRead=" + bytesRead + ", contentLength=" + contentLength + ", items=" + items + "]";
    }
}
