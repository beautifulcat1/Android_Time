package Time.main_java.TheFour;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;


import java.util.Calendar;

import Time.main_java.R;
import Time.main_java.today_data.Calendar_Adapter;



public class calender_fragement extends Fragment {
    String TAG="calender_fragement";
    Context context;
    RecyclerView recyclerView;
    CalendarView calendarView ;
    View view;
    Calendar_Adapter calendar_adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.calender_fragement_view,container,false);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        init();
        choose();
    }
    @SuppressLint("NewApi")
    public void init(){
        context=getActivity();
        calendarView=view.findViewById(R.id.calendarView2);
        calendarView.setShownWeekCount(2);
        recyclerView = view.findViewById(R.id.recycle_view2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        calendar_adapter=new Calendar_Adapter(Calendar.getInstance().get(Calendar.DATE));
        recyclerView.setAdapter(calendar_adapter);
    }
    public void choose(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@androidx.annotation.NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar_adapter.Set_Data(month,dayOfMonth);
                calendar_adapter.notifyDataSetChanged();
            }
        });

    }



}
