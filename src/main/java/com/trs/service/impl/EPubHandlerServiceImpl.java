package com.trs.service.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.trs.service.EpubHandlerService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Component
public class EPubHandlerServiceImpl implements EpubHandlerService {
	private static Logger logger = LoggerFactory
			.getLogger(EPubHandlerServiceImpl.class);
	static final int BUFFER = 2048;

	public void handleEpubOpf(String targetPath) {
		if (targetPath.lastIndexOf("/") != targetPath.length() - 1) {
			targetPath += "/";
		}
		try {
			// 添加document.domain，实现跨域访问
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			try {
				db = dbf.newDocumentBuilder();
				logger.debug(targetPath + "METAINF/container.xml");
				Document doc = db.parse(targetPath + "METAINF/container.xml");
				Element root = doc.getDocumentElement();
				NodeList nl = root.getElementsByTagName("rootfile");
				String opfFilePath = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					if (e.getAttribute("media-type").equals(
							"application/oebps-package+xml")) {
						opfFilePath = e.getAttribute("full-path");
						break;
					}
				}
				opfFilePath = targetPath + opfFilePath;
				BufferedReader bf = new BufferedReader(new FileReader(
						opfFilePath));
				String content = "";
				StringBuilder sb = new StringBuilder();
				while (content != null) {
					content = bf.readLine();
					if (content == null) {
						break;
					}
					sb.append(content.trim());
				}
				bf.close();
				String s = sb.toString();
				s = s.replaceAll("opf:", "");

				byte[] b = s.getBytes();
				BufferedOutputStream stream = null;
				File file1 = null;
				try {
					file1 = new File(opfFilePath);
					FileOutputStream fstream = new FileOutputStream(file1);
					stream = new BufferedOutputStream(fstream);
					stream.write(b);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (stream != null) {
						try {
							stream.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void handleEpubHtml(String targetPath) throws TransformerException {
		// complete
		if (targetPath.lastIndexOf("/") != targetPath.length() - 1) {
			targetPath += "/";
		}
		try {
			// 添加document.domain，实现跨域访问
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			try {
				db = dbf.newDocumentBuilder();
				logger.debug(targetPath + "METAINF/container.xml");
				Document doc = db.parse(targetPath + "METAINF/container.xml");
				Element root = doc.getDocumentElement();
				NodeList nl = root.getElementsByTagName("rootfile");
				String opfFilePath = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					if (e.getAttribute("media-type").equals(
							"application/oebps-package+xml")) {
						opfFilePath = e.getAttribute("full-path");
						break;
					}
				}

				boolean isSave = false;
				File file = new File(targetPath + opfFilePath);
				String theParentPath = file.getParent() + "/";
				Document opfDoc = db.parse(targetPath + opfFilePath);
				Element opfRoot = opfDoc.getDocumentElement();
				NodeList spine = opfRoot.getElementsByTagName("spine");
				Element spineElement = (Element) spine.item(0);
				NodeList itemrefList = spineElement
						.getElementsByTagName("itemref");
				NodeList resourceList = opfRoot.getElementsByTagName("item");
				for (int i = 0; i < itemrefList.getLength(); i++) {
					Element el = (Element) itemrefList.item(i);
					for (int j = 0; j < resourceList.getLength(); j++) {
						Element re = (Element) resourceList.item(j);
						// 找到资源文件
						if (el.getAttribute("idref").equals(
								re.getAttribute("id"))) {
							String resourceFilePath = re.getAttribute("href");

							if (resourceFilePath.lastIndexOf(".xhtml") > 0) {
								re.setAttribute("href", resourceFilePath
										.replace(".xhtml", ".html").replace("%20", " "));
								isSave = true;
							}
							resourceFilePath = resourceFilePath.replace("%20", " ");
							File resourceFile = new File(theParentPath
									+ resourceFilePath);
							File tempFile = new File(theParentPath
									+ "html.temp");
							if (tempFile.exists()) {
								tempFile.delete();
							}

							resourceFile.renameTo(tempFile);

							org.jsoup.nodes.Document doc2 = Jsoup.parse(
									tempFile, "utf-8");
							org.jsoup.nodes.Element head = doc2
									.getElementsByTag("head").get(0);
							TextNode node = new TextNode(
									"document.domain = 'dzzgsw.com';", "");
							org.jsoup.nodes.Element e = doc2
									.createElement("script");
							e.appendChild(node);
							org.jsoup.nodes.Element e2 = doc2
									.createElement("meta");
							e2.attr("http-equiv", "Content-Type");
							e2.attr("content", "text/html; charset=utf-8");
							head.appendChild(e2);
							head.appendChild(e);

							// 增加选中代码
							// <link rel="stylesheet"
							// href="${ctxStatic}/reader/epub_reader.css" />

							org.jsoup.nodes.Element e3 = doc2
									.createElement("link");
							e3.attr("rel", "stylesheet");
							e3.attr("href",
									"http://www.dzzgsw.com/static/reader/epub_reader.css");
							head.appendChild(e3);

							// <script type="text/javascript"
							// src="${ctxStatic}/reader/epub_reader.js"></script>
							// <script type="text/javascript"
							// src="${ctxStatic}/layer/layer.js">
							org.jsoup.nodes.Element e4 = doc2
									.createElement("script");
							e4.attr("type", "text/javascript");
							e4.attr("src",
									"http://www.dzzgsw.com/static/reader/epub_reader.js");
							head.appendChild(e4);

							org.jsoup.nodes.Element e5 = doc2
									.createElement("script");
							e5.attr("type", "text/javascript");
							e5.attr("src",
									"http://www.dzzgsw.com/static/layer/layer.js");
							head.appendChild(e5);

							// <script type="text/javascript"
							// src="${ctxStatic}/reader/libs/dist/ZeroClipboard.js"></script>

							org.jsoup.nodes.Element e6 = doc2
									.createElement("script");
							e6.attr("type", "text/javascript");
							e6.attr("src",
									"http://www.dzzgsw.com/static/reader/libs/dist/ZeroClipboard.js");
							head.appendChild(e6);

							File html = new File(resourceFile.getAbsolutePath()
									.replaceAll("xhtml", "html"));
							html.createNewFile();
							FileOutputStream fos = new FileOutputStream(html,
									false);
							OutputStreamWriter osw = new OutputStreamWriter(
									fos, "UTF-8");
							osw.write(doc2.html());
							osw.close();

							tempFile.delete();
						}
					}
				}

				if (isSave == true) {
					TransformerFactory transformerFactory = TransformerFactory
							.newInstance();
					Transformer transformer = transformerFactory
							.newTransformer();
					DOMSource opfSource = new DOMSource(opfDoc);
					transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
					StreamResult opfResult = new StreamResult(file);
					transformer.transform(opfSource, opfResult);

					modifyCatalog(targetPath);
				}

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleEpubImage(String targetPath) throws TransformerException {
		// complete
		if (targetPath.lastIndexOf("/") != targetPath.length() - 1) {
			targetPath += "/";
		}
		try {
			// 添加document.domain，实现跨域访问
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			try {
				db = dbf.newDocumentBuilder();
				logger.debug(targetPath + "METAINF/container.xml");
				Document doc = db.parse(targetPath + "METAINF/container.xml");
				Element root = doc.getDocumentElement();
				NodeList nl = root.getElementsByTagName("rootfile");
				String opfFilePath = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					if (e.getAttribute("media-type").equals(
							"application/oebps-package+xml")) {
						opfFilePath = e.getAttribute("full-path");
						break;
					}
				}
				File file = new File(targetPath + opfFilePath);
				String theParentPath = file.getParent() + "/";
				Document opfDoc = db.parse(targetPath + opfFilePath);
				Element opfRoot = opfDoc.getDocumentElement();
				NodeList spine = opfRoot.getElementsByTagName("spine");
			

				NodeList resourceList = opfRoot.getElementsByTagName("item");

				for (int j = 0; j < resourceList.getLength(); j++) {
					Element re = (Element) resourceList.item(j);
					if (re.getAttribute("id").equals("coverpage")) {
						continue;
					}
					String resourceFilePath = re.getAttribute("href");
					if (resourceFilePath.lastIndexOf(".html") > 0
							|| resourceFilePath.lastIndexOf(".xhtml") > 0
							|| resourceFilePath.lastIndexOf(".htm") > 0) {

						File tempFile = new File(theParentPath
								+ resourceFilePath);
						org.jsoup.nodes.Document doc2 = Jsoup.parse(tempFile,
								"utf-8");
						Elements els = doc2.getElementsByTag("img");

						for (org.jsoup.nodes.Element e : els) {
							String src = e.attr("src");
							String src1 = src.replace("..", "");
							if (null == src1 || src1.equals("")) {
								continue;
							}
							if (src1.indexOf("cover") > 0 || src1.indexOf("Cover") > 0) {
								
							} else {
								resizeImage(theParentPath + src1);
							}
						}

					}
				}

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleEpubCSS(String targetPath) throws TransformerException {
		// complete
		if (targetPath.lastIndexOf("/") != targetPath.length() - 1) {
			targetPath += "/";
		}
		try {
			// 添加document.domain，实现跨域访问
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			try {
				db = dbf.newDocumentBuilder();
				logger.debug(targetPath + "METAINF/container.xml");
				Document doc = db.parse(targetPath + "METAINF/container.xml");
				Element root = doc.getDocumentElement();
				NodeList nl = root.getElementsByTagName("rootfile");
				String opfFilePath = null;
				for (int i = 0; i < nl.getLength(); i++) {
					Element e = (Element) nl.item(i);
					if (e.getAttribute("media-type").equals(
							"application/oebps-package+xml")) {
						opfFilePath = e.getAttribute("full-path");
						break;
					}
				}
				File file = new File(targetPath + opfFilePath);
				String theParentPath = file.getParent() + "/";
				Document opfDoc = db.parse(targetPath + opfFilePath);
				Element opfRoot = opfDoc.getDocumentElement();
				NodeList spine = opfRoot.getElementsByTagName("spine");
				Element spineElement = (Element) spine.item(0);

				NodeList resourceList = opfRoot.getElementsByTagName("item");

				for (int j = 0; j < resourceList.getLength(); j++) {
					Element re = (Element) resourceList.item(j);
					// 找到资源文件

					String resourceFilePath = re.getAttribute("href");
					if (resourceFilePath.lastIndexOf(".css") > 0) {

						BufferedReader bf = new BufferedReader(new FileReader(
								theParentPath + resourceFilePath));
						String content = "";
						StringBuilder sb = new StringBuilder();
						while (content != null) {
							content = bf.readLine();
							if (content == null) {
								break;
							}
							sb.append(content.trim());
						}
						bf.close();
						String s = sb.toString();
						s = s.replace("contents-title{font-size: 2em",
								"contents-title{font-size: 1.2em");

						byte[] b = s.getBytes();
						BufferedOutputStream stream = null;
						File file1 = null;
						try {
							file1 = new File(theParentPath + resourceFilePath);
							FileOutputStream fstream = new FileOutputStream(
									file1);
							stream = new BufferedOutputStream(fstream);
							stream.write(b);
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (stream != null) {
								try {
									stream.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}

					}
				}

			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 抽取Epub目录
	 * 
	 * @param targetPath
	 * @return
	 */
	public String modifyCatalog(String targetPath) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		String str = "";
		if (targetPath.lastIndexOf("/") != targetPath.length() - 1) {
			targetPath += "/";
		}
		File file = new File(targetPath);
		File fileXml = new File(targetPath + "METAINF/container.xml");

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(targetPath + "METAINF/container.xml");
			// 得到根节点
			Element root = doc.getDocumentElement();
			NodeList nl = root.getElementsByTagName("rootfile");
			String strOpf = "";
			for (int i = 0; i < nl.getLength(); i++) {
				Element e = (Element) nl.item(i);
				if (e.getAttribute("media-type").equals(
						"application/oebps-package+xml")) {
					strOpf = e.getAttribute("full-path");
					break;
				}

			}
			File files = new File(targetPath + strOpf);
			String opfParentUrl = "";
			if (files.exists()) {
				opfParentUrl = files.getParent();
			}
			Document docopf = db.parse(targetPath + strOpf);
			Element rootopf = docopf.getDocumentElement();
			NodeList nlopf = rootopf.getElementsByTagName("spine");
			String ncx = nlopf.item(0).getAttributes().getNamedItem("toc")
					.getNodeValue();
			NodeList nlopfNcx = rootopf.getElementsByTagName("item");
			String ncxUrl = "";
			for (int j = 0; j < nlopfNcx.getLength(); j++) {
				Element e = (Element) nlopfNcx.item(j);
				if (e.getAttribute("id").equals(ncx)) {
					ncxUrl = e.getAttribute("href");
					break;
				}
			}
			Document docncx = db.parse(opfParentUrl + "/" + ncxUrl);

			NodeList nodeList = docncx.getElementsByTagName("content");

			for (int i = 0; i < nodeList.getLength(); i++) {
				String aa = ((Element) nodeList.item(i)).getAttribute("src");
				((Element) nodeList.item(i)).setAttribute("src",
						((Element) nodeList.item(i)).getAttribute("src")
								.replaceAll("xhtml", "html"));
			}

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource opfSource = new DOMSource(docncx);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			StreamResult opfResult = new StreamResult(new File(opfParentUrl
					+ "/" + ncxUrl));
			transformer.transform(opfSource, opfResult);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static void resizeImage(String srcImgPath) throws IOException {
		Image srcImage = null;
		File srcFile = null;
		File destFile = null;
		String fileSuffix = null;

		int imageWidth = 0;
		int imageHeight = 0;
		File _file = new File(srcImgPath);
		srcFile = _file;
		fileSuffix = _file.getName().substring(
				(_file.getName().indexOf(".") + 1), (_file.getName().length()));
		destFile = new File(srcImgPath);
		srcImage = javax.imageio.ImageIO.read(_file);
		// 得到图片的原始大小， 以便按比例压缩。
		imageWidth = srcImage.getWidth(null);
		imageHeight = srcImage.getHeight(null);

		int w, h;
		if (imageHeight > 200) {
			// 得到合适的压缩大小，按比例。
			h = 200;
			w = (int) Math.round((imageWidth * h * 1.0 / imageHeight));

			srcFile.renameTo(new File(srcFile.getPath().substring(0,
					(srcFile.getPath().lastIndexOf(".")))
					+ "_big." + fileSuffix));
			// 构建图片对象
			BufferedImage _image = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			// 绘制缩小后的图
			_image.getGraphics().drawImage(srcImage, 0, 0, w, h, null);
			// 输出到文件流
			FileOutputStream out = new FileOutputStream(destFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(_image);
			out.flush();
			out.close();
		}

	}

	/**
	 * @param args
	 * @throws TransformerException
	 */
	public static void main(String[] args) throws TransformerException {
		EPubHandlerServiceImpl service = new EPubHandlerServiceImpl();
		service.handleEpubOpf("D:\\test11");
	}

	public void handleZhongzuohuawen(String targetPath) {
		handleEpubOpf(targetPath);
		try {
			handleEpubHtml(targetPath);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
