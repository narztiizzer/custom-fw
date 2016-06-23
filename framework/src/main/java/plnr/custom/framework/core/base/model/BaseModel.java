package plnr.custom.framework.core.base.model;

import android.net.Uri;
import android.util.Log;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import plnr.custom.framework.Config;
import plnr.custom.framework.cache.CacheManager;
import plnr.custom.framework.core.BaseApplication;
import plnr.custom.framework.core.base.annotation.ColumnType;
import plnr.custom.framework.core.base.annotation.IQueryObject;
import plnr.custom.framework.core.base.annotation.JSONVariable;
import plnr.custom.framework.core.base.annotation.NullableType;
import plnr.custom.framework.core.base.enumulation.RequestMethod;
import plnr.custom.framework.core.base.enumulation.UpdatePolicy;
import plnr.custom.framework.core.base.exception.NotYetImplementedException;
import plnr.custom.framework.core.base.exception.ServiceNotFoundWithThisObjectException;
import plnr.custom.framework.core.base.exception.UnsupportedClassException;
import plnr.custom.framework.core.base.intf.IJSONEnable;
import plnr.custom.framework.core.base.intf.IRestServiceObject;
import plnr.custom.framework.core.base.intf.IRestServiceObjectDelegate;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseListAdapter;
import plnr.custom.framework.core.base.ui.listview.BaseScrollListAdapter;
import plnr.custom.framework.core.helper.PrimitiveHelper;

/**
 * Created by ponlavitlarpeampaisarl on 2/3/15 AD.
 */

public abstract class BaseModel implements Serializable, IJSONEnable, IRestServiceObject {

    @JSONVariable
    @IQueryObject(
            name = "updatedate",
            type = ColumnType.TEXT,
            nullAble = NullableType.NOT_NULL
    )
    public BaseDateTime updatedate;

    @JSONVariable
    public long unique_id;
    @JSONVariable
    public boolean active_flag = true;

    @JSONVariable
    @IQueryObject(
            name = "_id",
            type = ColumnType.INTEGER,
            nullAble = NullableType.NOT_NULL
    )
    public long _id = -1;
    private boolean isObjectFetched;
    private boolean isObjectFetching;
    private boolean isObjectProcessing;


    public BaseModel() {
        this.isObjectFetched = false;
        this.isObjectFetching = false;
        this.isObjectProcessing = false;
    }

    public BaseModel(String json, UpdatePolicy policy) {
        this();
        this.updateFromJson(json, policy);
    }

    public BaseModel(Map<String, Object> json, UpdatePolicy policy) {
        this();
        this.updateFromInfo(json, policy);
    }

    public String forceStringNotNull(String st) {
        if (st == null || st.trim().trim().equalsIgnoreCase("") || st.trim().equalsIgnoreCase("null"))
            st = Config.DEFAULT_REPLACE_NULL;
        return st.trim();
    }

    public long getObjectId() {
        return this._id;
    }

    protected BaseModel self() {
        return this;
    }

    @Override
    public void updateTimeStamp() {
        this.updatedate = new BaseDateTime();
    }

    @Override
    public BaseDateTime getUpdateDate() {
        return this.updatedate;
    }

    public boolean getIsObjectProcessing() {
        return this.isObjectProcessing;
    }

    public boolean getIsObjectFetching() {
        return this.isObjectFetching;
    }

    public boolean getIsObjectFetched() {
        return this.isObjectFetched;
    }

    @Override
    public String toJSONLocalString() {
        return this.toJSONObject(true).toString().trim();
    }

    @Override
    public String toJSONString() {
        return this.toJSONObject(false).toString().trim();
    }

    /**
     * @param forLocal determine either generate json for local only
     * @return JSONObject that represent the properties in class that define JSONVariable annotation.
     */
    public JSONObject toJSONObject(boolean forLocal) {
        JSONObject root = new JSONObject();
        Field[] allField = null;
        Field[] allChildField = getClass().getDeclaredFields();
        if (getClass().getSuperclass() != null) {
            Field[] allSuperField = getClass().getSuperclass().getDeclaredFields();
            allField = Arrays.copyOf(allChildField, allChildField.length + allSuperField.length);
            System.arraycopy(allSuperField, 0, allField, allChildField.length, allSuperField.length);
        } else {
            allField = allChildField;
        }
        for (Field aField : allField) {
            if (aField.isAnnotationPresent(JSONVariable.class)) {
                try {
                    aField.setAccessible(true);
                    String name = aField.getName();
                    Object value = aField.get(this);
                    JSONVariable aFieldAnnotation = aField.getAnnotation(JSONVariable.class);
                    // Check the caller is for local
                    if (!forLocal && aFieldAnnotation.localOnly()) {
                        continue;
                    }
                    if (value != null) {
                        if (Boolean.TYPE.isAssignableFrom(aField.getType()))
                            root.put(name, ((Boolean) value).booleanValue() ? "1" : "0");
                        else if (aField.getType().isPrimitive() || String.class.isAssignableFrom(value.getClass())) {
                            root.put(name, value.toString().trim());
                        } else if (BaseDateTime.class.isAssignableFrom(value.getClass()))
                            root.put(name, ((BaseDateTime) value).toUnixTimeString());
                        else if (IJSONEnable.class.isAssignableFrom(value.getClass()))
                            root.put(name, ((IJSONEnable) value).toJSONObject(forLocal));
                        else if (aField.getType().equals(Object.class)) {
                            root.put(name, value.toString().trim());
                        } else if (aField.getType().equals(ArrayList.class)) {
                            JSONArray array = new JSONArray();
                            for (int i = 0; i < ((ArrayList) value).size(); i++) {
                                array.put(((ArrayList) value).get(i));
                            }
                            root.put(name, array);
                        } else if (aField.getType().equals(HashMap.class)) {
                            root.put(name, value.toString().trim());
                        } else throw new UnsupportedClassException();
                    }
                    aField.setAccessible(false);
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return root;
    }

    private Field getField(Class classStruct, String fieldName) {
        try {
            return classStruct.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            try {
                return classStruct.getSuperclass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException ex) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Use to replace the current data from input value Map to this object
     *
     * @param info   field value map
     * @param policy update policy
     */
    public void updateFromInfo(Map<String, Object> info, UpdatePolicy policy) {

        for (String key : info.keySet()) {
            try {
                Field aField = getField(getClass(), key);
                if (aField == null) continue;
                aField.setAccessible(true);
                Object value = aField.get(this);
                Object newValue = info.get(key);

                if (newValue != null && !newValue.equals("null"))
                switch (policy) {
                    case ForceUpdate: {
                        if (aField.getType().equals(Boolean.TYPE))
                            aField.set(this, PrimitiveHelper.boolFromString(newValue.toString().trim()));
                        else if (aField.getType().isPrimitive()) {
                            String tmpValue = newValue.toString().trim();
                            if (tmpValue.equalsIgnoreCase("null")) tmpValue = "-1";
                            if (aField.getType() == Integer.TYPE)
                                aField.set(this, Integer.parseInt(tmpValue));
                            else if (aField.getType() == Float.TYPE)
                                aField.set(this, Float.parseFloat(tmpValue));
                            else if (aField.getType() == Double.TYPE)
                                aField.set(this, Double.parseDouble(tmpValue));
                            else if (aField.getType() == Long.TYPE)
                                aField.set(this, Long.parseLong(tmpValue));
                            else throw new UnsupportedClassException();
                        } else if (aField.getType().equals(HashMap.class)) {
                            aField.set(this, newValue);
                        } else if (aField.getType().equals(Object.class)) {
                            aField.set(this, newValue);
                        } else if (aField.getType().equals(ArrayList.class)) {
                            aField.set(this, newValue);
                        } else if (String.class.isAssignableFrom(aField.getType()))
                            aField.set(this, newValue.toString().trim());
                        else if (BaseDateTime.class.isAssignableFrom(aField.getType())) {
                            long uTime = Long.parseLong(newValue.toString().trim().replace("", ""));
                            if (newValue.toString().trim().length() > 10)
                                uTime = Long.parseLong(newValue.toString().trim().substring(0, 10));
                            aField.set(this, new BaseDateTime(uTime));
                        } else if (IJSONEnable.class.isAssignableFrom(aField.getType())) {
                            if (BaseArrayList.class.isAssignableFrom(aField.getType())) {
                                if (newValue != null && !newValue.toString().trim().equalsIgnoreCase("null")) {
                                    ((BaseArrayList) value).updateFromArray((List) newValue, policy);
                                    aField.set(this, value);
                                }
                            } else if (BaseModel.class.isAssignableFrom(aField.getType())) {
                                if (value == null)
                                    try {
                                        value = aField.getType().newInstance();
                                    } catch (InstantiationException e) {
                                        e.printStackTrace();
                                    }
                                if (newValue != null && !newValue.toString().equalsIgnoreCase("null")) {
                                    ((BaseModel) value).updateFromInfo((Map<String, Object>) newValue, policy);
                                    aField.set(this, value);
                                }
                            }
                        } else throw new UnsupportedClassException();
                    }
                    break;
                    case UseFromCacheIfAvailable: {
                        if (aField.getType().equals(Boolean.TYPE))
                            aField.set(this, PrimitiveHelper.boolFromString(newValue.toString().trim()));
                        else if (aField.getType().isPrimitive()) {
                            String tmpValue = newValue.toString().trim();
                            if (tmpValue.equalsIgnoreCase("null")) tmpValue = "-1";
                            if (aField.getType() == Integer.TYPE)
                                aField.set(this, Integer.parseInt(tmpValue));
                            else if (aField.getType() == Float.TYPE)
                                aField.set(this, Float.parseFloat(tmpValue));
                            else if (aField.getType() == Double.TYPE)
                                aField.set(this, Double.parseDouble(tmpValue));
                            else if (aField.getType() == Long.TYPE)
                                aField.set(this, Long.parseLong(tmpValue));
                            else throw new UnsupportedClassException();
                        } else if (aField.getType().equals(HashMap.class)) {
                            aField.set(this, newValue);
                        } else if (aField.getType().equals(Object.class)) {
                            aField.set(this, newValue);
                        } else if (aField.getType().equals(ArrayList.class)) {
                            aField.set(this, newValue);
                        } else if (String.class.isAssignableFrom(aField.getType()))
                            aField.set(this, newValue.toString().trim());
                        else if (BaseDateTime.class.isAssignableFrom(aField.getType())) {
                            long uTime = Long.parseLong(newValue.toString().trim().replace("", ""));
                            if (newValue.toString().trim().length() > 10)
                                uTime = Long.parseLong(newValue.toString().trim().substring(0, 10));
                            aField.set(this, new BaseDateTime(uTime));
                        } else if (IJSONEnable.class.isAssignableFrom(aField.getType())) {
                            if (BaseArrayList.class.isAssignableFrom(aField.getType())) {
                                if (newValue != null && !newValue.toString().trim().equalsIgnoreCase("null")) {
                                    ((BaseArrayList) value).updateFromArray((List) newValue, policy);
                                    aField.set(this, value);
                                }
                            } else if (BaseModel.class.isAssignableFrom(aField.getType())) {
                                if (value == null)
                                    try {
                                        value = aField.getType().newInstance();
                                    } catch (InstantiationException e) {
                                        e.printStackTrace();
                                    }
                                ((BaseModel) value).updateFromInfo((Map<String, Object>) newValue, policy);
                                aField.set(this, value);
                            }
                        } else throw new UnsupportedClassException();
                    }
                    break;
                    case UseNewest: {
                        if (aField.getType().equals(Boolean.TYPE))
                            aField.set(this, PrimitiveHelper.boolFromString(newValue.toString().trim()));
                        else if (aField.getType().isPrimitive()) {
                            String tmpValue = newValue.toString().trim();
                            if (tmpValue.equalsIgnoreCase("null")) tmpValue = "-1";
                            if (aField.getType() == Integer.TYPE)
                                aField.set(this, Integer.parseInt(tmpValue));
                            else if (aField.getType() == Float.TYPE)
                                aField.set(this, Float.parseFloat(tmpValue));
                            else if (aField.getType() == Double.TYPE)
                                aField.set(this, Double.parseDouble(tmpValue));
                            else if (aField.getType() == Long.TYPE)
                                aField.set(this, Long.parseLong(tmpValue));
                            else throw new UnsupportedClassException();
                        } else if (aField.getType().equals(HashMap.class)) {
                            aField.set(this, newValue);
                        } else if (aField.getType().equals(ArrayList.class)) {
                            aField.set(this, newValue);
                        } else if (aField.getType().equals(Object.class)) {
                            aField.set(this, newValue);
                        } else if (String.class.isAssignableFrom(aField.getType()))
                            aField.set(this, newValue.toString().trim());
                        else if (BaseDateTime.class.isAssignableFrom(aField.getType())) {
                            long uTime = Long.parseLong(newValue.toString().trim().replace("", ""));
                            if (newValue.toString().trim().length() > 10)
                                uTime = Long.parseLong(newValue.toString().trim().substring(0, 10));
                            aField.set(this, new BaseDateTime(uTime));
                        } else if (IJSONEnable.class.isAssignableFrom(aField.getType())) {
                            if (BaseArrayList.class.isAssignableFrom(aField.getType())) {
                                if (newValue != null && !newValue.toString().trim().equalsIgnoreCase("null")) {
                                    ((BaseArrayList) value).updateFromArray((List) newValue, policy);
                                    aField.set(this, value);
                                }
                            } else if (BaseModel.class.isAssignableFrom(aField.getType())) {
                                if (value == null)
                                    try {
                                        value = aField.getType().newInstance();
                                    } catch (InstantiationException e) {
                                        e.printStackTrace();
                                    }
                                ((BaseModel) value).updateFromInfo((Map<String, Object>) newValue, policy);
                                aField.set(this, value);
                            }
                        } else throw new UnsupportedClassException();
                    }
                    case MergeToNewest: {
                        throw new NotYetImplementedException();
                    }
                }
                aField.setAccessible(false);
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Update value from json string to this object with policy option.
     *
     * @param json   The string represent the class.
     * @param policy
     */
    public void updateFromJson(String json, UpdatePolicy policy) {
        JSONParser parser = JsonParserFactory.getInstance().newJsonParser();
        Map<String, Object> jsonData = parser.parseJson(json);
        if (jsonData.containsKey(Config.RESPONSE_ENTRIES))
            updateFromInfo((Map<String, Object>) jsonData.get(Config.RESPONSE_ENTRIES), policy);
        else
            updateFromInfo(jsonData, policy);
    }

    /**
     * fetch the data from the internet using mapping and id
     *
     * @param method
     * @param handler
     */

    //////////////////////////////////////////////////////
    // Apache library is obsolete from api 22 and above //
    //////////////////////////////////////////////////////
//    @Override
//    public void fetch(RequestMethod method, final IRestServiceObjectDelegate handler, final UpdatePolicy policy) {
//        if (isObjectFetching) return;
//        isObjectFetching = true;
//        if (RequestMethod.Asynchronous == method) {
//            Thread requestThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    HttpResponse response = null;
//                    try {
//                        String actionUrl = BaseApplication.getLibContext().getServicesMapping().FETCH_SERVICE_URL
//                                .get(self().getClass());
//
//                        if (actionUrl == null)
//                            throw new ServiceNotFoundWithThisObjectException();
//                        // if policy?  getFromCache(key) != null
//                        String responseString = null;
//
//                        if (policy == UpdatePolicy.UseFromCacheIfAvailable) {
//                            try {
//                                responseString = CacheManager.getInstance().getCacheStringForKey(actionUrl);
//                            } catch (Exception e) {
//                                responseString = null;
//                            }
//                        }
//                        if (responseString == null) {
//                            HttpClient httpclient = new DefaultHttpClient();
//                            HttpConnectionParams.setConnectionTimeout(httpclient.getParams(),
//                                    Config.RequestDefaultTimeout);
//                            HttpConnectionParams.setSoTimeout(getFetchParams(httpclient), Config.RequestDefaultTimeout);
//                            HttpPost post = new HttpPost(actionUrl);
//                            if (getKeyForObjectIdentificationRequest() != null) {
//                                if (getObjectIdentificationRequest() == null)
//                                    throw new NullPointerException();
//                                List<NameValuePair> params = new ArrayList<>();
//                                params.add(new BasicNameValuePair(
//                                        getKeyForObjectIdentificationRequest(),
//                                        getObjectIdentificationRequest()));
//                                post.setEntity(new UrlEncodedFormEntity(params));
//                            }
//                            response = httpclient.execute(post);
//                            ByteArrayOutputStream out = new ByteArrayOutputStream();
//                            response.getEntity().writeTo(out);
//                            out.close();
//                            responseString = out.toString().trim();
//                            CacheManager.getInstance().setCacheStringForKey(actionUrl, responseString, 100000);
//                        }
//
//                        if (handler != null) {
//                            if (handler.willMapDataFromString(responseString)) {
//                                try {
//                                    updateFromJson(responseString, policy);
//                                    handler.didMapDataFromStringSuccess(self());
//                                    isObjectFetched = true;
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
//                        isObjectFetching = false;
//                    }
//                }
//            });
//            requestThread.start();
//        } else if (RequestMethod.Synchronous == method) {
//            throw new NotYetImplementedException();
//        } else throw new UnsupportedClassException();
//    }

    /**
     * fetch the data from the internet using mapping and id
     *
     * @param method
     * @param handler
     */
    @Override
    public void fetch(RequestMethod method, final IRestServiceObjectDelegate handler, final UpdatePolicy policy) {
        if (isObjectFetching) return;
        isObjectFetching = true;
        if (RequestMethod.Asynchronous == method) {
            Thread requestThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    int responseCode = 0;
                    String responseString = null;
                    BufferedReader inputReader = null;

                    try {

                        String actionUrl = BaseApplication.getLibContext().getServicesMapping().FETCH_SERVICE_URL
                                .get(self().getClass());

                        if (actionUrl == null)
                            throw new ServiceNotFoundWithThisObjectException();
                        // if policy?  getFromCache(key) != null
                        responseString = null;

                        if (policy == UpdatePolicy.UseFromCacheIfAvailable) {
                            try {
                                responseString = CacheManager.getInstance().getCacheStringForKey(actionUrl);
                            } catch (Exception e) {
                                responseString = null;
                            }
                        }

                        if (responseString == null) {
                            URL requestUrl = new URL(actionUrl);
                            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
                            conn.setReadTimeout(Config.RequestDefaultTimeout);
                            conn.setConnectTimeout(Config.RequestDefaultTimeout);
                            conn.setRequestMethod("POST");
                            conn.setDoInput(true);
                            conn.setDoOutput(true);

                            Uri.Builder builder = new Uri.Builder();
                            if (getKeyForObjectIdentificationRequest() != null) {
                                if (getObjectIdentificationRequest() == null)
                                    throw new NullPointerException();
                                builder.appendQueryParameter(getKeyForObjectIdentificationRequest(), getObjectIdentificationRequest());
                            }
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
                            CacheManager.getInstance().setCacheStringForKey(actionUrl, responseString, 100000);

                            if (handler != null) {
                                if (handler.willMapDataFromString(responseString)) {
                                    try {
                                        updateFromJson(responseString, policy);
                                        handler.didMapDataFromStringSuccess(self());
                                        isObjectFetched = true;
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
                        }
                        Log.d("BASE MODEL", "FETCH FUNCTION SUCCESS");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        responseString = e.getMessage();
                        if (handler != null)
                            handler.didFetchFailedWithError(responseString != null ? responseString : e.getMessage() , responseCode);
                        Log.e("BASE MODEL", "FETCH FUNCTION FAIL");
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                        responseString = e.getMessage();
                        if (handler != null)
                            handler.didFetchFailedWithError(responseString != null ? responseString : e.getMessage() , responseCode);
                        Log.e("BASE MODEL", "FETCH FUNCTION FAIL");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        responseString = e.getMessage();
                        if (handler != null)
                            handler.didFetchFailedWithError(responseString != null ? responseString : e.getMessage() , responseCode);
                        Log.e("BASE MODEL", "FETCH FUNCTION FAIL");
                    } catch (IOException e) {
                        e.printStackTrace();
                        responseString = e.getMessage();
                        if (handler != null)
                            handler.didFetchFailedWithError(responseString != null ? responseString : e.getMessage() , responseCode);
                        Log.e("BASE MODEL", "FETCH FUNCTION FAIL");
                    } finally {
                        isObjectFetching = false;
                    }
                }
            });
            requestThread.start();
        } else if (RequestMethod.Synchronous == method) {
            throw new NotYetImplementedException();
        } else throw new UnsupportedClassException();
    }

//    public HttpParams getFetchParams(HttpClient httpclient) {
//        return httpclient.getParams();
//    }

    @Override
    public void fetch(RequestMethod method, UpdatePolicy policy) {
        fetch(method, null, policy);
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
        throw new NotYetImplementedException();
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

    /**
     * This function is use for save the data from cell data source to model object
     *
     * @param dataSource data of the cell
     * @return is the save process success or not
     */
    public abstract boolean saveListHandler(BaseListAdapter list, int row, BaseCellDataSource dataSource);

    public abstract boolean saveListHandler(BaseScrollListAdapter list, int row, BaseCellDataSource dataSource);
    /**
     * This function is for validation before the save process
     *
     * @param dataSource data of the cell
     * @return is this save process should start or not
     */
    public abstract boolean shouldSaveListHandler(BaseCellDataSource dataSource);

    /**
     * This function will provide the identification for the
     * object that use for fetch from the server
     * <p/>
     * e.g.
     * The mapping service point to www.example.com
     * getKeyForObjectIdentificationRequest = id
     * getObjectIdentificationRequest = 3
     * the final url is
     * www.example.com?id=3
     * for each object
     *
     * @return The identification of this object, null if there is no fetch service support
     */
    public abstract String getObjectIdentificationRequest();

    /**
     * This function will provide the key for identification
     * that will send to server to fetch object
     * <p/>
     * e.g.
     * The mapping service point to www.example.com
     * getKeyForObjectIdentificationRequest = id
     * getObjectIdentificationRequest = 3
     * the final url is
     * www.example.com?id=3
     * for each object
     *
     * @return The key for identification, null if there is no fetch service support
     */
    public abstract String getKeyForObjectIdentificationRequest();

}
