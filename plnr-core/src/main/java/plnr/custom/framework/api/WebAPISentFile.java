package plnr.custom.framework.api;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import plnr.custom.framework.Config;
import plnr.custom.framework.api.intf.IWebAPINotification;
import plnr.custom.framework.core.base.model.BaseDateTime;

/**
 * Created by nattapongr on 5/13/15.
 */
public class WebAPISentFile {

    private static WebAPISentFile ourInstance = new WebAPISentFile();

    private WebAPISentFile() {
    }

    public synchronized static WebAPISentFile getInstance() {
        if (ourInstance == null)
            ourInstance = new WebAPISentFile();
        return ourInstance;
    }

    public void sent(String key, String url, File file, String name, String type, final IWebAPINotification callback, final Class Class) {
        sent(key, url, file, name, type, callback, Class, 10);
    }

    public void sent(final String key, final String url, final File file, final String name, final String type, final IWebAPINotification callback, final Class Class, final long numberOfDayCache) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    postUpload(key, file, name, type, callback, Class, url, Config.DAY * numberOfDayCache);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public synchronized void postUpload(String key, File file, String name, String type, final IWebAPINotification callback, Class Class, String urlS, long numberOfDayCache) {

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        int resCode = 0;
        String resMessage = "";

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        try {
            /** Check file on SD Card ***/
            if (!file.exists()) {
            }

            FileInputStream fileInputStream = new FileInputStream(file);

            URL url = new URL(urlS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            DataOutputStream outputStream = new DataOutputStream(conn
                    .getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream
                    .writeBytes("Content-Disposition: form-data; name=\"" + key + "\";filename=\""
                            + name + "-" + (new BaseDateTime()) + "-" + "." + type + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Response Code and  Message
            resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                int read = 0;
                while ((read = is.read()) != -1) {
                    bos.write(read);
                }
                byte[] result = bos.toByteArray();
                bos.close();

                resMessage = new String(result);

            }

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();

            new CheckResponseCode().checkStatusAndCallBack(callback, 888, resMessage.toString(), Class);
        } catch (Exception ex) {
            new CheckResponseCode().checkStatusAndCallBack(callback, 889, resMessage.toString(), Class);
        }
    }
}
