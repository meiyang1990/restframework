/**
 * Copyright 2014 netfinworks.com, Inc. All rights reserved.
 */
package com.netfinworks.rest.utils.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * <p>IP工具</p>
 * @author huipeng
 * @version $Id: IPUtil.java, v 0.1 Dec 15, 2014 1:17:07 PM knico Exp $
 */
public class IPUtil {
    public static String getLocalIp() {
        String ret = null;
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            while (nets.hasMoreElements()) {
                NetworkInterface net = nets.nextElement();
                if (net.isLoopback() || net.isVirtual() || net.getName().startsWith("vmnet")
                    || net.getName().startsWith("docker")) {
                    continue;
                }
                Enumeration<InetAddress> nis = net.getInetAddresses();
                while (nis.hasMoreElements()) {
                    InetAddress ip = nis.nextElement();
                    if (ip.isSiteLocalAddress()) {
                        ret = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            ret = getLocalIpSimple();
        }
        return ret;
    }

    /**
     * @return
     */
    public static String getLocalIpSimple() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Get ip failed!", e);
        }
    }
}
