package com.trs.core.util;


import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.Enumeration;

/**
 * Created by zly on 2017-5-11.
 */
public class IPUtil {


    /**
     * 验证IP是否属于某个IP段
     * @param ip
     * @param ipSection
     * @return
     */
    public static boolean ipExistsInRange(String ip, String ipSection) {

        ipSection = ipSection.trim();

        ip = ip.trim();

        int idx = ipSection.indexOf('-');

        String beginIP = ipSection.substring(0, idx);

        String endIP = ipSection.substring(idx + 1);

        return getIp2long(beginIP)<=getIp2long(ip) &&getIp2long(ip)<=getIp2long(endIP);

    }

    public static long getIp2long(String ip) {
        ip = ip.trim();

        String[] ips = ip.split("\\.");

        long ip2long = 0L;
        if (ip != null) {
            if (ip != null && ip != "") {


                for (int i = 0; i < 4; ++i) {

                    ip2long = ip2long << 8 | Integer.parseInt(ips[i]);

                }

            }
        }
        return ip2long;



    }

    public static long getIp2long2(String ip) {

        ip = ip.trim();

        String[] ips = ip.split("\\.");

        long ip1 = Integer.parseInt(ips[0]);

        long ip2 = Integer.parseInt(ips[1]);

        long ip3 = Integer.parseInt(ips[2]);

        long ip4 = Integer.parseInt(ips[3]);

        long ip2long =1L* ip1 * 256 * 256 * 256 + ip2 * 256 * 256 + ip3 * 256 + ip4;

        return ip2long;

    }

    /**
     * 把字符串IP转换成long
     *
     * @param ipStr 字符串IP
     * @return IP对应的long值
     */
    public static long ip2Long(String ipStr) {
        String[] ip = ipStr.split("\\.");
        return (Long.valueOf(ip[0]) << 24) + (Long.valueOf(ip[1]) << 16)
                + (Long.valueOf(ip[2]) << 8) + Long.valueOf(ip[3]);
    }

    /**
     * 把IP的long值转换成字符串
     *
     * @param ipLong IP的long值
     * @return long值对应的字符串
     */
    public static String long2Ip(long ipLong) {
        StringBuilder ip = new StringBuilder();
        ip.append(ipLong >>> 24).append(".");
        ip.append((ipLong >>> 16) & 0xFF).append(".");
        ip.append((ipLong >>> 8) & 0xFF).append(".");
        ip.append(ipLong & 0xFF);
        return ip.toString();
    }

    public static void main(String[] args) {
       System.out.println("ip2Long:"+ip2Long("192.168.113.144"));
        System.out.println("getIp2long:"+getIp2long("192.168.113.144"));
        System.out.println("getIp2long2:"+getIp2long2("192.168.113.144"));
        System.out.println(long2Ip(3232264592L));
        System.out.println(getServerIp());
    }

    /**
     * 获取访问的真实ip，（apach或者ngix跳转获取用户访问的真实ip）
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static String getServerIp(){
        String clientIp = "";
        // 根据网卡取本机配置的IP
        Enumeration<NetworkInterface> allNetInterfaces;  //定义网络接口枚举类
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();  //获得网络接口
            InetAddress ip = null; //声明一个InetAddress类型ip地址
            while (allNetInterfaces.hasMoreElements()) //遍历所有的网络接口
            {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses(); //同样再定义网络地址枚举类
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                    if (ip != null && (ip instanceof Inet4Address)) //InetAddress类包括Inet4Address和Inet6Address
                    {
                        if (!ip.getHostAddress().equals("127.0.0.1")) {
                            clientIp = ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return clientIp;
    }


}


