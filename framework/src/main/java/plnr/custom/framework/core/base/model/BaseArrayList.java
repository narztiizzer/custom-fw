package plnr.custom.framework.core.base.model;

import android.net.Uri;
import android.util.Log;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import plnr.custom.framework.Config;
import plnr.custom.framework.cache.CacheManager;
import plnr.custom.framework.core.BaseApplication;
import plnr.custom.framework.core.base.enumulation.RequestMethod;
import plnr.custom.framework.core.base.enumulation.UpdatePolicy;
import plnr.custom.framework.core.base.exception.NotYetImplementedException;
import plnr.custom.framework.core.base.exception.ServiceNotFoundWithThisObjectException;
import plnr.custom.framework.core.base.exception.UnsupportedClassException;
import plnr.custom.framework.core.base.intf.IJSONEnable;
import plnr.custom.framework.core.base.intf.IRestServiceObject;
import plnr.custom.framework.core.base.intf.IRestServiceObjectDelegate;

/**
 * Created by ponlavitlarpeampaisarl on 2/3/15 AD.
 */
public class BaseArrayList<T> extends ArrayList implements IJSONEnable, IRestServiceObject, IRestServiceObjectDelegate {
    private Class<T> genericType;
    private boolean isListFetching;
    private BaseDateTime updatedate;
    private String target;

    public BaseArrayList() {
        throw new UnsupportedOperationException();
    }

    /**
     * Constructor is not available please use the builder class instead
     */
    protected BaseArrayList(Class<T> c) {
        this.genericType = c;
    }

    /**
     * Object builder method
     *
     * @return New BaseArrayList object with BaseModel generic type.
     */
    public static BaseArrayList Builder(Class asClass) {
        return new BaseArrayList(asClass);
    }

    @Override
    public void updateTimeStamp() {
        this.updatedate = new BaseDateTime();
    }

    @Override
    public BaseDateTime getUpdateDate() {
        if(this.updatedate == null)
            this.updatedate = new BaseDateTime(0);
        return this.updatedate;
    }

    public boolean getIsListFetching() {
        return this.isListFetching;
    }

    public BaseArrayList<?> self() {
        return this;
    }

    public Class<T> getGenericType() {
        return genericType;
    }

    @Override
    public String toJSONString() {
        return toJSONObject(false).toString().trim().trim();
    }

    @Override
    public String toJSONLocalString() {
        return toJSONObject(true).toString().trim();
    }

    @Override
    public JSONArray toJSONObject(boolean forLocal) {
        JSONArray ary = new JSONArray();
        for (int i = 0; i < size(); i++) {
            Object objItem = get(i);
            if (objItem instanceof BaseModel) {
                BaseModel item = (BaseModel) objItem;
                ary.put(item.toJSONObject(forLocal));
            } else
                throw new UnsupportedClassException();
        }
        return ary;
    }

    public void updateFromJson(String json, UpdatePolicy policy) {

            JSONParser parser = JsonParserFactory.getInstance().newJsonParser();
            Map jsonData = parser.parseJson(json);
            if (jsonData.containsKey(Config.RESPONSE_ENTRIES)) {
                if (jsonData.get(Config.RESPONSE_ENTRIES) != null && !jsonData.get(Config.RESPONSE_ENTRIES).equals("null"))
                    updateFromArray((List) jsonData.get(Config.RESPONSE_ENTRIES), policy);
                else clear();
            } else {
                if (!jsonData.isEmpty())
                    updateFromArray((List) jsonData, policy);
                else clear();
            }

    }

    public void updateFromArray(List list, UpdatePolicy policy) {
        switch (policy) {
            case ForceUpdate:
                clear();
                break;
            case UseFromCacheIfAvailable:
                clear();
                break;
            case UseNewest:
                // Check if JSON from server have a new activity or deleted old activity
                if (list.size() <= 0)
                    this.clear();
                else if (this.size() >= 0)
                    for (int i = 0; i < this.size(); i++) {
                        boolean isNoHave = true;
                        for (int j = 0; j < list.size(); j++) {
                            if (Long.parseLong(((HashMap) list.get(j)).get("unique_id").toString().trim()) == ((BaseModel) this.get(i)).unique_id) {
                                isNoHave = false;
                            }
                        }
                        if (isNoHave)
                            this.remove(i);
                    }
                break;
            case MergeToNewest:
                break;
            default:
                break;
        }

        for (int i = 0; i < list.size(); i++) {
            Object item = list.get(i);
            if (UpdatePolicy.ForceUpdate == policy || UpdatePolicy.UseFromCacheIfAvailable == policy) {
                try {
                    BaseModel base = (BaseModel) getGenericType().newInstance();
                    if (item instanceof Map) {
                        base.updateFromInfo((Map<String, Object>) item, policy);
                        add(base);
                    } else {
                        throw new UnsupportedOperationException();
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (UpdatePolicy.MergeToNewest == policy)
                throw new NotYetImplementedException();
            else if (UpdatePolicy.UseNewest == policy) {
                try {
                    BaseModel base = (BaseModel) getGenericType().newInstance();
                    if (item instanceof Map) {
                        // loop all old data
                        boolean isHave = false;
                        for (int thisCount = 0; thisCount < this.size(); thisCount++) {
                            // Check if 'id' in this index(old data) is equal to fetching list(new data)
                            // if true, Check timestamp
                            if (((HashMap) list.get(i)).get("unique_id") != null) {
                                if (Long.parseLong(((HashMap) list.get(i)).get("unique_id").toString().trim()) == ((BaseModel) this.get(thisCount)).unique_id) {
                                    // Check if timestamp in list of fetching is updated(new)
                                    // if true, Update data
                                    // if false, do nothing
                                    if (((BaseModel) this.get(thisCount)).updatedate.getTime() < Long.parseLong(((HashMap) list.get(i)).get("updatedate").toString().trim().replace("", ""))) {
                                        base.updateFromInfo((Map<String, Object>) item, UpdatePolicy.ForceUpdate);
                                        set(thisCount, base);
                                    }
                                    isHave = true;
                                }
                            }
                        }
                        if (!isHave) {
                            //Fetching list is a new data (Add to list)
                            base.updateFromInfo((Map<String, Object>) item, UpdatePolicy.ForceUpdate);
                            add(base);
                        }

                    } else throw new UnsupportedOperationException();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }

    }


    //////////////////////////////////////////////////////
    // Apache library is obsolete from api 22 and above //
    //////////////////////////////////////////////////////
//    @Override
//    public void fetch(RequestMethod method, final IRestServiceObjectDelegate handler, final UpdatePolicy policy) {
//        if (isListFetching) return;
//        isListFetching = true;
//        if (RequestMethod.Asynchronous == method) {
//            Thread requestThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    HttpResponse response = null;
//                    try {
//                        String actionUrl = BaseApplication.getLibContext().getServicesMapping().FETCH_LIST_SERVICE_URL
//                                .get(self().getGenericType());
//                        if (actionUrl == null)
//                            throw new ServiceNotFoundWithThisObjectException();
//
//                        String responseString = null;
//                        if (policy == UpdatePolicy.UseFromCacheIfAvailable) {
//                            try {
//                                responseString = CacheManager.getInstance().getCacheStringForKey(actionUrl);
//                            } catch (Exception e) {
//                                responseString = null;
//                            }
//
//                        }
//                        if (responseString == null) {
//                            HttpClient httpclient = new DefaultHttpClient();
//                            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
//                                    Config.RequestDefaultTimeout);
//                            HttpConnectionParams.setSoTimeout(httpclient.getParams(), 14000);
//                            HttpPost post = new HttpPost(actionUrl);
//                            response = httpclient.execute(post);
//                            ByteArrayOutputStream out = new ByteArrayOutputStream();
//                            response.getEntity().writeTo(out);
//                            out.close();
//                            responseString = out.toString().trim();
//
//                            // cache responseString
//                            CacheManager.getInstance().setCacheStringForKey(actionUrl, responseString, 100000);
//                        }
//                        if (handler != null) {
//                            if (handler.willMapDataFromString(responseString)) {
//                                try {
//                                    updateFromJson(responseString, policy);
//                                    handler.didMapDataFromStringSuccess(self());
//                                } catch (Exception e) {
//                                    handler.didMapDataFromStringFailedWithError(e.getMessage());
//                                }
//                            }
//                        } else {
//                            try {
//                                updateFromJson(responseString, policy);
//                            } catch (Exception e) {
//                                throw e;
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        if (handler != null)
//                            handler.didFetchFailedWithError(
//                                    response != null ? response.toString().trim() : e.getMessage(),
//                                    response != null ? response.getStatusLine().getStatusCode() : 0);
//                    } finally {
//                        isListFetching = false;
//                    }
//                }
//            });
//            requestThread.start();
//        } else if (RequestMethod.Synchronous == method) {
//            throw new NotYetImplementedException();
//        } else throw new UnsupportedClassException();
//    }

    @Override
    public void fetch(RequestMethod method, final IRestServiceObjectDelegate handler, final UpdatePolicy policy) {
        if (isListFetching) return;
        isListFetching = true;
        if (RequestMethod.Asynchronous == method) {
            Thread requestThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    int responseCode = 0;
                    String responseString = null;
                    BufferedReader inputReader = null;

                    String actionUrl = BaseApplication.getLibContext().getServicesMapping().FETCH_LIST_SERVICE_URL
                            .get(self().getGenericType());
                    if (actionUrl == null)
                        throw new ServiceNotFoundWithThisObjectException();

                    if (policy == UpdatePolicy.UseFromCacheIfAvailable) {
                        try {
                            responseString = CacheManager.getInstance().getCacheStringForKey(actionUrl);
                        } catch (Exception e) {
                            responseString = null;
                        }

                    }

                    if (responseString == null) {
                        try {
                            URL requestUrl = new URL(actionUrl);
                            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
                            conn.setReadTimeout(Config.RequestDefaultTimeout);
                            conn.setConnectTimeout(Config.RequestDefaultTimeout);
                            conn.setRequestMethod("POST");
                            conn.setDoInput(true);
                            conn.setDoOutput(true);

                            Uri.Builder builder = new Uri.Builder();
                            String query = builder.build().getEncodedQuery();

                            OutputStream outputReader = conn.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputReader, "UTF-8"));
                            writer.write(query);
                            writer.flush();
                            writer.close();

                            responseCode = conn.getResponseCode();

                            inputReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                            String inputLine;
                            StringBuffer response = new StringBuffer();

                            while ((inputLine = inputReader.readLine()) != null) {
                                response.append(inputLine);
                            }

                            inputReader.close();
                            outputReader.close();
                            responseString = response.toString().trim();

                            // cache responseString
                            CacheManager.getInstance().setCacheStringForKey(actionUrl, responseString, 100000);

                            if (handler != null) {
                                if (handler.willMapDataFromString(responseString)) {
                                    try {
                                        updateFromJson(responseString, policy);
                                        handler.didMapDataFromStringSuccess(self());
                                    } catch (Exception e) {
                                        handler.didMapDataFromStringFailedWithError(e.getMessage());
                                    }
                                }
                            } else {
                                try {
                                    updateFromJson(responseString, policy);
                                } catch (Exception e) {
                                    throw e;
                                }
                            }

                            Log.d("BASE ARRAYLIST", "FETCH FUNCTION SUCCESS");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            responseString = e.getMessage();
                            if (handler != null)
                                handler.didFetchFailedWithError(responseString != null ? responseString : e.getMessage() , responseCode);
                            Log.e("BASE ARRAYLIST", "FETCH FUNCTION FAIL");
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                            responseString = e.getMessage();
                            if (handler != null)
                                handler.didFetchFailedWithError(responseString != null ? responseString : e.getMessage() , responseCode);
                            Log.e("BASE ARRAYLIST", "FETCH FUNCTION FAIL");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                            responseString = e.getMessage();
                            if (handler != null)
                                handler.didFetchFailedWithError(responseString != null ? responseString : e.getMessage() , responseCode);
                            Log.e("BASE ARRAYLIST", "FETCH FUNCTION FAIL");
                        } catch (IOException e) {
                            e.printStackTrace();
                            responseString = e.getMessage();
                            if (handler != null)
                                handler.didFetchFailedWithError(responseString != null ? responseString : e.getMessage() , responseCode);
                            Log.e("BASE ARRAYLIST", "FETCH FUNCTION FAIL");
                        } finally {
                            isListFetching = false;
                        }
                    }
                }
            });
            requestThread.start();
        } else if (RequestMethod.Synchronous == method) {
            throw new NotYetImplementedException();
        } else throw new UnsupportedClassException();
    }

    @Override
    public void fetch(RequestMethod method, UpdatePolicy policy) {
        fetch(method, null);
    }

    @Override
    public void fetch(IRestServiceObjectDelegate handler, UpdatePolicy policy) {
        fetch(RequestMethod.Asynchronous, handler, policy);
    }

    @Override
    public void fetch(UpdatePolicy policy) {
        fetch(RequestMethod.Asynchronous, null, policy);
    }

    @Override
    public void push(RequestMethod method, IRestServiceObjectDelegate handler) {
        if (method == RequestMethod.Synchronous) {
            throw new NotYetImplementedException();
        } else {

        }
    }

    @Override
    public void push(RequestMethod method) {
        push(method, null);
    }

    @Override
    public void push(IRestServiceObjectDelegate handler) {
        push(RequestMethod.Asynchronous, handler);
    }

    @Override
    public void push() {
        push(RequestMethod.Asynchronous, null);
    }

    @Override
    public boolean willMapDataFromString(String stringToMap) {
        return true;
    }

    @Override
    public void didMapDataFromStringSuccess(IJSONEnable object) {

    }

    @Override
    public void didMapDataFromStringFailedWithError(String error) {

    }

    @Override
    public void didFetchFailedWithError(String error, int errorCode) {

    }

    @Override
    public void didPushSuccess() {

    }

    @Override
    public void didPushFailedWithError(String error, int errorCode) {

    }

}
