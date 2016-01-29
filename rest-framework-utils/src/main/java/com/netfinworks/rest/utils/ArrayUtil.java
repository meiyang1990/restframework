/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.rest.utils;

/**
 * <p>数组工具</p>
 * @author huipeng
 * @version $Id: ArrayUtil.java, v 0.1 Dec 25, 2014 1:56:03 PM knico Exp $
 */
public class ArrayUtil {

    /**
     * 比较数据，只关心内容是否相同，不关心次序
     * @param array1
     * @param array2
     * @return
     */
    public static <T> boolean equals(T[] array1, T[] array2) {
        boolean ret = array1 == array2;
        if (!ret) {
            if (array1 == null || array2 == null) {
            } else if (array1.length != array2.length) {
            } else {
                ret = true;
                boolean[] flags = new boolean[array2.length];
                for (int i = 0; i < array1.length; i++) {
                    boolean temp = false;
                    for (int j = 0; j < array1.length; j++) {
                        if (array1[i].equals(array2[j]) && (!flags[j])) {
                            flags[j] = temp = true;
                            break;
                        }
                    }
                    if (!temp) {
                        ret = false;
                        break;
                    }
                }
                for (boolean b : flags) {
                    if (!ret)
                        break;
                    ret &= b;
                }
            }
        }
        return ret;
    }
}
