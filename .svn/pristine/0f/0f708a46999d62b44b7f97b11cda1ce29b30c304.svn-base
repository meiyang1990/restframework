/**
 * 
 */
package com.netfinworks.rest.convert;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author bigknife
 *
 */
public class PrimitiveParamConvertTest {
	@Test
	public void testConvert(){
		PrimitiveParamConvert bpc = new PrimitiveParamConvert();
		
		String raw = "1";
		int iRaw = bpc.convert(raw, int.class);
		Assert.assertEquals(iRaw, 1);
		
		raw = "0.01";
		float fRaw = bpc.convert(raw, Float.class);
		Assert.assertEquals(fRaw, 0.01f);
		
		double dRaw = bpc.convert(raw, Double.class);
		Assert.assertEquals(dRaw, 0.01d);
		
		raw = "9999";
		long lRaw = bpc.convert(raw, Long.class);
		Assert.assertEquals(lRaw, 9999l);
		
		char cRaw = bpc.convert(raw, Character.class);
		Assert.assertEquals(cRaw, '9');
		
		
	}
}
