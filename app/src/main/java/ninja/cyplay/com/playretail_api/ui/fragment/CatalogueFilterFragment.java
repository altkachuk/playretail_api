package ninja.cyplay.com.playretail_api.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import ninja.cyplay.com.playretail_api.model.business.Filter;
import ninja.cyplay.com.playretail_api.model.business.FilterCheck;
import ninja.cyplay.com.apilibrary.models.business.FilterType;
import ninja.cyplay.com.playretail_api.utils.ColorUtils;
import ninja.cyplay.com.apilibrary.utils.Constants;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.activity.CatalogueActivity;
import ninja.cyplay.com.playretail_api.ui.custom.CatalogueFilterCheckbox;

/**
 * Created by damien on 20/05/15.
 */
public class CatalogueFilterFragment extends BaseFragment {

    @InjectView(R.id.filter_panel)
    FrameLayout filterPanel;

    @InjectView(R.id.search_bar)
    EditText searchText;

    @InjectView(R.id.filter_price_label)
    TextView filterPriceLabel;

    @InjectView(R.id.rangebar)
    RangeBar priceRangeBar;

    private List<Filter> PRFilterList;

    @InjectView(R.id.display_more_button)
    TextView displayMoreButton;

    /*
    *
    * this Container with contain a list of Linearlayout with the spec name of the filter
    * and for every LinearLayout it will be a list of element with the filter Type
     */
    @InjectView(R.id.filters_container)
    LinearLayout filtersContainer;


    private ArrayList<Boolean> checkboxesActivated;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_catalogue_filter, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity().getIntent() != null && getActivity().getIntent().hasExtra(Constants.EXTRA_SEARCH_FILTER))
            PRFilterList = Parcels.unwrap(getActivity().getIntent().getParcelableExtra(Constants.EXTRA_SEARCH_FILTER));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateDesign();
        populateFilters(PRFilterList);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void updateDesign() {
        int primary_light = Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_LIGHT));
        int primary_dark = Color.parseColor(ColorUtils.FeatureColor.getColorHex(ColorUtils.FeatureColor.PRIMARY_DARK));
        priceRangeBar.setBarColor(primary_dark);
        priceRangeBar.setPinColor(primary_light);
        priceRangeBar.setTickColor(primary_dark);
        priceRangeBar.setSelectorColor(primary_light);
        priceRangeBar.setConnectingLineColor(primary_light);
    }



    private void populateFilters(List<Filter> PRFilterList) {
        if (PRFilterList != null) {
            for (int i = 0; i < PRFilterList.size(); i++) {
                //TODO add the complete list of filter type here and the appropriate methode to populate the filters
                if (PRFilterList.get(i).getType().equals(FilterType.CHECKBOX.toString()))
                    populateCheckboxes(PRFilterList.get(i));
            }
        }
    }

    private void populateCheckboxes( Filter PRFilter) {
        if (PRFilter != null) {
            //create the layout
            LinearLayout filterlinearLayout = new LinearLayout(getActivity());
            filterlinearLayout.setOrientation(LinearLayout.VERTICAL);
            filterlinearLayout.setTag(PRFilter.getSpec());

            //create the text view
            TextView display_name_txt_view = new TextView(getActivity());
            display_name_txt_view.setText(PRFilter.getDisplay_name());
            filterlinearLayout.addView(display_name_txt_view);

            //create the checkbox list
            for (int i = 0; i < PRFilter.getValues().size(); i++) {
                CatalogueFilterCheckbox checkbox = new CatalogueFilterCheckbox(getActivity());
                checkbox.setLabel(PRFilter.getValues().get(i));
                filterlinearLayout.addView(checkbox);
            }

            //add the all to the layout
            filtersContainer.addView(filterlinearLayout);

        }
    }


    public void returnAndApplyFilters() {
        List<FilterCheck> chosenFilters = prepareFiltersForRequest();
        Intent intent = new Intent(getActivity(), CatalogueActivity.class);
        intent.putExtra(Constants.EXTRA_SEARCH_FILTER_CHECK, Parcels.wrap(chosenFilters));
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    private List<FilterCheck> prepareFiltersForRequest() {

        List<FilterCheck> filters =  new ArrayList<FilterCheck>();
        for (int i = 0; i <filtersContainer.getChildCount(); i++) {

            FilterCheck filterCheck = new FilterCheck();
            LinearLayout filterView = (LinearLayout) filtersContainer.getChildAt(i);
            if(filterView!= null){
                filterCheck.setSpec(filterView.getTag().toString());
                //verification of checked box values

                List<String> checkedValues = new ArrayList<String>();
                for (int j = 1; j <filterView.getChildCount(); j++) {

                    CatalogueFilterCheckbox checkboxView = (CatalogueFilterCheckbox) filterView.getChildAt(j);
                    if (checkboxView.checkBox.isChecked())
                        checkedValues.add(checkboxView.getLabel());
                }
                filterCheck.setValues(checkedValues);
            }

            filters.add(filterCheck);
        }
        return filters;

    }

    // -------------------------------------------------------------------------------------------
    //                                      Action(s)
    // -------------------------------------------------------------------------------------------

    @OnClick(R.id.display_more_button)
    public void displayMoreCheckBoxes() {
       // populateCheckboxes();
       // populateCheckboxes();
        displayMoreButton.setVisibility(View.GONE);
    }

}
