package ninja.cyplay.com.playretail_api.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseActivity;
import ninja.cyplay.com.playretail_api.model.business.Seller;
import ninja.cyplay.com.playretail_api.ui.adapter.SellerRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.component.MarginDecoration;
import ninja.cyplay.com.playretail_api.ui.fragment.AuthenticationFragment;
import ninja.cyplay.com.playretail_api.ui.listener.RecyclerItemClickListener;
import ninja.cyplay.com.playretail_api.ui.presenter.GetSellersPresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetSellersView;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;

public class LoginActivityListActivity extends BaseActivity implements GetSellersView {

    @Inject
    GetSellersPresenter getSellersPresenter;

    @Optional
    @InjectView(R.id.loginactivity_list)
    RecyclerView recyclerView;

    private List<Seller> sellers;
    private SellerRecyclerAdapter adapter;

    // Whether or not the activity is in two-pane mode, i.e. running on a tablet device
    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity_list);
        injectViews();

        if (findViewById(R.id.loginactivity_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // Recycler init
        initRecycler();

        // Init Presenter
        getSellersPresenter.setView(this);
        getSellersPresenter.getSellers();

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        ColorUtils.setActionBarBackgroundColor(getSupportActionBar(), ColorUtils.FeatureColor.PRIMARY_DARK);

//        View recyclerView = findViewById(R.id.loginactivity_list);
//        assert recyclerView != null;
//        setupRecyclerView((RecyclerView) recyclerView);


    }

    // -------------------------------------------------------------------------------------------
    //                                      Override(s)
    // -------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sellers_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_sellers_reload:
                reloadSellers();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initRecycler() {
        adapter = new SellerRecyclerAdapter(this.sellers);
        recyclerView.addItemDecoration(new MarginDecoration(this, 15, 15, 15, 15));
        recyclerView.setHasFixedSize(true);
        if (mTwoPane)
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        else
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new SellerItemClick()));
    }

    public void reloadSellers() {
        // empty seller list
        adapter.setSellers(null);
        adapter.notifyDataSetChanged();
        // reload seller list
        getSellersPresenter.getSellers();
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onSellersSuccess(List<Seller> sellers) {
        this.sellers = sellers;
        adapter.setSellers(this.sellers);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSellersError() {

    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    private class SellerItemClick implements RecyclerItemClickListener.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(Constants.EXTRA_SELLER, Parcels.wrap(sellers.get(position)));
                AuthenticationFragment fragment = new AuthenticationFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.loginactivity_detail_container, fragment)
                        .commit();
            }
            else {
                Intent intent = new Intent(LoginActivityListActivity.this, AuthenticationActivity.class);
                intent.putExtra(Constants.EXTRA_SELLER, Parcels.wrap(sellers.get(position)));
                startActivity(intent);
            }
        }
    }

}
