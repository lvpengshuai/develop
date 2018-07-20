package com.trs.core.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcy on 2017/5/3.
 */
public class UploadUtil {
    /**
     * 解压并读取文件
     *
     * @param file
     * @return
     */
    public static Status readFile(MultipartFile file) throws Exception {

        // 定义文件数组用于保存数据
        File[] tempList = null;

        // 获取上传文件的文件名
        String fileName = file.getOriginalFilename();

        // 保存上传文件并返回路径
        String filePath = ExcelUtils.savefile(fileName, file);

        // 拼接解压路径
        String upPath = System.getProperty("java.io.tmpdir") + FileUtil.getPathWithSystem("/") + "unzip" + FileUtil.getPathWithSystem("/") + createRelativePath("upload");

        // 设置解压文件路径
        Status status = ZipUtil.unZip(upPath, filePath, "GBK");
        // 读取文件目录
        File newfile = new File(upPath);
        tempList = newfile.listFiles();
        status.setFiles(tempList);
        return status;
    }

    /**
     * 判断是文件还是目录
     *
     * @param tempList
     * @return
     */
    public static Map isFile(File[] tempList) throws Exception {

        // 定义变量，用于存放文件的路径
        String strName = "";

        // 定义map返回存储返回值
        Map<String, Object> map = new HashMap<String, Object>();

        // 定义excelList用于保存的读取excel数据
        List<Map> excelList = null;

        // 循环遍历文件数据tempList，获取文件数组中的数据
        for (int i = 0; i < tempList.length; i++) {

            if (tempList[i].isFile()) {
                excelList = readExcel(tempList[i]);
            } else if (tempList[i].isDirectory()) {
                strName = tempList[i].toString();
            }
        }
        map.put("excelList", excelList);
        map.put("strName", strName);
        return map;
    }

    /**
     * 读取excelw文件并返回数据
     *
     * @param file
     * @return
     */
    public static List<Map> readExcel(File file) {

        // 定义excelList，用于存放从excel文件中获取的数据（map类型）
        List<Map> excelList = null;

        try {
            excelList = ExcelUtils.readExcel(file.getName(), file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return excelList;
    }

    /**
     * 将文件进行重命名
     *
     * @param toBeRenamed
     * @param toFile
     */
    public static void renameFile(File toBeRenamed, String toFile) {

        //检查要重命名的文件是否存在，是否是文件
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            return;
        }
        File newFile = new File(toFile);

        //修改文件名
        if (toBeRenamed.renameTo(newFile)) {
        } else {
        }

    }

    /**
     * 判断文件夹是否存在
     *
     * @param file
     */
    public static void dirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }

    }

    /**
     * 创建相对文件路径
     *
     * @return
     */
    public static String createRelativePath(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "/" + name + "/" + sdf.format(new Date()) + "/";
    }
}
