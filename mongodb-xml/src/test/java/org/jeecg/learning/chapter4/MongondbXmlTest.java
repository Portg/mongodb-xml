package org.jeecg.learning.chapter4;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MongondbXmlTest {

	private static final String DATABASE_NAME = "mongodb.xml";

	@Test
	public void testCreateDataBase() {

		try {
			MongondbXml mango = new MongondbXml();
			mango.createDataBase(DATABASE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	@Test
	public void testInsertData() {

		Address addr = new Address();
		addr.setCountry("中国");
		addr.setCity("北京市");
		addr.setStreet("北京市海淀区学院路35号");
		try {
			MongondbXml mango = new MongondbXml();
			mango.insertData(DATABASE_NAME, "foo", addr);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	@Test
	public void testUpdateData() {

		Address addr = new Address();
		addr.setId("4A9383AE-F67B-46FC-8384-E4EB73EB8D0D");
		addr.setCity("天津市");
		addr.setStreet("新兴街道卫津路143号广播电视国际新闻中心");
		try {
			MongondbXml mango = new MongondbXml();
			mango.updateData(DATABASE_NAME, "foo", addr);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	@Test
	public void testLoadTableDatas() {
		try {
			MongondbXml mango = new MongondbXml();
			@SuppressWarnings("rawtypes")
			List<Map> list = mango.loadTableDatas(DATABASE_NAME, "foo");
			System.out.println(list.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	@Test
	public void testDeleteData() {
		Address addr = new Address();
		addr.setId("4A9383AE-F67B-46FC-8384-E4EB73EB8D0D");
		try {
			MongondbXml mango = new MongondbXml();
			mango.deleteData(DATABASE_NAME, "foo", addr);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
