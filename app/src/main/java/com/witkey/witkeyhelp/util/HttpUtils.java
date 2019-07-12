package com.witkey.witkeyhelp.util;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author lingxu
 * @date 2019/7/12 10:32
 * @description 联网工具类--上传文件,下载数据流
 */
public class HttpUtils {

    /**
     * 上传文件
     */
    public static String uploadFile(String url, String filePath,
                                    String[] keyArray, String[] valuesArray,String key) {

        String boundary = "***329928569***";

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();

            // 设置可读写，不使用缓存
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求属性
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
			/* 设置StrictMode 否则HTTPURLConnection连接失败，因为这是在主进程中进行网络连接 */
            // StrictMode.setThreadPolicy(new
            // StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            // 数据输出流
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            // 参数
            if (keyArray != null && keyArray.length > 0) {

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < keyArray.length; i++) {
                    sb.append("--" + boundary + "\r\n");
                    sb.append("Content-Disposition: form-data; name=\""
                            + keyArray[i] + "\"" + "\r\n");
                    sb.append("\r\n");
                    sb.append(valuesArray[i] + "\r\n");
                }
                dos.write(sb.toString().getBytes());
            }
            // ////////////////////////////////////////////////////////////////////////
            String fileName = filePath;
            // ////////////////////////////////////////////////////////////////////////
            dos.writeBytes("--" + boundary + "\r\n");
            dos.writeBytes("Content-Disposition: form-data; "
                    + "name=\""+key+"\";filename=\"" + fileName + "\"" + "\r\n");
            dos.writeBytes("\r\n");

            // 文件输入流
            FileInputStream fis = new FileInputStream(filePath);
            // 缓冲大小 8K
            byte[] buffer = new byte[8192];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {

                dos.write(buffer, 0, length);
            }
            dos.writeBytes("\r\n");
            dos.writeBytes("--" + boundary + "--" + "\r\n");
            // 关闭流，写入的东西自动生成HTTP正文
            fis.close();
            dos.close();
            conn.connect();
            // 读取响应信息
            InputStream is = conn.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {

                b.append((char) ch);
            }
            return b.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 发送get请求 获取服务端返回的输入流
     *
     */
    public static InputStream getStream(String path) throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        InputStream is = conn.getInputStream();
        return is;
    }


}
