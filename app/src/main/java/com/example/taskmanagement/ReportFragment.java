package com.example.taskmanagement;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentReportBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {

FragmentReportBinding binding;
ProjectViewModel projectViewModel;
ContributorAdapter contributorAdapter;
    TeamDeliveryAdapter teamDeliveryAdapter;
    public ReportFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentReportBinding.inflate(inflater,container,false);
projectViewModel=new ProjectViewModel(new ProjectRepository());


projectViewModel.getReport(new ResponseCallback<Report>() {
    @Override
    public void onSuccess(Report data, String message) {
        teamDeliveryAdapter=new TeamDeliveryAdapter(requireContext(),data.getTeam_breakdown());
        binding.teamrecyler.setHasFixedSize(true);
        binding.teamrecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.teamrecyler.setAdapter(teamDeliveryAdapter);
        contributorAdapter=new ContributorAdapter(requireContext(),data.getMember_breakdown());
        binding.contributorRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.contributorRecycler.setHasFixedSize(true);
        binding.contributorRecycler.setAdapter(contributorAdapter);
        binding.completedCount.setText(String.valueOf((int) data.getKpis().getTotal_completed()));
        binding.velocitycount.setText(" of "+String.valueOf((int) data.getKpis().getTotal_created())+" created");
        binding.slaRate.setText(data.getKpis().on_time_rate+"% ");
        binding.sladueTask.setText(data.getKpis().total_overdue+"tasks currently overdue");
        binding.timeCount.setText(String.valueOf((int) data.getKpis().getAvg_cycle_time_days()));
        binding.allocatedhours.setText(String.valueOf((int) data.getKpis().getTotal_actual_hours()));
setUpThroughputChart(data);
    }

    @Override
    public void onError(String message) {

    }
});
        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    private void setUpThroughputChart(Report report) {

        List<ThroughoutTrend> trends = report.getThroughput_trend();

        if (trends == null || trends.isEmpty()) {
            binding.throughputChart.clear();
            return;
        }

        ArrayList<BarEntry> createdEntries = new ArrayList<>();
        ArrayList<BarEntry> completedEntries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < trends.size(); i++) {

            ThroughoutTrend trend = trends.get(i);
            float created=(float) trend.getCreated();
            float completed=(float) trend.getCompleted();
            if (created==0){
                created=0.6f;

            }
            if (completed==0){
                completed=0.6f;
            }

            createdEntries.add(
                    new BarEntry((float) i, (float) trend.getCreated())
            );

            completedEntries.add(
                    new BarEntry((float) i, (float) trend.getCompleted())
            );

            labels.add(trend.getLabel());
        }

        BarDataSet createdData =
                new BarDataSet(createdEntries, "Created Task");

        createdData.setColor(Color.rgb(105, 100, 255));
        createdData.setValueTextSize(10f);
        createdData.setValueTextColor(Color.DKGRAY);

        BarDataSet completedData =
                new BarDataSet(completedEntries, "Completed Task");

        completedData.setColor(Color.rgb(0, 195, 145));
        completedData.setValueTextSize(10f);
        completedData.setValueTextColor(Color.DKGRAY);

        BarData barData = new BarData(createdData, completedData);

        float barWidth = 0.30f;
        float barSpace = 0.05f;
        float groupSpace = 0.50f;

        barData.setBarWidth(barWidth);

        BarChart chart = binding.throughputChart;
        chart.setData(barData);
chart.setScaleEnabled(false);
chart.setPinchZoom(false);
chart.setDoubleTapToZoomEnabled(false);
        // X Axis
        XAxis xAxis = chart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setTextSize(9f);
        xAxis.setCenterAxisLabels(true);

        // Group bars
        barData.groupBars(0f, groupSpace, barSpace);

        final float groupWidth =
                barData.getGroupWidth(groupSpace, barSpace);

        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(groupWidth * labels.size());

        xAxis.setGranularity(groupWidth);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelCount(labels.size(), false);

        // Date labels ko har group ke center mein show karna
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {

                int index = Math.round(
                        (value - groupWidth / 2f) / groupWidth
                );

                if (index >= 0 && index < labels.size()) {
                    return labels.get(index);
                }

                return "";
            }
        });

        // Y Axis
        chart.getAxisLeft().setAxisMinimum(0f);
        chart.getAxisLeft().setTextColor(Color.GRAY);
        chart.getAxisRight().setEnabled(false);

        // Legend
        chart.getLegend().setEnabled(true);
        chart.getLegend().setTextSize(12f);
        chart.getLegend().setXEntrySpace(20f);

        chart.getDescription().setEnabled(false);

        chart.animateY(1000);
        chart.invalidate();
    }

}