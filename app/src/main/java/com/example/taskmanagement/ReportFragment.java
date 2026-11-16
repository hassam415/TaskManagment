package com.example.taskmanagement;

import android.app.DatePickerDialog;
import android.graphics.Color;
import java.util.TimeZone;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentReportBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportFragment extends Fragment {

FragmentReportBinding binding;
ProjectViewModel projectViewModel;
ContributorAdapter contributorAdapter;
    TeamDeliveryAdapter teamDeliveryAdapter;
    TeamViewModel teamViewModel;
    String datepreset="30d";
    String startDate=null;
    String endDate=null;
    int teamId=0;


    public ReportFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentReportBinding.inflate(inflater,container,false);
projectViewModel=new ProjectViewModel(new ProjectRepository());
ImageView filterBtn=requireActivity().findViewById(R.id.filterBtn);
filterBtn.setVisibility(View.VISIBLE);
filterBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        binding.filterDrawer.openDrawer(GravityCompat.END);
    }
});
teamViewModel=new TeamViewModel(new TeamRepository());


filterData();
binding.cancelbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        binding.dateSpinner.setSelection(1);
        binding.teamSpinner.setSelection(0);
        binding.startDateInput.setText("");
    }
});

//projectViewModel.getReport(new ResponseCallback<Report>() {
  //  @Override
    //public void onSuccess(Report data, String message) {


   // }

   // @Override
   // public void onError(String message) {

   // }
//});


teamViewModel.getTeamList(new ResponseCallback<List<Team>>() {
    @Override
    public void onSuccess(List<Team> data, String message) {
        List<Team>teams=new ArrayList<>();
        Team team=new Team();
        team.setId(0);
        team.setName("All Team");
        teams.add(team);
        teams.addAll(data);
        ArrayAdapter<Team>teamArrayAdapter=new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,teams);
        teamArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.teamSpinner.setAdapter(teamArrayAdapter);
        binding.teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Team selectedId=(Team) parent.getItemAtPosition(position);
                binding.applyCustomRange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        teamId=selectedId.getId();
                        loadReport();
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loadReport();
    }

    @Override
    public void onError(String message) {

    }
});


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void filterData() {
        String[]dates={"Last 7 Days","Last 30 Days","This Month","Last Month","Custom"};
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,dates);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dateSpinner.setAdapter(arrayAdapter);
        binding.dateSpinner.setSelection(1);
        binding.dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){

                    datepreset="7d";
                    binding.customDateLayout.setVisibility(View.GONE);
                    binding.applyCustomRange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            loadReport();
                            Toast.makeText(getContext(), "Data of Last 7 Days ", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                if (position==1){
                    datepreset="30d";
                    binding.customDateLayout.setVisibility(View.GONE);
                    binding.applyCustomRange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            loadReport();
                            Toast.makeText(getContext(), "Data of Last 30 Days ", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                if (position==2){

                    datepreset="this_month";
                    binding.customDateLayout.setVisibility(View.GONE);
                    binding.applyCustomRange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            loadReport();
                            Toast.makeText(getContext(), "Data of This Month ", Toast.LENGTH_SHORT).show();
                        }
                    });



                }
                if (position==3){

                    datepreset="last_month";
                    binding.customDateLayout.setVisibility(View.GONE);
                    binding.applyCustomRange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            loadReport();
                            Toast.makeText(getContext(), "Data of Last Month ", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
                if (position==4){

                    binding.customDateLayout.setVisibility(View.VISIBLE);
                    binding.startDateInput.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDateRangePicker();
                        }
                    });
                    binding.applyCustomRange.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            datepreset="custom";
                            loadReport();
                            binding.customDateLayout.setVisibility(View.GONE);

                        }
                    });



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private String formatDate(long timestamp, String pattern) {

        SimpleDateFormat sdf = new SimpleDateFormat(
                pattern,
                Locale.US
        );

        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        return sdf.format(new Date(timestamp));
    }
    private void showDateRangePicker() {

        MaterialDatePicker<Pair<Long, Long>> datePicker =
                MaterialDatePicker.Builder.dateRangePicker()
                        .setTitleText("Select Date Range")
                        .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {

            Long startMillis = selection.first;
            Long endMillis = selection.second;

            // API ke liye date format
            startDate = formatDate(startMillis, "yyyy-MM-dd");
            endDate = formatDate(endMillis, "yyyy-MM-dd");

            // Input field ke liye display format
            String displayStart = formatDate(startMillis, "dd/MM/yyyy");
            String displayEnd = formatDate(endMillis, "dd/MM/yyyy");

            binding.startDateInput.setText(
                    displayStart + " - " + displayEnd
            );
        });

        datePicker.show(
                getParentFragmentManager(),
                "DATE_RANGE_PICKER"
        );
    }
    private void loadReport() {
        projectViewModel.getFilterReport(datepreset, startDate, endDate, teamId, new ResponseCallback<Report>() {
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
                binding.filterDrawer.closeDrawer(GravityCompat.END);

            }

            @Override
            public void onError(String message) {

            }
        });
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