package plnr.custom.framework.api;

import android.net.Uri;

import java.net.HttpURLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import plnr.custom.framework.api.intf.IWebAPINotification;
import plnr.custom.framework.api.models.Add;
import plnr.custom.framework.api.models.Transaction;
import plnr.custom.framework.api.models.TransactionResponse;
import plnr.custom.framework.authentication.CurrentUser;
import plnr.custom.framework.core.base.model.BaseArrayList;
import plnr.custom.framework.core.base.model.BaseDateTime;
import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.converter.UnicodeConverter;
import plnr.custom.framework.logger.LogManager;
import plnr.custom.framework.repository.ObjectFactory;

/**
 * Created by ponlavitlarpeampaisarl on 3/30/15 AD.
 */
public class WebAPI {
    public static final String KEY_PARAM_JSON_HTTP_REQUEST = "json";
    private static WebAPI shareInstance;
    public boolean isForceUploadDone = false;
    private boolean ableToRefresh = true;

    private WebAPI() {

    }

    /**
     * get WebAPI Singleton class
     *
     * @return the share WebAPI object
     */
    public synchronized static WebAPI getInstance() {
        if (shareInstance == null) {
            shareInstance = new WebAPI();
        }
        return shareInstance;
    }


    public void registerWebAPIDatabase() {
        ObjectFactory.register(Transaction.class);
        ObjectFactory.register(TransactionResponse.class);
    }

    public void createNewTransaction(BaseModel objectToSent, String tag, String url, String target, String module, String token) {
        String json = objectToSent.toJSONString();
        Transaction tran = new Transaction();
        tran.setAddedDate(new BaseDateTime());
        tran.setFailedCount(0);
        tran.setSenderToken(CurrentUser.getInstance().getCurrentUser().getToken());
        tran.setTag(tag);
        tran.setJsonMessage(json);
        tran.setTarget(target);
        tran.setModule(module);
        tran.setSenderToken(token);
        tran.setSuccess(false);
        tran.setTargetURL(url);
        tran.updatedate = new BaseDateTime();
        ObjectFactory.insert(Transaction.class).asNewItem(tran);
    }

    public void startAllTransaction() {
        BaseArrayList<?> transactionList = ObjectFactory.select(Transaction.class).getAll();
        for (Object obj : transactionList) {
            Transaction transaction = (Transaction) obj;
            if (transaction.isSuccess()) continue;
            preUpload(transaction);
        }
    }

    public synchronized void forceUpload(Transaction transaction, IWebAPINotification callback) {

        if (preUpload(transaction)) {
            LogManager.getInstance().info("Force send done", this.getClass().getSimpleName(), transaction.getJsonMessage());
            callback.webAPISuccess("", "Force send done", "", callback.getClass());
        } else {
            LogManager.getInstance().info("Force send failed", this.getClass().getSimpleName(), transaction.getJsonMessage());
            callback.webAPISuccess("", "Force send failed", "", callback.getClass());
        }
    }

    private synchronized boolean preUpload(Transaction transaction) {
        LogManager.getInstance().info("Prepare for send Update to server : " + transaction.getTag(), this.getClass().getSimpleName(), transaction.toJSONString());
        String json = UnicodeConverter.unicodeEscapedString(transaction.getJsonMessage());
        Add upload = new Add();
        upload.updatedate = new BaseDateTime();
        upload.setToken(transaction.getSenderToken());
        upload.setData(json);
        upload.setModule(transaction.getModule());
        upload.setTarget(transaction.getTarget());

        return postUpload(transaction, upload, transaction.getTargetURL());
    }
    //////////////////////////////////////////////////////
    // Apache library is obsolete from api 22 and above //
    //////////////////////////////////////////////////////
//    private synchronized boolean postUpload(Transaction transaction, Add upload, String url) {
//        ableToRefresh = false;
//
//        HttpResponse response = null;
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
//                Config.RequestDefaultTimeout);
//        HttpConnectionParams.setSoTimeout(httpclient.getParams(), Config.RequestDefaultTimeout);
//        HttpPost post = new HttpPost(url);
//        String responseString = "";
//        boolean isFailed = false;
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
//            isFailed = true;
//        }
//
//        if (response != null && response.getStatusLine() != null && response.getStatusLine().getStatusCode() != 200) {
//            isFailed = true;
//        }
//
//        transaction.setSuccess(!isFailed);
//        transaction.setFailedCount(transaction.getFailedCount() + (!isFailed ? 0 : 1));
//
//        if (response != null) {
//            TransactionResponse resp = new TransactionResponse();
//            resp.setCode(response.getStatusLine().getStatusCode() + "");
//            resp.setAddedDate(new BaseDateTime());
//            resp.setRef_transaction(transaction);
//            resp.setResponseMessage(responseString);
//            resp.updatedate = new BaseDateTime();
//            ObjectFactory.insert(TransactionResponse.class).asNewItem(resp);
//        }
//        ObjectFactory.update(Transaction.class).asUpdateOrReplaceItem(transaction);
//        LogManager.getInstance().info("Send Update to server : " + transaction.getTag() + " : " + (isFailed ? "Failed" : "Success"), this.getClass().getSimpleName(), transaction.toJSONString());
//
//        LogManager.getInstance().info("server response", this.getClass().getSimpleName(), responseString + "");
//
//        ableToRefresh = true;
//        return !isFailed;
//    }

    private synchronized boolean postUpload(Transaction transaction, Add upload, String url) {
        ableToRefresh = false;
        boolean isFailed = false;

        int responseCode = 0;
        String responseString = "";
        String responseStringUnEscape = "";

        HttpURLConnection conn = WebAPIConnection.createHttpConnection("WEB API" , url);

        Uri.Builder builder = new Uri.Builder().appendQueryParameter(WebAPI.KEY_PARAM_JSON_HTTP_REQUEST, UnicodeConverter.unicodeEscapedString(upload.toJSONString()));
        String query = builder.build().getEncodedQuery();

        WebAPIConnection.postQueryParameterString(conn , query);

        responseCode = WebAPIConnection.getConnectionResponseCode("WEB API" , conn);
        responseString = WebAPIConnection.getConnectionResponseString("WEB API" , conn);
        responseStringUnEscape = WebAPIConnection.unEscapeResponseStringWithTrim(responseString);

        if (responseStringUnEscape != null && responseCode != 200) {
            isFailed = true;
        }

        transaction.setSuccess(!isFailed);
        transaction.setFailedCount(transaction.getFailedCount() + (!isFailed ? 0 : 1));

        if (responseStringUnEscape != null) {
            TransactionResponse resp = new TransactionResponse();
            resp.setCode(responseCode + "");
            resp.setAddedDate(new BaseDateTime());
            resp.setRef_transaction(transaction);
            resp.setResponseMessage(responseString);
            resp.updatedate = new BaseDateTime();
            ObjectFactory.insert(TransactionResponse.class).asNewItem(resp);
        }
        ObjectFactory.update(Transaction.class).asUpdateOrReplaceItem(transaction);
        LogManager.getInstance().info("Send Update to server : " + transaction.getTag() + " : " + (isFailed ? "Failed" : "Success"), this.getClass().getSimpleName(), transaction.toJSONString());

        LogManager.getInstance().info("server response", this.getClass().getSimpleName(), responseString + "");

        ableToRefresh = true;
        return !isFailed;
    }

    public synchronized void startService() {
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    if (ableToRefresh)
                        startAllTransaction();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}
