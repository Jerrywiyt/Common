package com.lujunyu.id;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IDGeneratorFactory {

    public static IDGenerator creat() {
        return new SnowFlake(getMachineId());
    }

    public static IDGenerator creat(long machineId) {
        Preconditions.checkArgument(machineId > 0, "machineId must > 0");
        return new SnowFlake(machineId % 1024);
    }

    private static long getMachineId() {
        String serverIp = getServerIp();
        Preconditions.checkArgument(StringUtils.isNotBlank(serverIp),"server ip is blank");
        long machineId = 0L;
        String[] strs = serverIp.split("\\.");
        for (String s : strs){
            machineId += Long.valueOf(s);
        }
        return machineId;
    }


    private static String getServerIp() {
        String serverIp = "";
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) netInterfaces
                        .nextElement();
                InetAddress ip = ni.getInetAddresses().nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                        &&!ip.getHostAddress().contains(":")) {
                    serverIp = ip.getHostAddress();
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return serverIp;
    }
}
