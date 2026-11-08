package com.example.taskmanagement;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskmanagement.callback.ResponseCallback;
import com.example.taskmanagement.databinding.FragmentCalendarBinding;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.ViewContainer;

import org.jspecify.annotations.NonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CalendarFragment extends Fragment {
    FragmentCalendarBinding binding;


    YearMonth currentMonth;
    LocalDate selectedDate;
    ProjectViewModel projectViewModel;

    Map<LocalDate, Calendar.DaySummary> taskMap = new HashMap<>();

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCalendarBinding.inflate(inflater, container, false);

        projectViewModel=new ProjectViewModel(new ProjectRepository());
        currentMonth = YearMonth.now();
        selectedDate = LocalDate.now();

        setupCalendar();

binding.calendarView.setOnTouchListener((v,event)->true);
        binding.previousMonth.setOnClickListener(v -> {
            currentMonth = currentMonth.minusMonths(1);
            Toast.makeText(getContext(), "Month Changed TO "+currentMonth, Toast.LENGTH_SHORT).show();
            setupCalendar();
            updateMonth();
            loadDataApi();
        });

        binding.nextMonth.setOnClickListener(v -> {
            currentMonth = currentMonth.plusMonths(1);
            Toast.makeText(getContext(), "Month Changed To "+currentMonth, Toast.LENGTH_SHORT).show();
            setupCalendar();
            updateMonth();
            loadDataApi();
        });

        updateMonth();
        loadDataApi();

        return binding.getRoot();
    }

    private void loadDataApi() {

        String month = currentMonth.toString();

        Log.d("CalendarAPI", "Request Month = " + month);

        projectViewModel.getCalenderList(month, new ResponseCallback<Calendar>() {
            @Override
            public void onSuccess(Calendar data, String message) {

                Log.d("CalendarAPI", "Month = " + data.getMonth());
                Log.d("CalendarAPI", "Total = " + data.getTotal_tasks());
                Log.d("CalendarAPI", "Completed = " + data.getCompleted_tasks());
                Log.d("CalendarAPI", "Pending = " + data.getPending_tasks());

                binding.totaltask.setText(String.valueOf(data.getTotal_tasks()));
                binding.completedTask.setText(String.valueOf(data.getCompleted_tasks()));
                binding.pendingTask.setText(String.valueOf(data.getPending_tasks()));

                taskMap.clear();

                if (data.getDays() != null) {

                    for (Map.Entry<String, Calendar.DaySummary> entry
                            : data.getDays().entrySet()) {

                        LocalDate date = LocalDate.parse(entry.getKey());

                        taskMap.put(date, entry.getValue());
                    }
                }

                binding.calendarView.notifyCalendarChanged();
            }

            @Override
            public void onError(String message) {
                Log.e("CalendarAPI", "Error = " + message);
            }
        });
    }

    private void updateMonth() {
        binding.monthTitle.setText(
                currentMonth.format(
                        DateTimeFormatter.ofPattern("MMMM yyyy")
                )
        );
    }

    private void setupCalendar() {

        binding.calendarView.setDayBinder(
                new MonthDayBinder<DayContainer>() {

                    @NonNull
                    @Override
                    public DayContainer create(@NonNull View view) {
                        return new DayContainer(view);

                    }


                    @Override
                    public void bind(@NonNull DayContainer container,
                                     @NonNull CalendarDay day) {


                        if (day.getPosition() != DayPosition.MonthDate) {
                            container.dayNumber.setVisibility(View.GONE);
                            container.taskCount.setVisibility(View.GONE);
                            container.taskChips.removeAllViews();
                            return;
                        }

                        container.dayNumber.setVisibility(View.VISIBLE);
                        LocalDate date = day.getDate();

                        container.day = day;
                        container.dayNumber.setText(
                                String.valueOf(date.getDayOfMonth())
                        );

                        container.taskChips.removeAllViews();

                        Calendar.DaySummary tasks = taskMap.get(date);

                        container.taskChips.removeAllViews();

                        if (tasks != null && tasks.getTotal_count() > 0) {

                            container.taskCount.setVisibility(View.VISIBLE);

                            container.taskCount.setText(
                                    tasks.getTotal_count()+""
                            );

                            TextView task = new TextView(requireContext());


                            task.setTextSize(9);
                            task.setTextColor(Color.BLUE);
                            task.setSingleLine(true);

                            container.taskChips.addView(task);

                        } else {

                            container.taskCount.setVisibility(View.GONE);
                        }
                    }
                }
        );

        binding.calendarView.setup(
                currentMonth,
                currentMonth,
                DayOfWeek.SUNDAY
        );

        binding.calendarView.scrollToMonth(currentMonth);
    }

    class DayContainer extends ViewContainer {

        TextView dayNumber, taskCount;
        LinearLayout taskChips;

        CalendarDay day;

        DayContainer(View view) {
            super(view);

            dayNumber = view.findViewById(R.id.dayNumber);
            taskCount = view.findViewById(R.id.taskCount);
            taskChips = view.findViewById(R.id.taskChips);

            view.setOnClickListener(v -> {
                if (day != null &&
                        day.getPosition() == DayPosition.MonthDate) {

                    selectedDate = day.getDate();
                    binding.calendarView.notifyDateChanged(selectedDate);
                }
            });
        }
    }

}
