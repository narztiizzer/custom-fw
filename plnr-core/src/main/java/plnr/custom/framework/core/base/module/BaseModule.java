package plnr.custom.framework.core.base.module;

import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import plnr.custom.framework.core.base.annotation.JSONVariable;
import plnr.custom.framework.core.base.enumulation.UpdatePolicy;
import plnr.custom.framework.core.base.intf.IRestServiceObjectDelegate;
import plnr.custom.framework.core.base.model.BaseArrayList;
import plnr.custom.framework.core.base.model.BaseMasterArrayList;
import plnr.custom.framework.core.base.model.BaseModel;
import plnr.custom.framework.core.base.ui.activity.BaseFragment;
import plnr.custom.framework.core.base.ui.listview.BaseCellDataSource;
import plnr.custom.framework.core.base.ui.listview.BaseListAdapter;
import plnr.custom.framework.core.base.ui.listview.BaseScrollListAdapter;

/**
 * Created by ponlavitlarpeampaisarl on 2/17/15 AD.
 */
public abstract class BaseModule extends BaseModel implements Module {
    public boolean isShowInWidget;
    @JSONVariable
    protected String menuTitle_SelectedState;
    @JSONVariable
    protected String menuTitle_NormalState;
    @JSONVariable
    protected String menuTitleColor_SelectedState;
    @JSONVariable
    protected String menuTitleColor_NormalState;
    @JSONVariable
    protected String navigationTitle;
    @JSONVariable
    protected String navigationBarBackgroundColor;
    protected BaseArrayList<BaseMasterArrayList<BaseModel>> master = BaseArrayList.Builder(BaseMasterArrayList.class);
    private Context mContext;
    private ArrayList<String> serviceRegistration = new ArrayList();

    public BaseModule(Context context) {
        registerMaster(master);
        setupServiceRegistration(serviceRegistration);
        this.mContext = context;
    }

    /**
     * Force no default constructor
     */
    private BaseModule() {
        throw new UnsupportedOperationException();
    }

    public ArrayList<String> getServiceRegistration() {
        return serviceRegistration;
    }

    public abstract boolean shouldHideActionBar();

    public abstract void registerMaster(BaseArrayList<BaseMasterArrayList<BaseModel>> master);

    /**
     * Get master data for this module
     *
     * @return the model for master data
     */
    public abstract Object getMaster(int i);

    public int sizeOfMaster() {
        return this.master.size();
    }

    /**
     * Reload the master data for this module
     */
    public void fetchMaster(UpdatePolicy policy) {
        // TODO: Need to check version before fetch
//        if(master.size()>0)
//            ((BaseModule)getMaster(0)).fetch(policy);
    }

    public abstract boolean moduleNeedMaster();

    /**
     * Reload the master data for this module
     */
    public void fetchMaster(IRestServiceObjectDelegate delegate, UpdatePolicy policy) {
        // TODO: Need to check version before fetch
        if (master.size() > 0) {
            for (int i = 0; i < master.size(); i++) {
                ((BaseArrayList<BaseModel>) getMaster(i)).fetch(delegate, policy);
            }
        }
    }

    /**
     * Reload the master data for this module
     */
    public void removeCacheMaster() {
        // TODO: Remove master cache from database
    }

    /**
     * This function is use for save the data from cell data source to model object
     *
     * @param list
     * @param row
     * @param dataSource data of the cell  @return is the save process success or not
     */
    @Override
    public boolean saveListHandler(BaseListAdapter list, int row, BaseCellDataSource dataSource) {
        return false;
    }

    @Override
    public boolean saveListHandler(BaseScrollListAdapter list, int row, BaseCellDataSource dataSource) {
        return false;
    }


    /**
     * This function is for validation before the save process
     *
     * @param dataSource data of the cell
     * @return is this save process should start or not
     */
    @Override
    final public boolean shouldSaveListHandler(BaseCellDataSource dataSource) {
        return false;
    }

    /**
     * Get the context of caller
     *
     * @return the context
     */
    protected Context getContext() {
        return this.mContext;
    }

    /**
     * Setup the menu title for this module
     *
     * @param state the button state that show
     * @return title of this module
     */
    public String getMenuTitle(ButtonState state) {
        return state == ButtonState.Normal ?
                this.menuTitle_NormalState :
                this.menuTitle_SelectedState;
    }

    /**
     * Setup color of menu title text for this module
     *
     * @param state the button state that show
     * @return the string represent color of the text
     */
    public String getMenuTextColor(ButtonState state) {
        return state == ButtonState.Normal ?
                this.menuTitleColor_NormalState :
                this.menuTitleColor_SelectedState;
    }

    /**
     * Setup the top navigator bar title text
     *
     * @return the title text
     */
    public String getNavigationBarTitle() {
        return this.navigationTitle;
    }

    /**
     * Setup color of the top navigator bar
     *
     * @return the string represent navigation bar color
     */
    public String getNavigationBarBackgroundColor() {
        return this.navigationBarBackgroundColor;
    }

    /**
     * Setup the menu icon for this module
     *
     * @return the resource that will use as icon
     */
    public abstract String getMenuIcon();

    /**
     * Should the application show the search box and button or not
     *
     * @return boolean that indicate weather the application should show the search box
     */
    public abstract boolean shouldShowSearchBox();

    /**
     * Should the application show the top right button or not
     *
     * @return boolean that indicate weather the application should show the top right box
     */
    public abstract boolean shouldShowTopRightButton();

    /**
     * Handler of the search box text change
     *
     * @param searchBox text field of the search box
     */
    public abstract void handleOnTextChangeSearchBox(TextView searchBox);

    /**
     * Handler of the search box on focus event
     *
     * @param searchBox text field of the search box
     */
    public abstract void handleOnFocusSearchBox(TextView searchBox);

    /**
     * Handler of the search box out focus event
     *
     * @param searchBox text field of the search box
     */
    public abstract void handleOnOutFocusSearchBox(TextView searchBox);

    /**
     * Setup the top right button image resource
     *
     * @param state the button state that show
     * @return the image resource for top right button
     */
    public abstract int getTopRightButtonImageResource(ButtonState state);

    /**
     * Setup the action for top right button
     *
     * @param context the context
     * @return the on click listener for top right button
     */
    public abstract View.OnClickListener getTopRightButtonAction(final Context context);

    /**
     * Setup the home content fragment
     *
     * @return the home content for this module
     */
    public abstract BaseFragment getModuleViewFragment();

    /**
     * Call this function when the module will load.
     */
    public void viewWillLoadModule() {

    }

    /**
     * Call this function when the module have loaded.
     */
    public void viewDidLoadModule() {

    }

    /**
     * Call this function when the module will appear
     */
    public void viewWillShowModule() {

    }

    /**
     * Call this function when module did appear
     */
    public void viewDidShowModule() {

    }

    /**
     * Call this function when module will disappear
     */
    public void viewWillDisappear() {

    }

    /**
     * Call this function when module did disappear
     */
    public void viewDidDisappear() {

    }

    public abstract void performService(String key, HashMap<?, ?> info);

    public abstract void setupServiceRegistration(ArrayList<String> serviceRegistration);

    /**
     * Should the application show in menu or not
     *
     * @return should or shouldn't
     */
    public abstract boolean shouldShowInMenu();

    /**
     * Should the application show in home fragment or not
     *
     * @return should or shouldn't
     */
    public abstract boolean shouldShowInHome();

    /**
     * Setup the action for Home menu button
     *
     * @param context the context
     * @return the on click listener for top right button
     */
    public abstract View.OnClickListener getHomeMenuAction(final Context context);

    /**
     * Setup the home icon for this module
     *
     * @return the resource that will use as icon
     */
    public abstract int getHomeIconResource();

    public abstract boolean shouldFetchMaster();

    public abstract boolean canBeAWidget();

    public abstract Fragment setWidgetFragment();

}
