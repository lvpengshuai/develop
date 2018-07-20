package com.trs.httpuntil;

import org.apache.ibatis.type.Alias;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by mxp on 2017/9/26.
 */
public class DataBlack {
    /**
     * ZAS回调
     *
     * @throws java.io.IOException
     */
  //  @Priv(login = false)
    //@Alias("member/SSOBack")
    public String SSOBack(HttpServletRequest request) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader bufferedReader = null;
        String content = "";
        try {
            bufferedReader = request.getReader();
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                sb.append(charBuffer, 0, bytesRead);
            }

        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        content = sb.toString();
        System.out.println(content);
        return null;
    }

}
