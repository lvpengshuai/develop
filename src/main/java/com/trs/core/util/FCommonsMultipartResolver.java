package com.trs.core.util;

import com.trs.core.listener.FileUploadProgressListener;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by root on 17-4-1.
 */
public class FCommonsMultipartResolver extends CommonsMultipartResolver {
    private HttpServletRequest request;

    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        upload.setSizeMax(2000 * 1024 * 1024);
        if (request != null) {
            HttpSession session = request.getSession();
            FileUploadProgressListener fileUploadProgressListener = new FileUploadProgressListener(session);
            upload.setProgressListener(fileUploadProgressListener);
        }
        return upload;
    }

    public MultipartHttpServletRequest resolveMultipart(
            HttpServletRequest request) throws MultipartException {
        this.request = request;// 获取到request,要用到session
        return super.resolveMultipart(request);
    }


    @SuppressWarnings("unchecked")
    @Override
    public MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {

        HttpSession session = request.getSession();

        String encoding = "utf-8";
        FileUpload fileUpload = prepareFileUpload(encoding);

        FileUploadProgressListener uploadProgressListener = new FileUploadProgressListener(session);
        fileUpload.setProgressListener(uploadProgressListener);
        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        }
        catch (FileUploadBase.SizeLimitExceededException ex) {
            ex.printStackTrace();
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        }
        catch (FileUploadException ex) {
            ex.printStackTrace();
            throw new MultipartException("Could not parse multipart servlet request", ex);
        }
    }
}
