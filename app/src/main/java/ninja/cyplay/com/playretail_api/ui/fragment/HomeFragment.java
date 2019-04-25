package ninja.cyplay.com.playretail_api.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;
import ninja.cyplay.com.apilibrary.models.business.features.Home;
import ninja.cyplay.com.apilibrary.models.business.features.Shortcut;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.model.business.EFeature;
import ninja.cyplay.com.playretail_api.model.cell.HomeTile;
import ninja.cyplay.com.playretail_api.ui.activity.BarCodeScannerActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CatalogueActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerHistoryActivity;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerSearchActivity;
import ninja.cyplay.com.playretail_api.ui.activity.InShopCustomerActivity;
import ninja.cyplay.com.playretail_api.ui.activity.TweetsActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.HomeRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.component.MarginDecoration;
import ninja.cyplay.com.playretail_api.ui.listener.RecyclerItemClickListener;
import ninja.cyplay.com.playretail_api.utils.ConfigHelper;

/**
 * Created by damien on 01/07/15.
 */
public class HomeFragment extends BaseFragment {

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    private HomeRecyclerAdapter adapter;

    private List<HomeTile> homeTiles;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        createTiles();
        adapter = new HomeRecyclerAdapter(getActivity(), homeTiles);
    }

//    private class TilePositionComparator {
//        public boolean compare(Shortcut object1, Shortcut object2) {
//            if (object1 != null && object2 != null)
//                return object1.;
//            return false;
//        }
//    }

    private void createTiles() {
        homeTiles = new ArrayList<>();

        if (ConfigHelper.getInstance().getFeature() != null
                && ConfigHelper.getInstance().getFeature().getHome() != null
                && ConfigHelper.getInstance().getFeature().getHome().getShortcuts() != null ) {
            for (int i = 0 ; i < ConfigHelper.getInstance().getFeature().getHome().getShortcuts().size() ; i++) {

            }
        }
//        Collections.sort(homeTiles, new TilePositionComparator());

        homeTiles.add(new HomeTile("Clients",   R.drawable.ic_account_search_black_48dp, HomeTile.ETileAction.CLIENTS));
        if (ConfigHelper.getInstance().isFeatureActivated(EFeature.SCAN))
            homeTiles.add(new HomeTile("Scan",      R.drawable.ic_scan_symbol, HomeTile.ETileAction.SCAN));
        if (ConfigHelper.getInstance().isFeatureActivated(EFeature.TWEETS))
            homeTiles.add(new HomeTile("Tweets",    R.drawable.ic_send_black_48dp, HomeTile.ETileAction.TWEETS));
        homeTiles.add(new HomeTile("Catalog",   R.drawable.ic_catalog_black_48dp, HomeTile.ETileAction.CATALOG));
        homeTiles.add(new HomeTile("History",   R.drawable.ic_assignment_ind_black_48dp, HomeTile.ETileAction.CHISTORY));
        if (ConfigHelper.getInstance().isFeatureActivated(EFeature.BEACON))
            homeTiles.add(new HomeTile("InShop",    R.drawable.ic_settings_input_antenna_black_48dp, HomeTile.ETileAction.INSHOP));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateDesign();
        initRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        // to Update Badger
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    void updateDesign() {
    }

    private void initRecycler() {
        recyclerView.addItemDecoration(new MarginDecoration(getActivity(), 3, 3, 3, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new ItemClick()));
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    private class ItemClick implements RecyclerItemClickListener.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            // Handle item click
            HomeTile homeTile = homeTiles.get(position);
            Intent intent;
            if (homeTile != null) {
                switch (homeTile.getAction()) {
                    case CLIENTS:
                        intent = new Intent(getActivity(), CustomerSearchActivity.class);
                        startActivity(intent);
                        break;
                    case SCAN:
                        intent = new Intent(getActivity(), BarCodeScannerActivity.class);
                        startActivity(intent);
                        break;
                    case TWEETS:
                        intent = new Intent(getActivity(), TweetsActivity.class);
                        startActivity(intent);
                        break;
                    case CATALOG:
                        intent = new Intent(getActivity(), CatalogueActivity.class);
                        startActivity(intent);
                        break;
                    case CHISTORY:
                        intent = new Intent(getActivity(), CustomerHistoryActivity.class);
                        startActivity(intent);
                        break;
                    case INSHOP:
                        intent = new Intent(getActivity(), InShopCustomerActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        }
    }


}
