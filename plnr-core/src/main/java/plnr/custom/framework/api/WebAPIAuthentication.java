package plnr.custom.framework.api;

import android.net.Uri;

import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;

import plnr.custom.framework.Config;
import plnr.custom.framework.api.intf.IWebAPINotification;
import plnr.custom.framework.authentication.models.Authen;
import plnr.custom.framework.core.converter.UnicodeConverter;

/**
 * Created by nattapongr on 5/13/15.
 */
public class WebAPIAuthentication {
    private static WebAPIAuthentication ourInstance = new WebAPIAuthentication();
    private Timer tm;
    private String json, url;
    private Authen upload;

    private WebAPIAuthentication() {
    }

    public static WebAPIAuthentication getInstance() {
        if (ourInstance == null)
            ourInstance = new WebAPIAuthentication();
        return ourInstance;
    }

    public synchronized void authen(String url, final Authen info, final IWebAPINotification callback, final Class Class) {
        this.url = url;
        json = UnicodeConverter.unicodeEscapedString(info.toJSONLocalString());
        upload = info;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    postUpload(callback, Class);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    //////////////////////////////////////////////////////
    // Apache library is obsolete from api 22 and above //
    //////////////////////////////////////////////////////
//    private void postUpload(final IWebAPINotification callback, Class Class) {
//        timeOut(callback, Class);
//        HttpResponse response = null;
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
//                Config.RequestDefaultTimeout);
//        HttpConnectionParams.setSoTimeout(httpclient.getParams(), Config.RequestDefaultTimeout);
//        HttpPost post = new HttpPost(this.url);
//        String responseString = "";
//        try {
//            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//            pairs.add(new BasicNameValuePair(WebAPI.KEY_PARAM_JSON_HTTP_REQUEST, upload.toJSONString()));
//            post.setEntity(new UrlEncodedFormEntity(pairs));
//            response = httpclient.execute(post);
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            response.getEntity().writeTo(out);
//            out.close();
//            responseString = StringEscapeUtils.unescapeJson(out.toString().trim());
//        } catch (IOException e) {
//            responseString = e.getMessage();
//        }
//        tm.cancel();
//        if (response != null)
//            new CheckResponseCode().checkStatusAndCallBack(callback, response.getStatusLine().getStatusCode(), responseString, Class);
//        else
//            callback.webAPIFailed("404", "Check your internet connection", Class);
//    }

    private void postUpload(final IWebAPINotification callback, Class Class) {

        timeOut(callback, Class);

        int responseCode = 0;
        String responseString = "";
        String responseStringUnEscape = "";

        HttpURLConnection conn = WebAPIConnection.createHttpConnection(Class.getSimpleName() , url);

        Uri.Builder builder = new Uri.Builder().appendQueryParameter(WebAPI.KEY_PARAM_JSON_HTTP_REQUEST, upload.toJSONString());
        String query = builder.build().getEncodedQuery();

        WebAPIConnection.postQueryParameterString(conn , query);

        responseCode = WebAPIConnection.getConnectionResponseCode(Class.getSimpleName() , conn);
        responseString = WebAPIConnection.getConnectionResponseString(Class.getSimpleName() , conn);
        responseStringUnEscape = WebAPIConnection.unEscapeResponseStringWithTrim(responseString);

        tm.cancel();
        if (responseStringUnEscape != null)
            new CheckResponseCode().checkStatusAndCallBack(callback, responseCode , responseString, Class);
        else
            callback.webAPIFailed("404", "Check your internet connection", Class);
    }

    private void timeOut(final IWebAPINotification callback, final Class Class) {
        tm = new Timer();
        tm.schedule(new TimerTask() {
            @Override
            public void run() {
                new CheckResponseCode().checkStatusAndCallBack(callback, 0, "", Class);
            }
        }, Config.RequestDefaultTimeout, Config.RequestDefaultTimeout);
    }
}
