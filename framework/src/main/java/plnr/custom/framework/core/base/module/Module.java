package plnr.custom.framework.core.base.module;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import plnr.custom.framework.core.base.ui.activity.BaseFragment;

/**
 * Created by ponlavitlarpeampaisarl on 2/18/15 AD.
 */
public interface Module {
    /**
     * Setup the menu title for this module
     *
     * @param state the button state that show
     * @return title of this module
     */
    public abstract String getMenuTitle(ButtonState state);

    /**
     * Setup the menu icon for this module
     *
     * @return the resource that will use as icon
     */
    public abstract String getMenuIcon();

    /**
     * Setup color of menu title text for this module
     *
     * @param state the button state that show
     * @return the color of the text
     */
    public abstract String getMenuTextColor(ButtonState state);

    /**
     * Setup the top navigator bar title text
     *
     * @return the title text
     */
    public abstract String getNavigationBarTitle();

    /**
     * Setup color of the title
     *
     * @return the color of the title on navigation bar
     */
    public abstract String getNavigationBarTextColor();

    /**
     * Setup color of the top navigator bar
     *
     * @return the color of the navigation bar
     */
    public abstract String getNavigationBarBackgroundColor();

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
    public abstract View.OnClickListener getTopRightButtonAction(Context context);

    /**
     * Setup the home content fragment
     *
     * @return the home content for this module
     */
    public abstract BaseFragment getModuleViewFragment();

}
