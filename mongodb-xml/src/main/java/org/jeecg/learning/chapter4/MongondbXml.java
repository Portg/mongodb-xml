package org.jeecg.learning.chapter4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.BaseElement;

import com.google.gson.Gson;

public class MongondbXml {

	private static final String id = "id";
	/**
	 * 创建数据库
	 * @param path         全文件路径
	 * @throws Exception
	 */
	public void createDataBase(String path) throws Exception {

		//创建一篇文档
		Document doc = DocumentHelper.createDocument();
		FileOutputStream file = new FileOutputStream(path);
		// 添加一个根元素
		Element rootElement = doc.addElement("database");
		//创建元素
		Element table = new BaseElement("table");

		//添加属性
		table.addAttribute("name", "foo");
		rootElement.add(table);
		Element table1 =  new BaseElement("table");
		table1.addAttribute("name", "system.indexs");
		rootElement.add(table1);
		Element table2 =  new BaseElement("table");
		table2.addAttribute("name", "system.users");
		rootElement.add(table2);

		doc.setRootElement(rootElement);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");//设置编码  
		XMLWriter writer = new XMLWriter(file, format);
		writer.write(doc);
		writer.close();
	}

	/**
	 * 表插入数据
	 * 
	 * @param path        文件全路径
	 * @param tablename   表名
	 * @param po          插入对象
	 * @return            插入件数
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public int insertData(String path, String tableName, Object po)
			throws IOException, DocumentException {

		Gson gson = new Gson();
		int count = 0;
		FileInputStream file = new FileInputStream(path);
		// 创建saxReader对象  
		SAXReader reader = new SAXReader();
		// 通过read方法读取一个文件 转换成Document对象  
		Document document = reader.read(file);
		//获取根节点元素对象  
		Element root = document.getRootElement();

		// 当前节点下面子节点迭代器  
		Iterator<Element> it = root.elementIterator();
		// 遍历  
		while (it.hasNext()) {
			// 获取某个子节点对象  
			Element e = it.next();
			String table_name = e.attributeValue("name");
			if (table_name.equals(tableName)) {
				Map<String, String> base = new HashMap<String, String>();
				base.put(id, UUID.randomUUID().toString().toUpperCase());
				Element data = new BaseElement("data");
				String json = gson.toJson(po);
				@SuppressWarnings("unchecked")
				Map<String, String> mp = gson.fromJson(json, Map.class);
				base.putAll(mp);
				data.addText(gson.toJson(base));
				e.add(data);
				count++;
			}
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");//设置编码  
		XMLWriter writer = new XMLWriter(new FileOutputStream(path), format);
		writer.write(document);
		writer.close();
		System.out.println("----------insert --- data --- success----------------------");
		return count;
	}

	/**
	 * 表修改数据
	 * 
	 * @param path
	 * @param tablename
	 * @param po
	 * @throws JDOMException
	 * @throws IOException
	 * @throws DocumentException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateData(String path, String tableName, Object po)
			throws IOException, DocumentException {

		FileInputStream file = new FileInputStream(path);
		// 创建saxReader对象  
		SAXReader reader = new SAXReader();
		// 通过read方法读取一个文件 转换成Document对象  
		Document document = reader.read(file);

		Element root = document.getRootElement();
		Gson gson = new Gson();

		List<Element> list = root.elements("table");
		for (Element x : list) {
			String table_name = x.attributeValue("name");
			if (table_name.equals(tableName)) {
				List<Element> al = x.elements("data");
				for (Element s : al) {
					// 如果定位Data[通过id唯一标示]
					Map mp = gson.fromJson(s.getText(), Map.class);
					Map newmp = gson.fromJson(gson.toJson(po), Map.class);
					if (mp.get(id).equals(newmp.get(id))) {
						mp.putAll(newmp);
						Element data = new BaseElement("data");
						data.setText(gson.toJson(mp));
						x.remove(s);
						x.add(data);
						break;
					}
				}
			}
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");//设置编码  
		XMLWriter writer = new XMLWriter(new FileOutputStream(path), format);
		writer.write(document);
		writer.close();
		System.out.println("----------update --- data --- success----------------------");
	}

	/**
	 * 表删除数据
	 * 
	 * @param path
	 * @param tablename
	 * @param po
	 * @throws JDOMException
	 * @throws IOException
	 * @throws DocumentException 
	 */
	@SuppressWarnings("rawtypes")
	public void deleteData(String path, String tableName, Object po)
			throws IOException, DocumentException {

		FileInputStream file = new FileInputStream(path);
		Gson gson = new Gson();

		// 创建saxReader对象  
		SAXReader reader = new SAXReader();
		// 通过read方法读取一个文件 转换成Document对象  
		Document document = reader.read(file);

		Element root = document.getRootElement();
		List<Element> list = root.elements("table");

		for (Element x : list) {
			List<Element> al = x.elements("data");
			String table = x.attributeValue("name");
			if (table.equals(tableName)) {
				for (Element s : al) {
					// 如果定位Data[通过id唯一标示]
					Map mp = gson.fromJson(s.getText(), Map.class);
					Map newmp = gson.fromJson(gson.toJson(po), Map.class);
					if (mp.get(id).equals(newmp.get(id))) {
						x.remove(s);
						break;
					}
				}
			}
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");//设置编码  
		XMLWriter writer = new XMLWriter(new FileOutputStream(path), format);
		writer.write(document);
		writer.close();
		System.out.println("----------delete --- data --- success----------------------");
	}

	/**
	 * 获取表的全部数据
	 * 
	 * @param path
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> loadTableDatas(String path, String tableName) throws Exception {
		Gson gson = new Gson();
		FileInputStream file = new FileInputStream(path);

		// 创建saxReader对象  
		SAXReader reader = new SAXReader();
		// 通过read方法读取一个文件 转换成Document对象  
		Document document = reader.read(file);

		Element root = document.getRootElement();

		List<Map> l = new ArrayList<Map>();
		List<Element> list = root.elements("table");
		for (Element x : list) {
			String attrValue = x.attributeValue("name");
			if (attrValue.equals(tableName)) {
				List<Element> al = x.elements("data");
				for (Element s : al) {
					String data = s.getText();
					if (data == null) {
						break;
					}
					Map mp = gson.fromJson(data, Map.class);
					l.add(mp);
				}
			}
		}
		return l;
	}
}
