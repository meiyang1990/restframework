/**
 * 
 */
package com.netfinworks.rest.auth;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.netfinworks.rest.util.Magic;

/**
 * @author bigknife
 *
 */
public class SimpleUserPasswordCheckTest {
	private SimpleUserPasswordCheck check;
	
	@Before
	public void init(){
		check = new SimpleUserPasswordCheck();
	}
	
	@Test
	public void testNoSystemProperty(){
		//设置空字符串，则使用默认的路径
		
		boolean ret = check.checkUserPassword("bigknife", "123456");
		Assert.assertEquals(ret, true);
		
		System.setProperty(SimpleUserPasswordCheck.SystemPropertyName, "    ");
		ret = check.checkUserPassword("bigknife", "123456");
		Assert.assertEquals(ret, true);
	}
	
	@Test
	public void testSystemProperty(){
		System.setProperty(SimpleUserPasswordCheck.SystemPropertyName, "nofile");
		try{
			check.checkUserPassword("bigknife", "123456");
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		System.setProperty(SimpleUserPasswordCheck.SystemPropertyName,Magic.EmtpyString);
	}
}
