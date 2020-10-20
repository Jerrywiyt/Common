package com.lujunyu.utils;

import org.apache.commons.lang.ArrayUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.regex.Pattern;

public class AddressHelper {
  public static final String IPV4_ERROR = "ipv4_error";
  private static volatile String ipv4 = null;

  public static String getLocalhostIPV4() {
    if (ipv4 == null) {
      Class var0 = AddressHelper.class;
      synchronized (AddressHelper.class) {
        if (ipv4 == null) {
          ipv4 = IPV4_ERROR;
          try {
            InetAddress[] arr$ = getInetAddresses("e(\\w)+\\d");
            for (InetAddress inetAddress : arr$) {
              ipv4 = inetAddress.getHostAddress();
              System.out.println("scanning ip ---> " + ipv4);
              if (!ipv4.contains(":")) {
                break;
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
    return ipv4;
  }

  private static InetAddress[] getInetAddresses(String regex) throws SocketException {
    Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
    Iterator i$ = Collections.list(nets).iterator();

    NetworkInterface netint;
    do {
      if (!i$.hasNext()) {
        return new InetAddress[0];
      }

      netint = (NetworkInterface) i$.next();
    } while (!Pattern.matches(regex, netint.getDisplayName()));

    Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
    ArrayList<InetAddress> list = Collections.list(inetAddresses);
    InetAddress[] addrs = (InetAddress[]) list.toArray(new InetAddress[list.size()]);
    if (list.size() > 1 && ((InetAddress) list.get(0)).getHostAddress().contains(":")) {
      ArrayUtils.reverse(addrs);
    }

    return addrs;
  }
}
