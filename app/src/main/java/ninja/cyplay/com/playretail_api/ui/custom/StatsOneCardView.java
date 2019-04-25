package ninja.cyplay.com.playretail_api.ui.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.github.mikephil.charting.animation.AnimationEasing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import ninja.cyplay.com.playretail_api.R;

/**
 * Created by damien on 20/07/15.
 */
public class StatsOneCardView extends FrameLayout {

    @Optional
    @InjectView(R.id.bar_chart)
    PieChart pieChart;

    protected final static String[] mParties = new String[] {
            "Jouets bébé", "Jouets 2-4ans", "Jouets 5-7ans", "Jouets 8-12ans", "T-shirt", "Jeans F", "Jeans G", "Jeans H",
            "Jeans I", "Jeans J", "Jeans K", "Jeans L", "Jeans M", "Jeans N", "Jeans O", "Jeans P",
            "Jeans Q", "Jeans R", "Jeans S", "Jeans T", "Jeans U", "Jeans V", "Jeans W", "Jeans X",
            "Jeans Y", "Jeans Z"
    };

    private Activity activity;

    public StatsOneCardView(Context context) {
        super(context);
        init(context);
    }

    public StatsOneCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StatsOneCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // -------------------------------------------------------------------------------------------
    //                                      Method(s)
    // -------------------------------------------------------------------------------------------

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.card_view_stats_one, this);
        ButterKnife.inject(this);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public void updateInfo() {

        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
//        pieChart.setExtraOffsets(5, 10, 5, 5);

//        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setCenterText("");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);

//        pieChart.setTransparentCircleColor(Color.WHITE);
//        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
//        pieChart.setHighlightPerTapEnabled(true);

        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener
//        pieChart.setOnChartValueSelectedListener(this);

        setData(3, 100);

        pieChart.getLegend().setEnabled(false);

        pieChart.animateY(1400, AnimationEasing.EasingOption.EaseInOutQuad);
        // pieChart.spin(2000, 0, 360);

//        Legend l = pieChart.getLegend();
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
//        setData(4, 10);
    }



    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, "Jouets");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

}
