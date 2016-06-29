package plnr.custom.framework.api;

import android.net.Uri;

import java.net.HttpURLConnection;

import plnr.custom.framework.api.intf.IWebAPINotification;
import plnr.custom.framework.api.models.Update;
import plnr.custom.framework.logger.LogManager;

/**
 * Created by nattapongr on 5/13/15.
 */
public class WebAPIUpdateObject {
    private static WebAPIUpdateObject ourInstance = new WebAPIUpdateObject();
    private Update upload;
    private String url;

    private WebAPIUpdateObject() {
    }

    public static WebAPIUpdateObject getInstance() {
        if (ourInstance == null)
            ourInstance = new WebAPIUpdateObject();
        return ourInstance;
    }

    public synchronized void update(String url, String id, String data, String target, String module, String token, final IWebAPINotification callback, final Class Class) {
        this.url = url;
        upload = new Update();
        upload.setObjectStringId(id);
        upload.setData(data);
        upload.setTarget(target);
        upload.setModule(module);
        upload.setToken(token);
        LogManager.getInstance().info("Update", this.getClass().getSimpleName(), "+++++ Start Update :" + upload.toJSONString());
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
//        new CheckResponseCode().checkStatusAndCallBack(callback, response.getStatusLine().getStatusCode(), responseString, Class);
//    }

    private void postUpload(final IWebAPINotification callback, Class Class) {

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

        new CheckResponseCode().checkStatusAndCallBack(callback, responseCode , responseStringUnEscape, Class);
    }
}
