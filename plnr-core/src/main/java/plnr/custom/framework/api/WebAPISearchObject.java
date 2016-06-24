package plnr.custom.framework.api;

import android.net.Uri;
import android.util.Log;

import java.net.HttpURLConnection;

import plnr.custom.framework.Config;
import plnr.custom.framework.api.intf.IWebAPINotification;
import plnr.custom.framework.api.models.Search;
import plnr.custom.framework.core.base.model.BaseDateTime;
import plnr.custom.framework.logger.LogManager;


/**
 * Created by nattapongr on 5/13/15.
 */
public class WebAPISearchObject {

    private static WebAPISearchObject ourInstance = new WebAPISearchObject();

    private WebAPISearchObject() {
    }

    public synchronized static WebAPISearchObject getInstance() {
        if (ourInstance == null)
            ourInstance = new WebAPISearchObject();
        return ourInstance;
    }

    public void search(final String url, BaseDateTime lastUpdate, String keyword, String target, String module, int limit, int offset, String token, final IWebAPINotification callback, final Class Class) {
        search(url, lastUpdate, keyword, target, module, limit, offset, token, callback, Class, 10);
    }

    public void search(final String url, BaseDateTime lastUpdate, String keyword, final String target, final String module, int limit, int offset, String token, final IWebAPINotification callback, final Class Class, final long numberOfDayCache) {
        final Search upload = new Search();
        upload.updatedate = lastUpdate;
        upload.setKeyword(keyword);
        upload.setTarget(target);
        upload.setModule(module);
        upload.setLimit(limit);
        upload.setOffset(offset);
        upload.setToken(token);

        Log.d("SEARCHING OBJECT", "SEARCHING " + Class.getName());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(module + " : " + target, upload.toJSONString() + "");
                    postUpload(callback, Class, url, upload, Config.DAY * numberOfDayCache);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    //////////////////////////////////////////////////////
    // Apache library is obsolete from api 22 and above //
    //////////////////////////////////////////////////////
//    private synchronized void postUpload(final IWebAPINotification callback, Class Class, String url, Search upload, long numberOfDayCache) {
//        HttpResponse response = null;
//        String responseString = "";
//
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
//                Config.RequestDefaultTimeout);
//        HttpConnectionParams.setSoTimeout(httpclient.getParams(), Config.RequestDefaultTimeout);
//        HttpPost post = new HttpPost(url);
//
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
//        if (response != null) {
//            LogManager.getInstance().info("Fetched : " + Class.getSimpleName(), this.getClass().getSimpleName(), responseString + "");
//            new CheckResponseCode().checkStatusAndCallBack(callback, response.getStatusLine().getStatusCode(), responseString, Class);
//        }
//    }

    private synchronized void postUpload(final IWebAPINotification callback, Class Class, String url, Search upload, long numberOfDayCache) {

        int responseCode = 0;
        String responseString = "";

        HttpURLConnection conn = WebAPIConnection.createHttpConnection(Class.getSimpleName() , url);

        Uri.Builder builder = new Uri.Builder().appendQueryParameter(WebAPI.KEY_PARAM_JSON_HTTP_REQUEST, upload.toJSONString());
        String query = builder.build().getEncodedQuery();

        WebAPIConnection.postQueryParameterString(conn , query);

        responseCode = WebAPIConnection.getConnectionResponseCode(Class.getSimpleName() , conn);
        responseString = WebAPIConnection.getConnectionResponseString(Class.getSimpleName() , conn);

        if (responseCode != 0) {
            LogManager.getInstance().info("Fetched : " + Class.getSimpleName(), this.getClass().getSimpleName(), responseString + "");
            new CheckResponseCode().checkStatusAndCallBack(callback, responseCode , responseString, Class);
        }
    }
}