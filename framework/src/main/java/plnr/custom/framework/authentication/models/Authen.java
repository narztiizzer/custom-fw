package plnr.custom.framework.authentication.models;

import plnr.custom.framework.core.base.annotation.JSONVariable;
import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseListAdapter;
import plnr.custom.framework.core.base.ui.listview.BaseScrollListAdapter;

/**
 * Created by nattapongr on 5/14/15.
 */
public class Authen extends BaseModel {

    @JSONVariable
    private String tk_uid;
    @JSONVariable
    private String tk_device_name;
    @JSONVariable
    private String tk_mac_address;
    @JSONVariable
    private String tk_device_model;
    @JSONVariable
    private String tk_app_version;
    @JSONVariable
    private String tk_emulator_version;
    @JSONVariable
    private String tk_device_owner;
    @JSONVariable
    private String tk_os;
    @JSONVariable
    private String username;
    @JSONVariable
    private String password;
    @JSONVariable
    private String tk_app_name;

    public String getTk_uid() {
        return tk_uid;
    }

    public void setTk_uid(String tk_uid) {
        this.tk_uid = tk_uid;
    }

    public String getTk_device_name() {
        return tk_device_name;
    }

    public void setTk_device_name(String tk_device_name) {
        this.tk_device_name = tk_device_name;
    }

    public String getTk_mac_address() {
        return tk_mac_address;
    }

    public void setTk_mac_address(String tk_mac_address) {
        this.tk_mac_address = tk_mac_address;
    }

    public String getTk_device_model() {
        return tk_device_model;
    }

    public void setTk_device_model(String tk_device_model) {
        this.tk_device_model = tk_device_model;
    }

    public String getTk_app_version() {
        return tk_app_version;
    }

    public void setTk_app_version(String tk_app_version) {
        this.tk_app_version = tk_app_version;
    }

    public String getTk_emulator_version() {
        return tk_emulator_version;
    }

    public void setTk_emulator_version(String tk_emulator_version) {
        this.tk_emulator_version = tk_emulator_version;
    }

    public String getTk_device_owner() {
        return tk_device_owner;
    }

    public void setTk_device_owner(String tk_device_owner) {
        this.tk_device_owner = tk_device_owner;
    }

    public String getTk_os() {
        return tk_os;
    }

    public void setTk_os(String tk_os) {
        this.tk_os = tk_os;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTk_app_name() {
        return tk_app_name;
    }

    public void setTk_app_name(String tk_app_name) {
        this.tk_app_name = tk_app_name;
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
