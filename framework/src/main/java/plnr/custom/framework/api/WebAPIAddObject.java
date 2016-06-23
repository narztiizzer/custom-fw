package plnr.custom.framework.api;

import android.net.Uri;

import java.net.HttpURLConnection;

import plnr.custom.framework.api.intf.IWebAPINotification;
import plnr.custom.framework.api.models.Add;
import plnr.custom.framework.core.base.model.BaseDateTime;
import plnr.custom.framework.core.converter.UnicodeConverter;

/**
 * Created by nattapongr on 5/13/15.
 */
public class WebAPIAddObject {
    private static WebAPIAddObject ourInstance = new WebAPIAddObject();
    private Add upload;
    private String url;

    private WebAPIAddObject() {
    }

    public static WebAPIAddObject getInstance() {
        if (ourInstance == null)
            ourInstance = new WebAPIAddObject();
        return ourInstance;
    }

    public synchronized void add(String url, String json, String target, String module, String token, final IWebAPINotification callback, final Class Class) {
        this.url = url;
        json = UnicodeConverter.unicodeEscapedString(json);
        upload = new Add();
        upload.updatedate = new BaseDateTime();
        upload.setToken(token);
        upload.setData(json);
        upload.setModule(module);
        upload.setTarget(target);
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
//        HttpResponse response = null;
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
//                Config.RequestDefaultTimeout);
//        HttpConnectionParams.setSoTimeout(httpclient.getParams(), Config.RequestDefaultTimeout);
//        HttpPost post = new HttpPost(this.url);
//        String responseString = "";
//        try {
//            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//            pairs.add(new BasicNameValuePair(WebAPI.KEY_PARAM_JSON_HTTP_REQUEST, UnicodeConverter.unicodeEscapedString(upload.toJSONString())));
//            post.setEntity(new UrlEncodedFormEntity(pairs));
//            response = httpclient.execute(post);
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            response.getEntity().writeTo(out);
//            out.close();
//            responseString = StringEscapeUtils.unescapeJson(out.toString().trim());
//        } catch (IOException e) {
//            responseString = e.getMessage();
//        }
//
//        responseString = StringEscapeUtils.unescapeJson(responseString);
//
//        if (response != null)
//            new CheckResponseCode().checkStatusAndCallBack(callback, response.getStatusLine().getStatusCode(), responseString, Class);
//        else
//            callback.webAPIFailed("404", "Time Out !!", Class);
//    }

    private void postUpload(final IWebAPINotification callback, Class Class) {

        int responseCode = 0;
        String responseString = "";
        String responseStringUnEscape = "";

        HttpURLConnection conn = WebAPIConnection.createHttpConnection(Class.getSimpleName() , url);

        Uri.Builder builder = new Uri.Builder().appendQueryParameter(WebAPI.KEY_PARAM_JSON_HTTP_REQUEST, UnicodeConverter.unicodeEscapedString(upload.toJSONString()));
        String query = builder.build().getEncodedQuery();

        WebAPIConnection.postQueryParameterString(conn , query);

        responseCode = WebAPIConnection.getConnectionResponseCode(Class.getSimpleName() , conn);
        responseString = WebAPIConnection.getConnectionResponseString(Class.getSimpleName() , conn);

        responseStringUnEscape = WebAPIConnection.unEscapeResponseStringWithTrim(responseString);
        responseStringUnEscape = WebAPIConnection.unEscapeResponseString(responseStringUnEscape);

        if (responseStringUnEscape != null)
            new CheckResponseCode().checkStatusAndCallBack(callback, responseCode, responseStringUnEscape, Class);
        else
            callback.webAPIFailed("404", "Time Out !!", Class);
    }
}
