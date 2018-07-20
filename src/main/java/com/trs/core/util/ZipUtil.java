package com.trs.core.util;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import java.io.File;

/**
 * Created 李春雨 on 16/7/27.
 */
public class ZipUtil {


    public static void main(String[] args) {

        ZipUtil.zip("D:\\epro\\shiyou\\data\\test\\words", "D:\\epro\\shiyou\\data\\test\\words\\word.zip");

        //unZip("D:\\epro\\shiyou\\data\\test\\words", "D:\\epro\\shiyou\\data\\words\\word.zip");
    }

    /**
     * 解压缩
     *
     * @param destDir   生成的目标目录下   c:/a
     * @param sourceZip 源zip文件      c:/upload.zip
     *                  结果则是 将upload.zip文件解压缩到c:/a目录下
     */
    public static Status unZip(String destDir, String sourceZip, String encoding) throws Exception {
        try {

            Project prj1 = new Project();

            Expand expand = new Expand();
            expand.setEncoding(encoding);
            expand.setProject(prj1);

            expand.setSrc(new File(sourceZip));

            expand.setOverwrite(false);//是否覆盖

            File file = new File(destDir);

            if (!file.exists()) file.mkdir();
            expand.setDest(file);
            expand.execute();
            FileUtil.forceDeleteDirectory(new File(sourceZip));
        } catch (Exception e) {
            return Status.fileError;
        }
        return Status.SUCCESS;
    }


    /**
     * 压缩
     *
     * @param sourceFile 压缩的源文件 如: c:/upload
     * @param targetZip  生成的目标文件 如：c:/upload.zip
     */
    public static void zip(String sourceFile, String targetZip) {

        Project prj = new Project();

        Zip zip = new Zip();

        zip.setProject(prj);

        zip.setDestFile(new File(targetZip));//设置生成的目标zip文件File对象

        FileSet fileSet = new FileSet();

        fileSet.setProject(prj);

        fileSet.setDir(new File(sourceFile));//设置将要进行压缩的源文件File对象

        //fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹,只压缩目录中的所有java文件

        //fileSet.setExcludes("**/*.java"); //排除哪些文件或文件夹,压缩所有的文件，排除java文件

        zip.addFileset(fileSet);

        zip.execute();

    }
}
