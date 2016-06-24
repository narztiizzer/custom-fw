package plnr.custom.framework.authentication.models;

import java.util.ArrayList;

import plnr.custom.framework.authentication.enumulation.URLService;
import plnr.custom.framework.core.base.annotation.JSONVariable;
import plnr.custom.framework.core.base.model.BaseArrayList;
import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseListAdapter;
import plnr.custom.framework.core.base.ui.listview.BaseScrollListAdapter;

/**
 * Created by nattapongr on 5/14/15.
 */
public class User extends BaseModel {

    @JSONVariable
    private long u_id;
    @JSONVariable
    private String token;
    @JSONVariable
    private String name;
    @JSONVariable
    private String role;
    @JSONVariable
    private ArrayList allow_module = new ArrayList();
    @JSONVariable
    private ArrayList allow_service = new ArrayList();
    @JSONVariable
    private BaseArrayList<UserServiceURL> webapi = BaseArrayList.Builder(UserServiceURL.class);

    public String getWepAPIForKey(URLService service) {
        switch (service) {
            case URL_ADDED:
                return getURL("ADDED");
            case URL_DELETED:
                return getURL("DELETED");
            case URL_SELECTED:
                return getURL("SELECTED");
            case URL_UPDATED:
                return getURL("UPDATED");
            case URL_IMAGES:
                return getURL("IMAGES");
            default:
                return "";
        }
    }

    private String getURL(String txt) {
        for (int i = 0; i < webapi.size(); i++) {
            UserServiceURL tmpAPI = (UserServiceURL) webapi.get(i);
            if (tmpAPI.getService_key().equalsIgnoreCase(txt))
                return tmpAPI.getService_url();
        }
        return "";
    }

    public long getU_id() {
        return u_id;
    }

    public void setU_id(long u_id) {
        this.u_id = u_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ArrayList getAllow_module() {
        if (allow_module == null)
            allow_module = new ArrayList();
        return allow_module;
    }

    public void setAllow_module(ArrayList allow_module) {
        this.allow_module = allow_module;
    }

    public ArrayList getAllow_service() {
        return allow_service;
    }

    public void setAllow_service(ArrayList allow_service) {
        this.allow_service = allow_service;
    }

    public BaseArrayList<UserServiceURL> getWebapi() {
        return webapi;
    }

    public void setWebapi(BaseArrayList<UserServiceURL> webapi) {
        this.webapi = webapi;
    }

    @Override
    public boolean saveListHandler(BaseListAdapter list, int row, BaseCellDataSource dataSource) {
        return false;
    }

    @Override
    public boolean saveListHandler(BaseScrollListAdapter list, int row, BaseCellDataSource dataSource) {
        return false;
    }

    @Override
    public boolean shouldSaveListHandler(BaseCellDataSource dataSource) {
        return false;
    }

    @Override
    public String getObjectIdentificationRequest() {
        return null;
    }

    @Override
    public String getKeyForObjectIdentificationRequest() {
        return null;
    }
}
