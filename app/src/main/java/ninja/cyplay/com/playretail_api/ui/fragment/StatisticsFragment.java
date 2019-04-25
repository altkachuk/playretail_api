package ninja.cyplay.com.playretail_api.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.InjectView;
import ninja.cyplay.com.playretail_api.R;
import ninja.cyplay.com.playretail_api.app.BaseFragment;
import ninja.cyplay.com.playretail_api.ui.custom.StatsOneCardView;

/**
 * Created by damien on 07/05/15.
 */
public class StatisticsFragment extends BaseFragment {

    @InjectView(R.id.stats_one)
    StatsOneCardView statsOneCardView;

//    @InjectView(R.id.stats_two)
//    StatsOneCardView statsTwoCardView;

    // -------------------------------------------------------------------------------------------
    //                                      Lifecycle
    // -------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCardViews();
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void initCardViews() {
        statsOneCardView.setActivity(getActivity());
        statsOneCardView.updateInfo();

//        statsTwoCardView.setActivity(getActivity());
//        statsTwoCardView.updateInfo();

    }

}
