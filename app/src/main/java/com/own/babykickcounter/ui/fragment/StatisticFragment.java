package com.own.babykickcounter.ui.fragment;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.own.babykickcounter.R;
import com.own.babykickcounter.obvervable.TrackDataChangeObserver;
import com.own.babykickcounter.task.BarchartDataTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;


public class StatisticFragment extends Fragment implements Observer {
    BarChart barChart;
    TextView tvDurations;
    TextView tvKickPerSession;
    TextView tvKicks;
    TextView tvSessions;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_statistic, viewGroup, false);

        barChart = (BarChart) inflate.findViewById(R.id.barchart);
        tvDurations = (TextView) inflate.findViewById(R.id.tv_total_durations);
        tvKickPerSession = (TextView) inflate.findViewById(R.id.tv_kicks_per_session);
        tvKicks = (TextView) inflate.findViewById(R.id.tv_total_kicks);
        tvSessions = (TextView) inflate.findViewById(R.id.tv_total_sessions);

        TrackDataChangeObserver.getInstance().addObserver(this);
        refreshViews();
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TrackDataChangeObserver.getInstance().deleteObserver(this);
    }

    private void refreshViews() {
        new BarchartDataTask(getContext(), new BarchartDataTask.Listener() {
            public void onloadbarchartdata(ArrayList<String> arrayList, ArrayList<BarEntry> arrayList2, String str, String str2, String str3, String str4) {
                tvKicks.setText(str);
                tvDurations.setText(str2);
                tvSessions.setText(str3);
                tvKickPerSession.setText(str4);
                BarDataSet barDataSet = new BarDataSet(arrayList2, "Test");
                TypedValue typedValue = new TypedValue();
                StatisticFragment.this.getContext().getTheme().resolveAttribute(R.color.colorPrimary, typedValue, true);
                barDataSet.setColors(typedValue.data);
                StatisticFragment.this.getContext().getTheme().resolveAttribute(R.color.primaryTextColor, typedValue, true);
                barDataSet.setValueTextColor(typedValue.data);
                barDataSet.setValueFormatter(new LargeValueFormatter() {
                    @Override
                    public String getFormattedValue(float f) {
                        return f != 0.0f ? String.valueOf((int) f) : "";
                    }
                });
                barDataSet.setValueTextSize(14.0f);
                BarData barData = new BarData(barDataSet);
                StatisticFragment.this.barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter((Collection<String>) arrayList));
                StatisticFragment.this.barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                StatisticFragment.this.barChart.getAxisRight().setEnabled(false);
                StatisticFragment.this.barChart.getDescription().setEnabled(false);
                StatisticFragment.this.barChart.getXAxis().setDrawGridLines(false);
                StatisticFragment.this.barChart.setDrawBorders(false);
                StatisticFragment.this.barChart.getAxisLeft().setAxisMinimum(0.0f);
                StatisticFragment.this.barChart.getLegend().setEnabled(false);
                StatisticFragment.this.barChart.setData(barData);
                StatisticFragment.this.barChart.getAxisLeft().setValueFormatter(new LargeValueFormatter() {
                    @Override
                    public String getFormattedValue(float f) {
                        return String.valueOf((int) Math.floor((double) f));
                    }
                });
                StatisticFragment.this.barChart.getAxisLeft().setTextColor(typedValue.data);
                StatisticFragment.this.barChart.getXAxis().setTextColor(typedValue.data);
                StatisticFragment.this.barChart.invalidate();
            }
        }).execute(new Object[0]);
    }

    public void update(Observable observable, Object obj) {
        refreshViews();
    }
}
