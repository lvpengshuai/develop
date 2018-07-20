package com.trs.core.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;
import org.junit.Test;

import java.io.*;

/**
 * Created by root on 17-4-6.
 */
public class ITextTest {

    @Test
    public void test() {
        System.out.println(readPdfToTxt("D:\\Users\\lcy\\Desktop\\111.pdf").trim());
//        System.out.println(readPdfToTxt("/root/20090415.pdf"));
//        readPdfToTxt("/root/20090415.pdf", "/root/20090415.txt");
    }
    /**
     * 读取pdf内容
     * @param pdfPath
     */
    public static String readPdfToTxt(String pdfPath) {
        PdfReader reader = null;
        StringBuffer buff = new StringBuffer();
        try {
            reader = new PdfReader(pdfPath);
            System.out.println(reader.getInfo());
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            int num = reader.getNumberOfPages();// 获得页数
            TextExtractionStrategy strategy;
            for (int i = 1; i <= num; i++) {
                strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
                buff.append(strategy.getResultantText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buff.toString();
    }


    /**
     * 读取PDF文件内容到txt文件
     *
     * @param pdfPath
     * @param txtfilePath
     */
    private static void readPdfToTxt(String pdfPath,String txtfilePath) {
        // 读取pdf所使用的输出流
        PrintWriter writer = null;
        PdfReader reader = null;
        try {
            writer = new PrintWriter(new FileOutputStream(txtfilePath));
            reader = new PdfReader(pdfPath);
            int num = reader.getNumberOfPages();// 获得页数
            System.out.println("Total Page: " + num);
            String content = ""; // 存放读取出的文档内容
            for (int i = 1; i <= num; i++) {
                // 读取第i页的文档内容
                content += PdfTextExtractor.getTextFromPage(reader, i);
            }
            String[] arr = content.split("/n");
            for(int i=0;i<arr.length;i++){
                System.out.println(arr[i]);
            /*String[] childArr = arr[i].split(" ");
            for(int j=0;j<childArr.length;j++){
                System.out.println(childArr[j]);
            }*/
            }

            //System.out.println(content);

            writer.write(content);// 写入文件内容
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static final String SRC = "/root/2010.pdf";

    class FontRenderFilter extends RenderFilter {
        public boolean allowText(TextRenderInfo renderInfo) {
            String font = renderInfo.getFont().getPostscriptFontName();
            return font.endsWith("Bold") || font.endsWith("Oblique");
        }
    }

    @Test
    public void test2() throws IOException, DocumentException {
        parse(SRC);
    }

    public void parse(String filename) throws IOException {
        PdfReader reader = new PdfReader(filename);
        Rectangle rect = new Rectangle(36, 750, 559, 806);
        RenderFilter regionFilter = new RegionTextRenderFilter(rect);
        FontRenderFilter fontFilter = new FontRenderFilter();
        TextExtractionStrategy strategy = new FilteredTextRenderListener(
                new LocationTextExtractionStrategy(), regionFilter, fontFilter);
        System.out.println(PdfTextExtractor.getTextFromPage(reader, 1, strategy));
        reader.close();
    }


    @Test
    public void test3() throws IOException, DocumentException {
        String SRC = "/root/2010.pdf";
        String DEST = "/root/2010.txt";
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        parse2(SRC, DEST);
    }


    public void parse2(String filename, String dest) throws IOException {
        PdfReader reader = new PdfReader(filename);
        Document document = new Document();
        FileOutputStream fos = new FileOutputStream(dest);
        for (int page = 1; page <= 1; page++) {
            fos.write(PdfTextExtractor.getTextFromPage(reader, page).getBytes("UTF-8"));
        }
        fos.flush();
        fos.close();
    }


    @Test
    public void test6(){
        File file = new File("D:\\Users\\lcy\\Desktop\\tes\\20080103.pdf");
        File file1 = new File("D:\\Users\\lcy\\Desktop\\tes2\\test.pdf");
        boolean b = file.renameTo(file1);
        System.out.println(b);


    }

}
