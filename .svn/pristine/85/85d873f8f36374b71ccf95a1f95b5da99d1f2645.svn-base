/**
 * 
 */
package com.netfinworks.rest.convert;

import org.junit.Test;

/**
 * @author bigknife
 *
 */
public class NeverUsedParamConvertTest {
	@Test
	public void testConvert(){
		NeverUsedParamConvert convert = new NeverUsedParamConvert();
		try{
			convert.convert("hello", String.class);
		}catch(IllegalAccessError error){
			//ok
			error.printStackTrace();
		}
	}
}
