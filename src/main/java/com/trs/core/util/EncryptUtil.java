package com.trs.core.util;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EncryptUtil {

	static final int BUFFER = 2048;
	static final String bookRoot = "book";
	static final String epubKey = "ffa17e84f481201844724a4e1c1b981e";


	public  static void unzip(String fileName, String filePath) throws IOException {
		ZipFile zipFile = new ZipFile(fileName);
		Enumeration<?> emu = zipFile.entries();
		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			String entryname=entry.getName().replace("META-INF","METAINF");
			// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
			if (entry.isDirectory()) {
				new File(filePath + entry.getName().replace("META-INF","METAINF")).mkdirs();
				continue;
			}
			BufferedInputStream bis = new BufferedInputStream(
					zipFile.getInputStream(entry));
			File file = new File(filePath + entry.getName().replace("META-INF","METAINF"));
			// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
			// 而这个文件所在的目录还没有出现过，所以要建出目录来。
			File parent = file.getParentFile();
			if (parent != null && (!parent.exists())) {
				parent.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);

			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				bos.write(data, 0, count);
			}
			bos.flush();
			bos.close();
			bis.close();
		}
		zipFile.close();
	}

	public static void zip(String filePath, String file)
			throws FileNotFoundException, IOException {
		FileOutputStream dest = new FileOutputStream(file);
		ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(dest));
		recursiveZip(new File(filePath), "", out, false);
		out.close();
	}

	// 递归压缩函数
	// fDir　要压缩的目录或者文件
	// pName　父压缩记录名称，第一次调用应该被设置为一个空字符串""
	// zos　压缩输出流
	private static void recursiveZip(File fDir, String pName, ZipOutputStream zos,
									 boolean includeRootDir) throws FileNotFoundException, IOException {
		if (fDir.isDirectory()) {
			// 如果为目录，ZipEntry名称的尾部应该以反斜杠"/"结尾
			if (includeRootDir) {
				zos.putNextEntry(new ZipEntry(pName + fDir.getName() + "/"));
			}
			File[] files = fDir.listFiles();
			if (files != null) {
				String subDirName = "";
				for (int i = 0; i < files.length; i++) {
					// 进行递归，同时传递父文件ZipEntry的名称，还有压缩输出流
					if (includeRootDir) {
						subDirName = pName + fDir.getName() + "/";
					}
					recursiveZip(files[i], subDirName, zos, true);
				}
			}
		}
		if (fDir.isFile()) {
			byte[] bt = new byte[2048];
			ZipEntry ze = new ZipEntry(pName + fDir.getName());
			// 设置压缩前的文件大小
			ze.setSize(fDir.length());
			zos.putNextEntry(ze);
			FileInputStream fis = new FileInputStream(fDir);
			int i = 0;
			while ((i = fis.read(bt)) != -1) {
				zos.write(bt, 0, i);
			}
			fis.close();
		}
	}

	static int getUnsignedByte(byte data) { // 将data字节型数据转换为0~255 (0xFF
											// 即BYTE)。
		return data & 0x0FF;
	}

	static byte bswap(byte a) {
		byte b = 0;
		for (int i = 0; i < 8; ++i)
			b |= ((a & (1 << i)) == 0 ? 0 : 1) << (7 - i);
		return b;
	}

	private static Log log = LogFactory.getLog(EncryptUtil.class);

}
