/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.test;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.netfinworks.rest.utils.ArrayUtil;

/**
 * <p>注释</p>
 * @author huipeng
 * @version $Id: ArrayUtilTest.java, v 0.1 Dec 25, 2014 1:59:40 PM knico Exp $
 */
public class ArrayUtilTest {
    @Test
    public void test_equre() {
        String[] a = new String[] { "a", "b", "c" };
        String[] b = new String[] { "c", "a", "b", "a" };
        Assert.assertFalse("Compare:" + Arrays.asList(a) + " <-> " + Arrays.asList(b), ArrayUtil.equals(a, b));
        a = new String[] { "a", "a", "b", "c" };
        Assert.assertTrue("Compare:" + Arrays.asList(a) + " <-> " + Arrays.asList(b), ArrayUtil.equals(a, b));
        a = new String[] { "a", "b", "b", "c" };
        Assert.assertFalse("Compare:" + Arrays.asList(a) + " <-> " + Arrays.asList(b), ArrayUtil.equals(a, b));
        Assert.assertFalse("Compare: null <-> " + Arrays.asList(b), ArrayUtil.equals(null, b));
        Assert.assertFalse("Compare:" + Arrays.asList(a) + " <-> null", ArrayUtil.equals(a, null));
        Assert.assertTrue("Compare: null <-> null", ArrayUtil.equals(null, null));
    }
}
