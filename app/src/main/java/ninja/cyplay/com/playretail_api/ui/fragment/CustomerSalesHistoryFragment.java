package ninja.cyplay.com.playretail_api.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.model.business.SalesHistory;
import ninja.cyplay.com.playretail_api.model.events.ClickAndCollectEvent;
import ninja.cyplay.com.playretail_api.model.events.GetSalesHistoryEvent;
import ninja.cyplay.com.playretail_api.model.events.HistoryTabEvent;
import ninja.cyplay.com.apilibrary.utils.DateUtils;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.ui.activity.CustomerActivity;
import ninja.cyplay.com.playretail_api.ui.adapter.SalesHistoryAdapter;
import ninja.cyplay.com.playretail_api.ui.component.MultiStateToggleButton;
import ninja.cyplay.com.playretail_api.ui.component.ToggleButton;
import ninja.cyplay.com.playretail_api.ui.custom.FullScreenView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by damien on 07/05/15.
 */
public class CustomerSalesHistoryFragment extends OpinionBaseFragment {

    @Inject
    public Bus bus;

    @InjectView(R.id.list_view)
    StickyListHeadersListView listView;

    @InjectView(R.id.purchase_navigation_options)
    MultiStateToggleButton navigationOptions;

    @InjectView(R.id.full_screen_view)
    FullScreenView fullScreenView;

    @InjectView(R.id.empty_string_text_view)
    TextView emptyString;

    private int lastSegmentSelected = 0;

    private List<SalesHistory> salesHistories = new ArrayList<>();
    private List<SalesHistory> filteredPRSalesHistory;

    SalesHistoryAdapter adapter;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_sales_history, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
        adapter = new SalesHistoryAdapter(getActivity(), salesHistories);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createNavigateOption();
        listView.setAdapter(adapter);
        if (fullScreenView != null)
            fullScreenView.showLoading();
        loadSalesHistory();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Optional
    @OnClick(R.id.error_view_button)
    void reload() {
        fullScreenView.hideReloadButton();
        loadSalesHistory();
    }
    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    public void createNavigateOption() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int yearMinusOne = year - 1;
        navigationOptions.setElements(PurchaseState.getLabels(this.getActivity()));
        navigationOptions.setValue(0);
        navigationOptions.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                lastSegmentSelected = value;
                reloadItems();
            }
        });
    }

    private void reloadItems() {
        adapter.closeAllItems();
        loadSalesHistory();
    }

    private void loadSalesHistory() {
        this.salesHistories = ((CustomerActivity)getActivity()).salesHistories;
        if (this.salesHistories != null) {
            if (salesContainProduct()) {
                listView.setVisibility(View.VISIBLE);
                navigationOptions.setVisibility(View.VISIBLE);
                emptyString.setVisibility(View.GONE);
            }
            else {
                listView.setVisibility(View.GONE);
                navigationOptions.setVisibility(View.GONE);
                emptyString.setVisibility(View.VISIBLE);
            }
            // stop loading animation
            if (fullScreenView != null)
                fullScreenView.hideLoading();
            // show items
            filteredPRSalesHistory = PurchaseState.values()[navigationOptions.getValue()].filter(this.salesHistories);
            adapter.setPRSalesHistory(filteredPRSalesHistory);
            adapter.notifyDataSetChanged();
        }
        else if (((CustomerActivity)getActivity()).salesHistoryError) {
            onError();
        }
    }

    private boolean salesContainProduct() {
        for (int i = 0 ; i < salesHistories.size() ; i++)
            if (salesHistories.get(i).getPl() != null && salesHistories.get(i).getPl().size() > 0)
                return true;
        return false;
    }

    // -------------------------------------------------------------------------------------------
    //                                      Extra(s)
    // -------------------------------------------------------------------------------------------

    private enum PurchaseState {
        IN_PROGRESS(R.string.in_progress_purchase),
        TO_PICKUP(R.string.purchase_to_pick_up),
        THIS_YEAR_PURCHASE(0),
        OLD_PURCHASE(0);

        public static String[] labels;
        private int stringId;

        PurchaseState(int stringId) {
            this.stringId = stringId;
        }

        public static String[] getLabels(Context context) {
            if (labels == null) {
                labels = new String[PurchaseState.values().length];
                labels[IN_PROGRESS.ordinal()] = context.getString(IN_PROGRESS.stringId);
                labels[TO_PICKUP.ordinal()] = context.getString(TO_PICKUP.stringId);
                labels[THIS_YEAR_PURCHASE.ordinal()] = String.valueOf(DateUtils.getDateYear(new Date()));
                labels[OLD_PURCHASE.ordinal()] = String.valueOf(DateUtils.getDateYear(new Date()) - 1) + "...";
            }
            return labels;
        }

        public List<SalesHistory> filter(List<SalesHistory> salesHistoriesToFilter) {
            List<SalesHistory> filteredPRSalesHistory = new ArrayList<SalesHistory>();
            for (SalesHistory PRSalesHistory : salesHistoriesToFilter) {
                if (PRSalesHistory.getSta() != null) {
                    if (PRSalesHistory.getSta() == this.ordinal()) {
                        filteredPRSalesHistory.add(PRSalesHistory);
                    }
                } else {
                    int currentYear = DateUtils.getDateYear(new Date());
                    int purchaseYear = DateUtils.getDateYear(PRSalesHistory.getDateObject());
                    if (this == THIS_YEAR_PURCHASE && currentYear == purchaseYear) {
                        filteredPRSalesHistory.add(PRSalesHistory);
                    } else if (this == OLD_PURCHASE && currentYear > purchaseYear) {
                        filteredPRSalesHistory.add(PRSalesHistory);
                    }
                }
            }
            return filteredPRSalesHistory;
        }
    }

    // -------------------------------------------------------------------------------------------
    //                                      View Impl
    // -------------------------------------------------------------------------------------------

    @Override
    public void onLikeSuccess() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onDisLikeSuccess() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        listView.setVisibility(View.GONE);
        navigationOptions.setVisibility(View.GONE);
        if (fullScreenView != null) {
            fullScreenView.showError();
            fullScreenView.showReloadButton();
        }
    }

    @Override
    public void displaySttPopUp(String message, String stt) {
        //do nothing
    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.error_view_button)
    public void onReloadButtonClicked() {
        ((CustomerActivity)getActivity()).getSalesHistoryRequest();
        Toast.makeText(getContext(), "TAP BTN", Toast.LENGTH_SHORT).show();
    }

    // -------------------------------------------------------------------------------------------
    //                                       Event(s)
    // -------------------------------------------------------------------------------------------

    @Subscribe
    public void getSalesHistory(GetSalesHistoryEvent event) {
        if (((CustomerActivity)getActivity()).salesHistoryError)
            onError();
        else
            loadSalesHistory();
    }

    @Subscribe
    public void onTabSelect(HistoryTabEvent customerInfoTabEvent) {
        pagesViews.addPageView(className);
    }

    @Subscribe
    public void clickAndCollect(ClickAndCollectEvent event) {
        if (event != null) {
            if (event.getProduct() != null && filteredPRSalesHistory != null) {
                for (int i = 0 ; i < filteredPRSalesHistory.size() ; i++) {
                    SalesHistory PRSalesHistory = filteredPRSalesHistory.get(i);
                    if (PRSalesHistory.getPl() != null)
                        for (int j = 0 ; j < PRSalesHistory.getPl().size() ; j++) {
                            if (event.getProduct().equals(PRSalesHistory.getPl().get(j))) {
                                PRSalesHistory.setSta(2);
                                showLoading();
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideLoading();
                                        reloadItems();
                                    }
                                }, 1000);
                                return;
                            }
                        }
                }
            }
        }
    }

}
