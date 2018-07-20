package com.trs.core.util;

import java.io.*;
import java.sql.Timestamp;


/**
 * 封装了文件操作（增加、删除、移动、复制）相关的一些方法。
 */
public final class FileUtil {
    /**
     * 十六进制数字符数组，能根据0-15的十进制数都到相应的十六进制数字符，如<code>HEX_DIGITS[10]=A</code>，根据文件ID生成相对
     * 路径时用到该数组。
     */
    private final static char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 删除所有文件
     *
     * @param file
     */
    public static void deleteAll(File file) {
        if (file.isFile() || file.list().length == 0) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteAll(files[i]);
                files[i].delete();
            }
            if (file.exists())         //如果文件本身就是目录 ，就要删除目录
                file.delete();
        }
    }

    /**
     * 根据文件ID计算文件的相对存储目录名，目前的实现支持到32位的 ID，即支持到4294967296个文件。
     *
     * @param fileId 文件ID
     * @return 文件的相对存储目录名
     */
    public static String id2path(int fileId) {
        char[] buff = new char[9];

        buff[0] = '/';
        buff[1] = HEX_DIGITS[(fileId >>> 28) & 0xF];
        buff[2] = HEX_DIGITS[(fileId >>> 24) & 0xF];
        buff[3] = '/';
        buff[4] = HEX_DIGITS[(fileId >>> 20) & 0xF];
        buff[5] = HEX_DIGITS[(fileId >>> 16) & 0xF];
        buff[6] = '/';
        buff[7] = HEX_DIGITS[(fileId >>> 12) & 0xF];
        buff[8] = HEX_DIGITS[(fileId >>> 8) & 0xF];

        return new String(buff, 0, 9);
    }

    /**
     * 根据文件ID计算文件名，不包含文件扩展名(带"."号)。
     *
     * @param fileId 文件ID
     * @return 文件名（不包含扩展名）
     */
    public static String id2file(int fileId) {
        char[] buff = new char[2];

        buff[0] = HEX_DIGITS[(fileId >>> 4) & 0xF];
        buff[1] = HEX_DIGITS[fileId & 0xF];

        return new String(buff, 0, 2);
    }

    /**
     * 根据文件ID，根目录和文件扩展名构造包含绝对路径的文件名。
     *
     * @param fileId   fileId 对应文件的ID
     * @param fileExt  文件的扩展名需带"."号
     * @param basePath 文件存放的根目录
     * @return 包含绝对路径的文件名。
     */
    public static String getFileName(int fileId, String fileExt, String basePath) {
        StringBuffer buff = new StringBuffer(128);
        buff.append(basePath);
        buff.append(id2path(fileId));
        buff.append('/');
        buff.append(id2file(fileId));
        buff.append(fileExt);
        return buff.toString();
    }

    /**
     * 根据文件ID，根目录和文件扩展名构造文件对象。
     *
     * @param fileId   对应文件的ID
     * @param fileExt  文件的扩展名需带"."号
     * @param basePath 文件存放的根目录
     * @return 文件对象
     */
    public static File getFile(int fileId, String fileExt, String basePath) {
        return new File(getFileName(fileId, fileExt, basePath));
    }

    /**
     * 根据文件ID，目录和文件扩展名构造文件对象。
     *
     * @param fileId  对应文件的ID
     * @param fileExt 文件的扩展名需带"."号
     * @param path    目录对象
     * @return 文件对象
     */
    public static File getFile(int fileId, String fileExt, File path) {
        return new File(path, id2file(fileId) + fileExt);
    }

    /**
     * @param fileId   文件ID
     * @param basePath 根目录
     * @return 返回路径文件对象
     */
    public static File getPath(int fileId, String basePath) {
        return new File(basePath + id2path(fileId));
    }

    /**
     * 将源文件复制到目标文件。
     *
     * @param sourceName 源文件名
     * @param targetName 目标文件名
     */
    public static void copy(String sourceName, String targetName)
            throws IOException {
        copy(new File(sourceName), new File(targetName));
    }

    /**
     * 将源文件复制到目标文件。
     *
     * @param sourceFile 源文件对象
     * @param targetFile 目标文件对象
     */
    public static boolean copy(File sourceFile, File targetFile)
            throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
        byte buff[] = new byte[4096];
        int length;

        while ((length = bis.read(buff)) != -1) {
            bos.write(buff, 0, length);
        }

        bos.close();
        bis.close();
        return true;
    }

    /**
     * 将源文件复制到目标文件。文件的相关路径由ID计算产生，文件名（扩展名部分除外）也由ID计算产生。
     *
     * @param fileId     文件对应的ID
     * @param fileExt    文件扩展名，带"."号
     * @param sourceBase 源文件所在的根目录
     * @param targetBase 目标文件所在文件所在的根目录
     * @return 如果复制成功返回<code>true</code>，否则返回<code>false</code>。
     */
    public static void copy(int fileId, String fileExt, String sourceBase, String targetBase)
            throws IOException {
        File sourceFile = getFile(fileId, fileExt, sourceBase);
        File targetFile = getFile(fileId, fileExt, targetBase);
        copy(sourceFile, targetFile);
    }

    /**
     * 将源文件移动到目标文件。文件的相关路径由ID计算产生，文件名（扩展名部分除外）也由ID计算产生。
     *
     * @param fileId     文件对应的ID
     * @param fileExt    文件扩展名，带"."号
     * @param sourceBase 源文件所在的根目录
     * @param targetBase 目标文件所在文件所在的根目录
     */
    public static void move(int fileId, String fileExt, String sourceBase, String targetBase) {
        File sourceFile = getFile(fileId, fileExt, sourceBase);
        File targetFile = getFile(fileId, fileExt, targetBase);

        if (sourceFile.exists()) {
            sourceFile.renameTo(targetFile);
        }
    }

    /**
     * 把指定的输入流写入目标文件。
     *
     * @param inputStream 文件输入流
     * @param targetFile  目标文件
     * @throws IOException
     */
    public static void writeFile(InputStream inputStream, File targetFile) throws IOException {
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(targetFile);
            if (fos != null) {
                byte buff[] = new byte[4096];
                int length;
                while ((length = inputStream.read(buff, 0, 4096)) > 0) {
                    fos.write(buff, 0, length);
                }
            }
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    /**
     * 保存文件，文件的相关路径由ID计算产生，文件名（扩展名部分除外）也由ID计算产生。
     *
     * @param fileId      文件ID
     * @param fileExt     文件扩展名
     * @param basePath    文件存放的根目录（相对目录由计算产生）
     * @param inputStream 文件输入流
     * @return 返回实际保存到的文件
     * @throws IOException
     */
    public static File save(int fileId, String fileExt, String basePath, InputStream inputStream)
            throws IOException {
        File path = getPath(fileId, basePath);
        if (!path.exists()) {
            path.mkdirs();
        }

        File targetFile = getFile(fileId, fileExt, path);
        writeFile(inputStream, targetFile);

        return targetFile;
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void delete(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除文件，文件的相关路径由ID计算产生，文件名（扩展名部分除外）也由ID计算产生。
     *
     * @param fileId   文件ID
     * @param fileExt  文件扩展名
     * @param basePath 文件存放的根目录（相对目录由计算产生）
     */
    public static void delete(int fileId, String fileExt, String basePath) {
        File file = getFile(fileId, fileExt, basePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 提取文件扩展名。
     *
     * @param fileName 文件名
     * @return 文件扩展名（带"."），如果没有扩展名则返回<code>null</code>
     */
    public static String getExtend(String fileName) {
        if (fileName != null) {
            int index = fileName.lastIndexOf('.');
            if (index != -1) {
                return fileName.substring(index);
            }
        }

        return null;
    }

    public static String getPartName(String fileName) {
        if (fileName != null) {
            int index = fileName.lastIndexOf('.');
            if (index != -1) {
                return fileName.substring(0, index);
            }
        }

        return null;
    }

    /**
     * 强制删除一个目录，包含所有子目录。<br>
     * 如果目录不存在，直接返回。
     */
    public static void forceDeleteDirectory(File directory) throws IOException {
        if (!directory.exists()) return;

        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                } else {
                    forceDeleteDirectory(files[i]);
                }
            }
        }
        //最后删除当前的目录。
        directory.delete();
    }

    // ls@06-0407
    private static final String ENTER = System.getProperty("line.separator");

    /**
     * 获取文件大小信息和内容(不超过1 MB的). 主要用于查看配置文件等.
     *
     * @return 文件大小信息和内容
     */
    public static String getFileContent(String fileName) {
        if (fileName == null || fileName.trim().length() == 0) {
            return "fileName cannot be empty!";
        }
        File f = new File(fileName);
        if (false == f.isFile()) {
            return fileName + " is not exist or is not a file!";
        }
        final int maxLength = 1024000;
        long len = f.length();
        if (len > maxLength) {
            return "Deny: size larger than " + maxLength + " Byte! file=" + fileName + ", size=" + len;
        }
        StringBuffer sb = new StringBuffer(maxLength);
        BufferedReader br = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(f));
            br = new BufferedReader(isr);
            sb.append("LAST_MODIFIED:").append(new Timestamp(f.lastModified())).append(ENTER);
            sb.append("CONTENT:").append(ENTER);
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
                sb.append(ENTER);
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            return sb.insert(0, e + ENTER + "Already read content:" + ENTER).toString();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 返回指定目录下文件名称
     *
     * @param directory
     * @return
     */
    public static String[] getFileDirectoryName(String directory) {
        if (!StringUtil.isEmpty(directory)) {
            File path = new File(directory);
            return path.list();
        }
        return null;
    }

    /**
     * 返回指定目录下文件
     *
     * @param directory
     * @return
     */
    public static File[] getFileDirectory(String directory) {
        if (!StringUtil.isEmpty(directory)) {
            File path = new File(directory);
            return path.listFiles();
        }
        return null;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean fileisExist(String filePath) {
        if (!StringUtil.isEmpty(filePath)) {
            File file = new File(filePath);
            return file.exists();
        }
        return false;
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        if (sourceFile.exists()) {
            // 新建文件输入流并对它进行缓冲
            FileInputStream input = new FileInputStream(sourceFile);
            BufferedInputStream inBuff = new BufferedInputStream(input);

            // 新建文件输出流并对它进行缓冲
            FileOutputStream output = new FileOutputStream(targetFile);
            BufferedOutputStream outBuff = new BufferedOutputStream(output);

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();

            // 关闭流
            inBuff.close();
            outBuff.close();
            output.close();
            input.close();
        }
    }

    // 复制文件夹
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir)
                        .getAbsolutePath()
                        + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    /**
     * 读取文本文件到StringBUffert
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }


    public static InputStream readFile(String filePath) throws FileNotFoundException {
        InputStream is = new FileInputStream(filePath);
        return is;
    }


    /**
     * 重命名文件夹
     *
     * @param fdirname
     * @param oldname
     * @param newname
     */
    public static void renameDir(String fdirname, String oldname, String newname) {
        File fl = new File(fdirname);
        if (fl.isDirectory()) {
            File f = new File(fl, oldname);
            if (f.isDirectory()) {
                f.renameTo(new File(fl.getAbsolutePath() + "/" + newname));
            }
        }
    }

    public static boolean copy(File file, String floderPath, String fileName) {
        try {
            InputStream is = new FileInputStream(file);
            File filePath = new File(floderPath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            File toFile = new File(floderPath, fileName);

            OutputStream os = new FileOutputStream(toFile);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            is.close();
            os.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getPathWithSystem(String path) {
        if (isWindowsOS()) {
            return path.replaceAll("/", "\\\\");
        } else {
            return path.replaceAll("\\\\", "/");
        }
    }


    public static boolean isWindowsOS() {
        boolean isWindowsOS = false;
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().indexOf("windows") > -1) {
            isWindowsOS = true;
        }
        return isWindowsOS;
    }

}
