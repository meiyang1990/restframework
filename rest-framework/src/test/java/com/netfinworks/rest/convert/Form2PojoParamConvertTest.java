/**
 * 
 */
package com.netfinworks.rest.convert;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.netfinworks.rest.util.Encoding;

/**
 * @author bigknife
 *
 */

public class Form2PojoParamConvertTest {
	private Form2PojoParamConvert convert;
	@Before
	public void init(){
		convert = new Form2PojoParamConvert();
		convert.setEncoding(Encoding.UTF_8);
	}
	
	public static class TestItems{
		private int id;
		private double [] item;
		private String name;
		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}
		/**
		 * @return the item
		 */
		public double[] getItem() {
			return item;
		}
		/**
		 * @param item the item to set
		 */
		public void setItem(double[] item) {
			this.item = item;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	@Test
	public void testConvert() throws UnsupportedEncodingException{
		String raw = "id=1&item=0.9&item=1.0&item=8&name="+URLEncoder.encode("大刀",Encoding.UTF_8);
		TestItems items = convert.convert(raw, TestItems.class);
		
		Assert.assertNotNull(items);
		
		Assert.assertEquals("id=1", 1, items.getId());
		Assert.assertArrayEquals("items=[0.9,1.0,8]", new double[]{0.9,1.0,8}, items.getItem(),0);
		Assert.assertEquals("name=大刀", "大刀", items.getName());
	}
}
