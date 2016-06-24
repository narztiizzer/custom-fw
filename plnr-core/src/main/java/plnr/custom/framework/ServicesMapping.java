package plnr.custom.framework;

import java.util.HashMap;

/**
 * Created by ponlavitlarpeampaisarl on 2/5/15 AD.
 * This class use for define the request setting for each object that implement IJSONEnable
 */
public abstract class ServicesMapping {

    /**
     * This map use for fetch object for BaseModel.
     */
    public HashMap<Class, String> FETCH_SERVICE_URL;
    /**
     * This map use for fetch object for BaseArrayList.
     */
    public HashMap<Class, String> FETCH_LIST_SERVICE_URL;
    /**
     * This map use for push object for BaseModel.
     */
    public HashMap<Class, String> PUSH_SERVICE_URL;
    /**
     * This map use for push object for BaseArrayList.
     */
    public HashMap<Class, String> PUSH_LIST_SERVICE_URL;

    /**
     * This map use for module checking version
     */
    public HashMap<Class, String> FETCH_MASTER_CHECK_VERSION_URL;

    /**
     * default constructor.
     */
    public ServicesMapping() {
        this.setupFetchMapping();
        this.setupFetchListMapping();
        this.setupPushMapping();
        this.setupPushListMapping();
        this.setupFetchMasterCheckVersion();
    }

    private void setupFetchMasterCheckVersion() {
        FETCH_MASTER_CHECK_VERSION_URL = new HashMap<>();
        setupFetchMasterCheckVersionMapping(FETCH_MASTER_CHECK_VERSION_URL);
    }

    protected abstract void setupFetchMasterCheckVersionMapping(HashMap<Class, String> map);

    /**
     * Use for setup fetch map BaseModel object.
     */
    private void setupFetchListMapping() {
        FETCH_LIST_SERVICE_URL = new HashMap<>();
        setupFetchListMapping(FETCH_LIST_SERVICE_URL);
    }

    protected abstract void setupFetchListMapping(HashMap<Class, String> map);

    /**
     * Use for setup fetch map BaseArrayList object.
     */
    private void setupPushListMapping() {
        PUSH_LIST_SERVICE_URL = new HashMap<>();
        setupPushListMapping(PUSH_LIST_SERVICE_URL);
    }

    protected abstract void setupPushListMapping(HashMap<Class, String> map);

    /**
     * Use for setup push map BaseModel object.
     */
    private void setupPushMapping() {
        PUSH_SERVICE_URL = new HashMap<>();
        setupPushMapping(PUSH_SERVICE_URL);
    }

    protected abstract void setupPushMapping(HashMap<Class, String> map);

    /**
     * Use for setup push map BaseArrayList object.
     */
    private void setupFetchMapping() {
        FETCH_SERVICE_URL = new HashMap<>();
        setupFetchMapping(FETCH_SERVICE_URL);
    }

    protected abstract void setupFetchMapping(HashMap<Class, String> map);
}
