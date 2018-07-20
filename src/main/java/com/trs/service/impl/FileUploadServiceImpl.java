package com.trs.service.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.evan.common2.utils.CommonUtils;
import com.trs.core.util.Config;
import com.trs.core.util.EncryptUtil;
import com.trs.core.util.FileAccess;
import com.trs.core.util.Util;
import com.trs.model.Book;
import com.trs.model.Book;
import com.trs.service.FileUploadService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;


/**
 * 文件上传服务
 * <p>
 * 上传文件包括：meb/zip,图标,封面,截图
 * </p>
 *
 * @author 程志伟
 * @version 1.0 2013-05-20
 */
@Component
public class FileUploadServiceImpl implements FileUploadService {


	private static int GB = 1024*1024*1024;
	private static int MB = 1024*1024;
	private static int KB = 1024;
	public static String convertSize(Long fileSize) {
		if(fileSize >= GB) {
			return (fileSize / GB + "GB");
		} else if (fileSize >= MB) {
			return (fileSize / MB + "MB");
		} else if (fileSize >= KB) {
			return (fileSize / KB + "KB");
		} else {
			return fileSize + "B";
		}
	}

	/**
	 * 绝对路径
	 *
	 * @param book
	 * @param platform
	 * @return
	 */
	private String getMebFileURI(Book book, Short platform) {
		StringBuilder sb = new StringBuilder();
		sb.append(getBookRoot());
//		sb.append(book.getTitle());
//		sb.append("/");
//		sb.append("epub");
//		sb.append("/");
		sb.append(book.getBookcode());
		sb.append("/");
		sb.append(platform);
		sb.append("/");
		sb.append(book.getEpub());
		sb.append("/");
		sb.append(Config.getKey("book.mebname"));
		sb.append(".meb");

		String uri = sb.toString();
		//log.debug(uri);
		return uri;
	}
	/**
	 * 判断meb文件是否存在
	 *
	 * @param book
	 * @return
	 */
	public boolean isMebExists(Book book, Short platform) {
		return new File(getMebFileURI(book, platform)).exists();
	}


	public static String getBookRoot() {
		String root =Config.getKey("book.root");
		if (root.lastIndexOf("/") != root.length() - 1) {
			root += "/";
		}
		return root;
	}
	public String getFilePathForZip(Book book, Short platform) {
		StringBuilder sb = new StringBuilder();
		sb.append(getBookRoot());
//		sb.append(book.getTitle());
//		sb.append("/");
//		sb.append("epub");
//		sb.append("/");
		sb.append(book.getBookcode());
		sb.append("/");
		sb.append(platform);
		sb.append("/");
		sb.append(book.getEpub());
		sb.append("/temp/book/");

		String uri = sb.toString();
		//log.debug(uri);
		return uri;
	}

	public void saveFile(File file, byte [] bytes) throws IOException {
		FileAccess f = new FileAccess(file.getAbsolutePath(), 0);
		f.write(bytes, 0, bytes.length);
		try {
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createFile(File file) throws IOException {
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
	}

	public long uploadFileForZip(Book book, Short platform, /*byte[] bytes*/MultipartFile file, String fileType, String filename) throws IOException {
		String bootPath = getFilePathForZip(book, platform);
		File bootFile = new File(bootPath);
		// 如果目录不存在，创建
		if (!bootFile.exists()) {
			bootFile.mkdirs();
		}
		// 生成book文件
		File bookFile = new File(bootPath + "book." + fileType);
		//createFile(bookFile);
		// 保存文件
		saveFile(bookFile, file.getBytes());

		// 生成book.dat
		File bookDat = new File(bootPath + "book.dat");
		createFile(bookDat);
		saveFile(bookDat, ("<Book><Name>" + filename + "</Name><Size>" + bookFile.length() + "</Size><Chapters></Chapters></Book>").getBytes("utf-8"));
		// 生成book.xml
		File bookXml = new File(bootPath + "book.xml");
		createFile(bookXml);
		saveFile(bookXml, ("<Book><BookInfo><Name>" + filename + "</Name><BookType>" + fileType.toUpperCase() + "</BookType></BookInfo></Book>").getBytes("utf-8"));
		// 生成hash.dat
		File hashDat = new File(bootPath + "hash.dat");
		createFile(hashDat);
		saveFile(hashDat, CommonUtils.getUUID().getBytes());

		// 加密
//		if ("epub".equals(fileType)) {
//			EncryptUtil.encryptAppBook(bootPath);
//		}
		// 打包
		EncryptUtil.zip(bootPath.substring(0, bootPath.lastIndexOf("book")),getZipFileURI(book, platform));

		return getZipSize(book, platform);


	}
	/**
	 * 得到相对路径
	 * @param book
	 * @param platform
	 * @return
	 */
	public String getFilePathForZip2(Book book, Short platform) {
		StringBuilder sb = new StringBuilder();
//		sb.append(book.getTitle());
//		sb.append("/");
//		sb.append("epub");
//		sb.append("/");
		sb.append(book.getBookcode());
		sb.append("/");
		sb.append(platform);
		sb.append("/");
		sb.append(book.getEpub());
		sb.append("/");
		sb.append("/temp/book/");
		String uri = sb.toString();
		//log.debug(uri);
		return uri;
	}


	/**
	 * 获取zip文件的当前大小
	 *
	 * @param book
	 * @return
	 */
	public static long getZipSize(Book book, Short platform) {
		return new File(getZipFileURI(book, platform)).length();
	}
	public static String getZipFileURI(Book book, Short platform) {
		StringBuilder sb = new StringBuilder();
		sb.append(getBookRoot());
//		sb.append(book.getTitle());
//		sb.append("/");
		sb.append(book.getBookcode());
		sb.append("/");
		sb.append(platform);
		sb.append("/");
		sb.append(book.getEpub());
		sb.append("/");
		sb.append(Config.getKey("book.zipname"));
		sb.append(".zip");

		String uri = sb.toString();
		//log.debug(uri);
		return uri;
	}
	public String getEpubCompleteURL(Book book, Short platform){
		StringBuilder sb = new StringBuilder();
		sb.append(getBookRoot());
//		sb.append(book.getTitle());
//		sb.append("/");
//		sb.append("epub");
//		sb.append("/");
		sb.append(book.getBookcode());
		sb.append("/");
		sb.append(platform);
		sb.append("/");
		sb.append(book.getEpub());
		sb.append("/");
		sb.append("complete");
		sb.append("/");
		return sb.toString();
	}

	/**
	 * 资源文件 保存目录
	 *
	 * @param book
	 * @param platform
	 * @return
	 */
	public static String getFileFolderPath(Book book, Short platform) {
		StringBuilder sb = new StringBuilder();
		sb.append(getBookRoot());
//		sb.append(book.getTitle());
//		sb.append("/");
//		sb.append("epub");
//		sb.append("/");
		sb.append(book.getBookcode());
		sb.append("/");
		sb.append(platform);
		sb.append("/");
		sb.append(book.getEpub());
		sb.append("/");
		return sb.toString();
	}

	/**
	 * 文件夹复制方法
	 */
	public void  copyDir(String oldPath, String newPath)  {
		File file = new File(oldPath);
		//文件名称列表
		String[] filePath = file.list();

		if (!(new File(newPath)).exists()) {
			(new File(newPath)).mkdir();
		}

		for (int i = 0; i < filePath.length; i++) {
			if ((new File(oldPath + file.separator + filePath[i])).isDirectory()) {
				copyDir(oldPath  + file.separator  + filePath[i], newPath  + file.separator + filePath[i]);
			}

			if (new File(oldPath  + file.separator + filePath[i]).isFile()) {
				try {
					copyFile(oldPath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 复制文件夹方法
	 * @param oldPath
	 * @param newPath
	 * @throws IOException
	 */
	public static void copyFile(String oldPath, String newPath) throws IOException {
		File oldFile = new File(oldPath);
		File file = new File(newPath);
		FileInputStream in = new FileInputStream(oldFile);
		FileOutputStream out = new FileOutputStream(file);;

		byte[] buffer=new byte[2097152];

		while((in.read(buffer)) != -1){
			out.write(buffer);
		}


	}
	/**
	 * 获取文件夹下所有的文件夹名称
	 * @param oldPath
	 * @throws IOException
	 */
	public List FileName(String oldPath) {
		List  list=new ArrayList();
		File file = new File(oldPath);
		File[] tempList = file.listFiles();
		System.out.println("该目录下对象个数："+tempList.length);
		for (int i = 0; i < tempList.length; i++) {
//			if (tempList[i].isFile()) {
//				System.out.println("文     件：" + tempList[i]);
//			}
//			Util.log("测试全部日志1", "年鉴解析全部日志" , 0);
			if (tempList[i].isDirectory()) {
				System.out.println("文件夹：" + tempList[i]);
				list.add(tempList[i]);
			}
		}
			return list;
		}
	/**
	 * 获取文件夹下所有的文件名称
	 * @param oldPath
	 * @throws IOException
	 */
	public List FilefoloderName(String oldPath) {
		List  list=new ArrayList();
		File file = new File(oldPath);
		File[] tempList = file.listFiles();
		System.out.println("该目录下对象个数："+tempList.length);
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile()) {
				System.out.println("文     件：" + tempList[i]);
				list.add(tempList[i]);
			}
//			Util.log("测试全部日志1", "年鉴解析全部日志" , 0);
//			if (tempList[i].isDirectory()) {
//				System.out.println("文件夹：" + tempList[i]);
//				list.add(tempList[i]);
//			}
		}
		return list;
	}

	/**
	 * 删除日志
	 * @param folderPath
	 */
	public  void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			//不想删除文佳夹隐藏下面
//          String filePath = folderPath;
//          filePath = filePath.toString();
//          java.io.File myFilePath = new java.io.File(filePath);
//          myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹空
	 * @param folderPath
	 */
	public  void delFolderEmpty(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			//不想删除文佳夹隐藏下面
          String filePath = folderPath;
          filePath = filePath.toString();
          java.io.File myFilePath = new java.io.File(filePath);
          myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				delFolderEmpty(path + "/" + tempList[i]);//再删除空文件夹
				flag = true;
			}
		}
		return flag;

}
	public  boolean deleteDir(String path){
		File file = new File(path);
		if(!file.exists()){//判断是否待删除目录是否存在
			System.err.println("The dir are not exists!");
			return false;
		}

		String[] content = file.list();//取得当前目录下所有文件和文件夹
		for(String name : content){
			File temp = new File(path, name);
			if(temp.isDirectory()){//判断是否是目录
				deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容
				temp.delete();//删除空目录
			}else{
				if(!temp.delete()){//直接删除文件
					System.err.println("Failed to delete " + name);
				}
			}
		}
		return true;
	}


	public List<String> readLog(String filePath) {
		List<String> list = new ArrayList<String>();

		try {
			FileInputStream is = new FileInputStream(filePath);
			InputStreamReader isr=null;
			try{
				isr = new InputStreamReader(new FileInputStream(filePath),"UTF-8");
			}catch(IOException e){
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(isr);
			String line;
			try {
				while ((line = br.readLine()) != null) {
					if (line.equals(""))
						continue;
					else
						list.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("读取一行数据时出错");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("文件读取路径错误FileNotFoundException");
		}
		return list;
	}


	public  void delFolderAll(File file) {

		if (file.isFile()) {// 表示该文件不是文件夹
			file.delete();
		} else {
			// 首先得到当前的路径
			String[] childFilePaths = file.list();
			for (String childFilePath : childFilePaths) {
				File childFile = new File(file.getAbsolutePath() + "/" + childFilePath);
				delFolderAll(childFile);
			}
			file.delete();
		}
	}

	public Integer  readPdfjiami(String  pdfpath,String newPdfPath){

		File file=new File(pdfpath);
		File filenew=new File(pdfpath);
		byte[] data=null;
		try{
			FileInputStream input=new FileInputStream(file);
			FileOutputStream ouput=new FileOutputStream(pdfpath.replace(".pdf",".txt"));
			data = new byte[input.available()];
			input.read(data);
			ouput.write(data);
			input.close();
			//判断是否生成txt文件
			File filecheck = new File(pdfpath.replace(".pdf",".txt"));
			if(!filecheck .exists()){
				filecheck.createNewFile();
				System.out.println("文件不存在");
				return 0;
			}else{
				Boolean succeedDelete = filenew.delete();
				if(succeedDelete){
					System.out.println("删除pdf文件"+pdfpath+"成功！");
				}
				else{
					System.out.println("删除pdf文件"+pdfpath+"失败！");
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return 1;
	}
}
