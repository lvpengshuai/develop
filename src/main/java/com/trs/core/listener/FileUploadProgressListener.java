package com.trs.core.listener;

import com.trs.core.util.ProgressEntity;
import org.apache.commons.fileupload.ProgressListener;

import javax.servlet.http.HttpSession;

/**
 * Created by zhangpeng on 2017/4/1.
 */
public class FileUploadProgressListener implements ProgressListener {
    private HttpSession session;

    public FileUploadProgressListener() {
    }

    public FileUploadProgressListener(HttpSession session) {
        this.session = session;
        ProgressEntity ps = new ProgressEntity();
        session.setAttribute("upload_ps", ps);
    }

    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        ProgressEntity ps = (ProgressEntity) session.getAttribute("upload_ps");
        ps.setpBytesRead(pBytesRead);
        ps.setpContentLength(pContentLength);
        ps.setpItems(pItems);
        session.setAttribute("upload_ps", ps);
    }
}
