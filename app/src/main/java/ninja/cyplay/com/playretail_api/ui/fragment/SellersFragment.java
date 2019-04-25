package ninja.cyplay.com.playretail_api.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.Seller;
import ninja.cyplay.com.apilibrary.utils.ActivityHelper;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.AuthenticationActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.SellerRecyclerAdapter;
import ninja.cyplay.com.playretail_api.ui.component.MarginDecoration;
import ninja.cyplay.com.playretail_api.ui.custom.FullScreenView;
import ninja.cyplay.com.playretail_api.ui.listener.RecyclerItemClickListener;
import ninja.cyplay.com.playretail_api.ui.presenter.GetSellersPresenter;
import ninja.cyplay.com.playretail_api.ui.view.GetSellersView;

/**
 * Created by damien on 28/04/15.
 */
public class SellersFragment extends BaseFragment implements GetSellersView {

    @InjectView(R.id.root)
    View root;

    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Optional
    @InjectView(R.id.progress_bar)
    ProgressBar progressBar;

    @Inject
    GetSellersPresenter getSellersPresenter;

    @Optional
    @InjectView(R.id.full_screen_view)
    FullScreenView fullScreenView;

    private SellerRecyclerAdapter adapter;

    private List<Seller> sellers;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sellers, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getActivity().getIntent() != null && getActivity().getIntent().hasExtra(Constants.EXTRA_SELLERS))
//            PRSellers = Parcels.unwrap(getActivity().getIntent().getParcelableExtra(Constants.EXTRA_SELLERS));
        getSellersPresenter.setView(this);
        adapter = new SellerRecyclerAdapter(this.sellers);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler();
        if (this.sellers == null)
            getSellersPresenter.getSellers();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initRecycler() {
        recyclerView.addItemDecoration(new MarginDecoration(getActivity(), 15, 15, 15, 15));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), ActivityHelper.ifTablet(getActivity()) ? 6 : 3));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new SellerItemClick()));
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
        if (fullScreenView != null)
            fullScreenView.showLoading();
    }

    @Override
    public void hideLoading() {
        if (fullScreenView != null)
            fullScreenView.hideLoading();
    }

    @Override
    public void onSellersSuccess(List<Seller> sellers) {
        this.sellers = sellers;
        adapter.setSellers(this.sellers);
        adapter.notifyDataSetChanged();
//        adapter = new SellerRecyclerAdapter(PRSellers);
    }
    public void onSellersError() {
        onError();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    private class SellerItemClick implements RecyclerItemClickListener.OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
            // TODO Handle item click
            Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
            intent.putExtra(Constants.EXTRA_SELLER, Parcels.wrap(sellers.get(position)));
            startActivity(intent);
        }
    }

}
