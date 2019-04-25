package ninja.cyplay.com.playretail_api.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.ui.fragment.CatalogueFilterFragment;

/**
 * Created by damien on 21/05/15.
 */
public class CatalogueFilterActivity extends BaseActivity {

    // Fragments
    private CatalogueFilterFragment     catalogueFilterFragment;
    private final static String CATALOGUE_FILTER_FRAGMENT = "CATALOGUE_FILTER_FRAGMENT";

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_filter, null);
        setContentView(view);

        // show back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(CATALOGUE_FILTER_FRAGMENT))
            catalogueFilterFragment = (CatalogueFilterFragment) getSupportFragmentManager().getFragment(savedInstanceState, CATALOGUE_FILTER_FRAGMENT);
        else
            catalogueFilterFragment = new CatalogueFilterFragment();

        updateDesign();
        initFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (catalogueFilterFragment != null && getSupportFragmentManager().findFragmentById(R.id.container) == catalogueFilterFragment)
            getSupportFragmentManager().putFragment(outState, CATALOGUE_FILTER_FRAGMENT, catalogueFilterFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.catalogue_filter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_catalogue_filtre_apply:
                catalogueFilterFragment.returnAndApplyFilters();
                return true;

            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, catalogueFilterFragment);
        transaction.commit();
    }

    private void updateDesign() {
        // show back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
