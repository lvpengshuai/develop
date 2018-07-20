package com.trs.core.util;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.beans.IntrospectionException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * poi 读取excel 支持2003 --2007 及以上文件
 *
 * @author 李春雨
 * @version V 2.0
 * @CreatTime 2016-08-24 @
 */
public class ExcelUtils {

	public static List<Map> read(MultipartFile file) throws Exception{
		CommonsMultipartFile cf= (CommonsMultipartFile)file;
		DiskFileItem fi = (DiskFileItem)cf.getFileItem();
		File f = fi.getStoreLocation();
		String name = fi.getName();
		return readExcel(name,f);
	}

	/**
	 * 合并方法，读取excel文件 根据文件名自动识别读取方式 支持97-2013格式的excel文档
	 *
	 * @param fileName
	 *            上传文件名
	 * @param file
	 *            上传的文件
	 * @return 返回列表内容格式： 每一行数据都是以对应列的表头为key 内容为value 比如 excel表格为：
	 *         =============== A | B | C | D ===|===|===|=== 1 | 2 | 3 | 4
	 *         ---|---|---|--- a | b | c | d --------------- 返回值 map： map1: A:1
	 *         B:2 C:3 D:4 map2: A:a B:b C:d D:d
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel(String fileName, File file) throws Exception {
		// 准备返回值列表
		List<Map> valueList = new ArrayList<Map>();

		if (fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()).equalsIgnoreCase("xls")) {
			valueList = readExcel2003(file.toString());
		} else if (fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()).equalsIgnoreCase("xlsx")) {
			valueList = readExcel2007(file.toString());
		}
		// 删除缓存文件
		file.delete();
		return valueList;

	}

	/**
	 * 读取97-2003格式
	 *
	 * @param filePath
	 *            文件路径
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2003(String filePath) throws IOException {
		// 返回结果集
		List<Map> valueList = new ArrayList<Map>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			HSSFWorkbook wookbook = new HSSFWorkbook(fis); // 创建对Excel工作簿文件的引用
			HSSFSheet sheet = wookbook.getSheetAt(0); // 在Excel文档中，第一张工作表的缺省索引是0
			int rows = sheet.getPhysicalNumberOfRows(); // 获取到Excel文件中的所有行数­
			Map<Integer, String> keys = new HashMap<Integer, String>();
			int cells = 0;
			// 遍历行­（第1行 表头） 准备Map里的key
			HSSFRow firstRow = sheet.getRow(0);
			if (firstRow != null) {
				// 获取到Excel文件中的所有的列
				cells = firstRow.getPhysicalNumberOfCells();
				// 遍历列
				for (int j = 0; j < cells; j++) {
					// 获取到列的值­
					try {
						HSSFCell cell = firstRow.getCell(j);
						String cellValue = getCellValue(cell);
						keys.put(j, cellValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 遍历行­（从第二行开始）
			for (int i = 1; i < rows; i++) {
				// 读取左上端单元格(从第二行开始)
				HSSFRow row = sheet.getRow(i);
				// 行不为空
				if (row != null) {
					// 准备当前行 所储存值的map
					Map<String, Object> val = new HashMap<String, Object>();

					boolean isValidRow = false;

					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值­
						try {
							HSSFCell cell = row.getCell(j);
							String cellValue = getCellValue(cell);
							val.put(keys.get(j), cellValue);
							if (!isValidRow && cellValue != null && cellValue.trim().length() > 0) {
								isValidRow = true;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// 第I行所有的列数据读取完毕，放入valuelist
					if (isValidRow) {
						valueList.add(val);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}
		return valueList;
	}

	/**
	 * 读取2007-2013格式
	 * @param filePath 文件路径
	 * @return
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2007(String filePath) throws IOException{
		List<Map> valueList=new ArrayList<Map>();
		FileInputStream fis =null;
		try {
			fis =new FileInputStream(filePath);
			XSSFWorkbook xwb = new XSSFWorkbook(fis);   // 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFSheet sheet = xwb.getSheetAt(0);            // 读取第一章表格内容
			// 定义 row、cell
			XSSFRow row;
			// 循环输出表格中的第一行内容   表头
			Map<Integer, String> keys=new HashMap<Integer, String>();
			row = sheet.getRow(0);
			if(row !=null){
				for (int j = row.getFirstCellNum(); j <=row.getPhysicalNumberOfCells(); j++) {
					// 通过 row.getCell(j).toString() 获取单元格内容，
					if(row.getCell(j)!=null){
						if(!row.getCell(j).toString().isEmpty()){
							keys.put(j, row.getCell(j).toString());
						}
					}else{
						keys.put(j, "K-R1C"+j+"E");
					}
				}
			}
			// 循环输出表格中的从第二行开始内容
			for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				//short tmp = 0;
				//int index = lastCellNum + tmp;
				if (row != null) {
					short lastCellNum = row.getLastCellNum();
					boolean isValidRow = false;
					Map<String, Object> val = new HashMap<String, Object>();
					for (int j = row.getFirstCellNum(); j < lastCellNum; j++) {
						DecimalFormat nf = new DecimalFormat("0");
						XSSFCell cell = row.getCell(j);
						if (cell != null) {
							String cellValue = null;
							if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
								if(DateUtil.isCellDateFormatted(cell)){
									cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
								}
								else if("General".equals(cell.getCellStyle()
										.getDataFormatString())){
									cellValue = nf.format(cell.getNumericCellValue());
								}else {
									cellValue = String.valueOf(cell.getNumericCellValue());
								}
							}
							else{
								cellValue = cell.toString();
							}
							if(cellValue!=null&&cellValue.trim().length()<=0){
								cellValue=null;
							}
							val.put(keys.get(j), cellValue);
							if(!isValidRow && cellValue!= null && cellValue.trim().length()>0){
								isValidRow = true;
							}
						}
					}

					// 第I行所有的列数据读取完毕，放入valuelist
					if (isValidRow) {
						valueList.add(val);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			fis.close();
		}

		return valueList;
	}


	/**
	 * 读取2007excel
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<List<Object>> read2007Excel(File file)
			throws IOException {

		List<List<Object>> list = new LinkedList<List<Object>>();
		// String path = System.getProperty("user.dir") +
		// System.getProperty("file.separator")+"dd.xlsx";
		// System.out.println("路径："+path);
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));

		// 读取第一章表格内容
		XSSFSheet sheet = xwb.getSheetAt(0);
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		for (int i = sheet.getFirstRowNum(); i <= sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// 格式化 number String
				// 字符
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
				DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字

				switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if ("@".equals(cell.getCellStyle().getDataFormatString())) {
							value = df.format(cell.getNumericCellValue());

						} else if ("General".equals(cell.getCellStyle()
								.getDataFormatString())) {
							value = nf.format(cell.getNumericCellValue());
						} else {
							value = sdf.format(HSSFDateUtil.getJavaDate(cell
									.getNumericCellValue()));
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = cell.getBooleanCellValue();
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						value = "";
						break;
					default:
						value = cell.toString();
				}
				if (value == null || "".equals(value)) {
					continue;
				}
				linked.add(value);
			}
			list.add(linked);
		}
		return list;
	}


	/**
	 * 文件操作 获取文件扩展名
	 *
	 * @Author: sunny
	 * @param filename
	 *            文件名称包含扩展名
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/** -----------上传文件,工具方法--------- */
	private static final int BUFFER_SIZE = 2 * 1024;

	/**
	 *
	 * @param src
	 *            源文件
	 * @param dst
	 *            目标位置
	 */
	private static void copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 上传copy文件方法(for MultipartFile)
	 *
	 *
	 *            新的文件名称， 采用系统时间做文件名防止中文报错的问题
	 * @throws Exception
	 */

	public static void multipartcopy(MultipartFile file, String savePath, String newname) throws Exception {
		try {
			File targetFile = new File(savePath, newname);
			if (!targetFile.exists()) {
				// 判断文件夹是否存在，不存在就创建
				targetFile.mkdirs();
			}

			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
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

	private static String getCellValue(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		String cellValue = null;
		if (cell == null)
			return null;
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					break;
				}
				cellValue = df.format(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_STRING:
				cellValue = String.valueOf(cell.getStringCellValue());
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				cellValue = String.valueOf(cell.getCellFormula());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				cellValue = null;
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				cellValue = String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				cellValue = String.valueOf(cell.getErrorCellValue());
				break;
		}
		if (cellValue != null && cellValue.trim().length() <= 0) {
			cellValue = null;
		}
		return cellValue;
	}
	/**
	 * 保存文件返回路径
	 */
	public static String savefile(String fileName, MultipartFile file) throws Exception {
		// 设置文件缓存的目录,获取服务器的临时目录
		String filePathTemp = System.getProperty("java.io.tmpdir");

		// 将上传文件更改名称
		String tmpFileName = System.currentTimeMillis() + "." + getExtensionName(fileName);

		// 判断临时目录是否存在不存在则创建
		File fileList = new File(filePathTemp);
		if (!fileList.exists() && !fileList.isDirectory()) {
			fileList.mkdir();
		}

		// 获取压缩包的路径
		String filePath = filePathTemp + System.getProperty("file.separator") + tmpFileName;

		// 拷贝文件到服务器缓存目录（在项目下）
		multipartcopy(file, filePathTemp, tmpFileName);

		// 返回文件路径
		return filePath;

	}

	public static Workbook downloadExcel(Map map) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
		//拿到原始数据
		List list = (List) map.get("data");
		Workbook workbook=new HSSFWorkbook();
		//创建sheet
		Sheet sheet = workbook.createSheet(map.get("fileName").toString());
		//设置行高
		sheet.setDefaultRowHeight((short) (600));
		//创建单元格样式
		CellStyle cellStyle = workbook.createCellStyle();
		//单元格自动换行
		cellStyle.setWrapText(true);
		//单元格居中
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//创建行
		Row row = sheet.createRow(0);
		//创建表格第一行内容
		List firstRow = (List) map.get("firstRow");
		for (int i = 0; i < firstRow.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(firstRow.get(i).toString());
			cell.setCellStyle(cellStyle);
			//列宽
			sheet.setColumnWidth(i, 256 * 35 + 184);
		}
		List data = (List) map.get("data");
		List rowValue = (List) map.get("rowValue");
		for (int i = 0; i < data.size(); i++) {
			Row rowData = sheet.createRow(i + 1);
			//获取当前行数据，并将对象转换成map
			Map<String,Object> mapObject= (Map<String, Object>) data.get(i);
			//设置每个单元格内容
			for (int j = 0; j < rowValue.size(); j++) {
				String key = (String) rowValue.get(j);
				String value = mapObject.get(key).toString();
				Cell cell = rowData.createCell(j);
				if (value.length()>=30000){
					cell.setCellValue(value.substring(0,30000));
				}else {
					cell.setCellValue(value);
				}
				cell.setCellStyle(cellStyle);
			}
		}
		return workbook;
	}

}