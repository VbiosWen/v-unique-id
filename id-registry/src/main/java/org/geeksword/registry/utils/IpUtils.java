package org.geeksword.registry.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.time.Instant;
import java.util.Enumeration;
import java.util.Objects;

@Slf4j
public class IpUtils {

    private static final String IP_URL = "http://ip-api.com/json/?lang=zh-CN";


    /**
     * 获取公网ip，这个获取不到
     *
     * @return
     */
    @Deprecated
    public static String getIp() {
        String ip = null;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    System.out.println(inetAddress.getHostAddress());
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && !inetAddress.isSiteLocalAddress()) {
                        ip = inetAddress.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            log.error("get the ip address error:", e);
        }
        return ip;
    }

    public static String getIp0() {
        String ip;
        URL url;
        URLConnection urlConnection;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            url = new URL(IP_URL);
            urlConnection = url.openConnection();
            inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder resp = new StringBuilder();
            String buf;
            while ((buf = bufferedReader.readLine()) != null) {
                resp.append(buf);
            }
            JSONObject jsonObject = JSON.parseObject(resp.toString());
            ip = jsonObject.getString("query");
            return ip;
        } catch (IOException e) {
            log.error("io exception:", e);
        } finally {
            closeStream(bufferedReader, inputStream);
        }
        return null;
    }

    @SneakyThrows
    private static void closeStream(BufferedReader bufferedReader, InputStream inputStream) {
        if (Objects.nonNull(bufferedReader)) {
            bufferedReader.close();
        }
        if (Objects.nonNull(inputStream)) {
            inputStream.close();
        }
    }

    public static void main(String[] args) {
        long start = Instant.now().toEpochMilli();
        String ip = getIp0();
        System.out.println(Instant.now().toEpochMilli() - start);
        System.out.println(ip);
    }
}
