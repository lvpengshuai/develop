package com.trs.core.util;

import java.io.*;

/**
 *
 */
public class PdfUtilTest {

    /**
     * 调用pdf2htmlEX将pdf文件转换为html文件
     *
     * @param command  调用exe的字符串
     * @param pdfName  需要转换的pdf文件名称
     * @param htmlName 生成的html文件名称
     * @return
     */

    //private static String COMMAND_PATH = Config.getKey("E:\\社科年鉴\\pdf2html\\pdf2html\\pdf2htmlEX-win32-0.14.6-upx-with-poppler-data\\pdf2htmlEX-win32-0.14.6-upx-with-poppler-data\\pdf2htmlEX.exe");

    private static String COMMAND_PATH = "D:\\epro\\cssp\\swf\\pdf2htmlEX\\pdf2htmlEX.exe";

    public static class StreamGobbler extends Thread {
        InputStream is;
        String type;
        OutputStream os;

        public StreamGobbler(InputStream is, String type) {
            this(is, type, null);
        }

        StreamGobbler(InputStream is, String type, OutputStream redirect) {
            this.is = is;
            this.type = type;
            this.os = redirect;
        }

        public void run() {
            InputStreamReader isr = null;
            BufferedReader br = null;
            PrintWriter pw = null;
            try {
                if (os != null)
                    pw = new PrintWriter(os);
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (pw != null)
                        pw.println(line);
                    System.out.println(type + ">" + line);
                }
                if (pw != null)
                    pw.flush();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                try {
                    if (pw != null)
                        pw.close();
                    if (br != null)
                        br.close();
                    if (isr != null)
                        isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static boolean pdf2html(String pdfFile, String destDir, String htmlFileName) {
        if (!(pdfFile != null && !"".equals(pdfFile)
                && htmlFileName != null && !"".equals(htmlFileName))) {
            System.out.println("传递的参数有误！");
            return false;
        }
        Runtime rt = Runtime.getRuntime();
        StringBuilder command = new StringBuilder();
        //pdf2html.exe
        command.append(COMMAND_PATH).append(" ");
        if (destDir != null && !"".equals(destDir.trim()))//生成文件存放位置,需要替换文件路径中的空格
            command.append("--dest-dir ").append(destDir.replace(" ", "\" \"")).append(" ");
        command.append("--optimize-text 1 ");//尽量减少用于文本的HTML元素的数目 (default: 0)
        // command.append("--embed o ");//去除目录
        command.append("--zoom 1.5 ");//放大
        command.append("--embed-font 1 ");//是否字体文件嵌入
        command.append("--hdpi 144 ");//图像水平分辨率
        command.append("--vdpi 144 ");//图像垂直分辨率
        //   command.append("--tounicode 1 ");
        command.append("--embed-javascript 0 ");//将javascript文件嵌入到输出中
//        command.append("--embed-css ");//将CSS文件嵌入到输出中
//        command.append("-f " + start + " ");//开始
//        command.append("-l " + end + " ");//结束

        command.append(pdfFile.replace(" ", "\" \"")).append(" ");//需要替换文件路径中的空格
        if (htmlFileName != null && !"".equals(htmlFileName.trim())) {
            command.append(htmlFileName);
            if (htmlFileName.indexOf(".html") == -1)
                command.append(".html");
        }
        try {
            Process p = rt.exec(command.toString());
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
            //开启屏幕标准错误流
            errorGobbler.start();
            StreamGobbler outGobbler = new StreamGobbler(p.getInputStream(), "STDOUT");
            //开启屏幕标准输出流
            outGobbler.start();
            int w = p.waitFor();
            int v = p.exitValue();
            if (w == 0 && v == 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] ags) {
        try {
            long starTime = System.currentTimeMillis();
            pdf2html("D:\\1.pdf", "D:\\html", "3.html");
            long endTime = System.currentTimeMillis();
            System.out.println((endTime - starTime) / 1000 + "s");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
