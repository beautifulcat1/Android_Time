package Time.main_java.TheFour;

import android.app.AlertDialog;
import android.app.Dialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import Time.main_java.MyApplication;
import Time.main_java.R;
import Time.main_java.Synchronization.Up_Today_Date;
import Time.main_java.Theone.Manage_user;
import Time.main_java.Theone.User_bean;
import Time.main_java.today_data.Manage_Today_Data;
import Time.main_java.today_data.Today_Data;
import Time.main_java.today_data.todayAdapter;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/*Today fragment*/
public class Today_Fragment extends Fragment implements View.OnClickListener {
    String TAG ="Today_Fragment";
    private View view;
    MyDialog_Add_Data myDialog;
    todayAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton done_button;
    Manage_Today_Data manage_today_data;
    com.getbase.floatingactionbutton.FloatingActionButton Button_a, Button_b;


    @Nullable
    @Override
    //创建view
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.today_fragment_view, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        init_view();
        init_adapter();
        Delet_Data delet_data = new Delet_Data();
    }

    public void init_view() {
        myDialog = new MyDialog_Add_Data();
        recyclerView = view.findViewById(R.id.recycle_view);
        done_button = myDialog.dia_view.findViewById(R.id.done_load);
    }

    public void init_adapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new todayAdapter();
        recyclerView.setAdapter(adapter);
        done_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        myDialog.set_bean_Date();
    }


    //新建事件dialog类
    class MyDialog_Add_Data {
        View dia_view;
        Dialog cus_dialog;
        DatePicker datePicker;
        TimePicker timePicker;
        TextView start_time, end_time, day;
        EditText input_word;
        String Email=new String();
        CheckBox checkBox3_shock, checkBox1_clock, checkBox2_tanchuang;
        public Calendar Start_Time = Calendar.getInstance();
        public Calendar End_Time = Calendar.getInstance();
        public int Alarm_Type = -1;
        public String word;
        //today_datas_Strc today_datas_strc =new today_datas_Strc();
        int flag1 = 0;


        MyDialog_Add_Data() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // 布局填充器
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            dia_view = inflater.inflate(R.layout.diglog, null);
            // 设置自定义的对话框界面
            builder.setView(dia_view);
            cus_dialog = builder.create();
            input_word = dia_view.findViewById(R.id.input_word);
            Button_a = view.findViewById(R.id.action_a);
            Button_a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cus_dialog.show();
                    input_word.setText(null);
                }
            });
            init_view();
            set_time_click_date();
            set_word_Data();


        }

        public void init_view() {
            datePicker = dia_view.findViewById(R.id.date_pick);
            timePicker = dia_view.findViewById(R.id.time_picker);
            timePicker.setEnabled(false);
            start_time = dia_view.findViewById(R.id.start_time);
            start_time.setText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE));
            end_time = dia_view.findViewById(R.id.end_time);
            end_time.setText(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE));
            day = dia_view.findViewById(R.id.day);
            checkBox1_clock = dia_view.findViewById(R.id.clock);
            checkBox2_tanchuang = dia_view.findViewById(R.id.tanchuang);
            checkBox3_shock = dia_view.findViewById(R.id.shock);
            TextView day = dia_view.findViewById(R.id.day);
        }


        /**
         * 未完成
         */
        public void set_month() {
            day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "现在不想加，有空再说吧", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //设置时间
        public void set_time_click_date() {
            start_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag1 == 0) {
                        timePicker.setVisibility(VISIBLE);
                        timePicker.setEnabled(true);
                        start_time.setTextColor(getResources().getColor(R.color.primaryDarkColor));
                        end_time.setTextColor(getResources().getColor(R.color.secondaryTextColor));
                        day.setVisibility(INVISIBLE);
                        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                            @Override
                            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                                String str;
                                Start_Time.set(Start_Time.get(Calendar.YEAR), Start_Time.get(Calendar.MONTH), Start_Time.get(Calendar.DATE), hourOfDay, minute);
                                str = hourOfDay + ":" + minute;
                                start_time.setText(str);

                            }
                        });

                        flag1 = 1;
                    }
                }
            });

            end_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag1 == 1) {
                        timePicker.setVisibility(VISIBLE);
                        timePicker.setEnabled(true);
                        end_time.setTextColor(getResources().getColor(R.color.primaryDarkColor));
                        start_time.setTextColor(getResources().getColor(R.color.secondaryTextColor));
                        day.setVisibility(INVISIBLE);
                        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                            @Override
                            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                                End_Time.set(End_Time.get(Calendar.YEAR), End_Time.get(Calendar.MONTH), End_Time.get(Calendar.DATE), hourOfDay, minute);
                                String str;
                                str = hourOfDay + ":" + minute;
                                end_time.setText(str);
                            }
                        });
                        flag1 = 0;
                    }
                }
            });
        }

        //设置word
        public void set_word_Data() {
            input_word.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    word = s.toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }

        //设置alarm_type
        public void set_Alarm_Type() {

            if (checkBox2_tanchuang.isChecked()) {
                Alarm_Type = 0;
            }
            if (checkBox3_shock.isChecked()) {
                Alarm_Type = 1;
            }
            if (checkBox1_clock.isChecked()) {
                Alarm_Type = 2;
            }
            if (checkBox2_tanchuang.isChecked() && checkBox3_shock.isChecked()) {
                Alarm_Type = 3;
            }
            if (checkBox2_tanchuang.isChecked() && checkBox1_clock.isChecked()) {
                Alarm_Type = 4;
            }
            if (checkBox1_clock.isChecked() && checkBox3_shock.isChecked()) {
                Alarm_Type = 5;
            }
            if (checkBox3_shock.isChecked() && checkBox1_clock.isChecked() && checkBox2_tanchuang.isChecked()) {
                Alarm_Type = 6;
            }

        }

        public void set_bean_Date() {
            User_bean user_bean=new User_bean();
            Manage_user manage_user=new Manage_user();
            user_bean=manage_user.Query_From_Db();
            Email=user_bean.getemail();
            set_Alarm_Type();
//                     设置数据
            Today_Data today_data1 = new Today_Data();

            today_data1.set_start_data(Start_Time);
            today_data1.set_end_data(End_Time);
            today_data1.set_word(word);
            today_data1.setAlarm_type(Alarm_Type);
            today_data1.set_Email(Email);

            if (End_Time.getTimeInMillis()-Start_Time.getTimeInMillis()>600000 && Start_Time.getTimeInMillis() >= System.currentTimeMillis() && judge()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String s=new String();
                        s="#####"+Start_Time.getTimeInMillis()+"==="+End_Time.getTimeInMillis()+"==="+Alarm_Type+"==="+word+"==="+Email+"==="+0;
                        Up_Today_Date up_today_date =new Up_Today_Date();
                        if(up_today_date.connectHost()){
                         up_today_date.sendData(s);
                         Log.d(TAG, "run: "+up_today_date.getData());
                        }else Log.d(TAG, "run: "+"不能连接服务器");
                    }
                }).start();
                adapter.add_data(today_data1);
                cus_dialog.cancel();
            } else if (End_Time.getTimeInMillis()-Start_Time.getTimeInMillis()<=600000) {
                Toast.makeText(getContext(), "间隔要大于10分钟呀", Toast.LENGTH_SHORT).show();
            } else if (Start_Time.getTimeInMillis() < System.currentTimeMillis()) {
                Toast.makeText(getContext(), "开始时间要在未来的某一天哦", Toast.LENGTH_SHORT).show();
            } else if (judge() == false) {
                Toast.makeText(getContext(), "要保证此段时间没有被占用", Toast.LENGTH_SHORT).show();
            }

        }




        public boolean judge() {
            List<Today_Data> t = new LinkedList<>();
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            Calendar c3 = Calendar.getInstance();
            Calendar c4 = Calendar.getInstance();
            Calendar c5 = Calendar.getInstance();
            Calendar c6 = Calendar.getInstance();
            List<Today_Data> temp_2 = new Manage_Today_Data().Get_From_Db();
            List<Today_Data>temp_1=new LinkedList<>();
            for(int i =0;i<temp_2.size();i++){
                if(!temp_2.isEmpty()&&temp_2.get(i).get_star_data().get(Calendar.DATE)>=Calendar.getInstance().get(Calendar.DATE))
                {
                    temp_1.add(temp_2.get(i));
                }
            }
            c1.setTimeInMillis(System.currentTimeMillis());

            if (!temp_1.isEmpty()) {
                for (int i = 0; i <= temp_1.size() - 1; i++) {

                    if (i == 0) {
                        Today_Data t0 = new Today_Data();
                        t0.set_start_data(c1);
                        t0.set_end_data(temp_1.get(i).get_star_data());
                        t.add(t0);
                        Log.d(TAG, "judge1: "+i);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
                        Log.d(TAG, "judge: "+formatter.format(t0.get_star_data().getTimeInMillis()));
                        Log.d(TAG, "judge: "+formatter.format(t0.get_end_data().getTimeInMillis())+"\n");



                    }

                    if (i > 0 && i < temp_1.size() - 1) {
                        Today_Data t1=new Today_Data();
                        Log.d(TAG, "judge2: "+i);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
                        if (temp_1.get(i).get_star_data().getTimeInMillis() - temp_1.get(i-1).get_end_data().getTimeInMillis() >= 60000) {
                            c2.setTimeInMillis(temp_1.get(i - 1).get_end_data().getTimeInMillis() + 60000);
                            c3.setTimeInMillis(temp_1.get(i).get_star_data().getTimeInMillis() - 60000);
                            t1.set_start_data(c2);
                            t1.set_end_data(c3);
                            t.add(t1);
                            Log.d(TAG, "judge: "+formatter.format(t1.get_star_data().getTimeInMillis()));
                            Log.d(TAG, "judge: "+formatter.format(t1.get_end_data().getTimeInMillis())+"\n");
                        }
                    }
                    if(i ==temp_1.size()-1){

                        Today_Data t4 =new Today_Data();
                        if (i!=0&&temp_1.get(i).get_star_data().getTimeInMillis() - temp_1.get(i-1).get_end_data().getTimeInMillis() >= 60000) {
                            Today_Data t3=new Today_Data();
                            c4.setTimeInMillis(temp_1.get(i - 1).get_end_data().getTimeInMillis() + 60000);
                            c5.setTimeInMillis(temp_1.get(i).get_star_data().getTimeInMillis() - 60000);
                            t3.set_start_data(c4);
                            t3.set_end_data(c5);
                            t.add(t3);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
                            Log.d(TAG, "judge: 执行t5");
                            Log.d(TAG, "judge: "+formatter.format(t3.get_star_data().getTimeInMillis()));
                            Log.d(TAG, "judge: "+formatter.format(t3.get_end_data().getTimeInMillis()));

                        }
                        c6.set(Calendar.YEAR, 2020);
                        t4.set_start_data(temp_1.get(i).get_end_data());
                        t4.set_end_data(c6);
                        t.add(t4);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
                        Log.d(TAG, "i==0,judge3: "+i);
                        Log.d(TAG, "judge: "+formatter.format(t4.get_star_data().getTimeInMillis()));
                        Log.d(TAG, "judge: "+formatter.format(t4.get_end_data().getTimeInMillis())+"\n");

                    }

//                    t1.add(t2);
                }
                for (int i = 0; i < t.size(); i++) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
//                    Log.d(TAG, "judge: "+t1.size());
//                    Log.d(TAG, "judge: "+"KAIS"+t1.get(i).get_star_data().get(Calendar.HOUR_OF_DAY));
//                    Log.d(TAG, "judge: "+"jieshu"+t1.get(i).get_end_data().get(Calendar.HOUR_OF_DAY));

                    Log.d(TAG, "时间片的开始: "+formatter.format(t.get(i).get_star_data().getTimeInMillis()));
                    Log.d(TAG, "时间片的结束: "+ formatter.format(t.get(i).get_end_data().getTimeInMillis()));
//
//                    Log.d(TAG, "要插的开始： "+formatter.format(Start_Time.getTimeInMillis()));
//                    Log.d(TAG, "要插的结束: "+formatter.format(End_Time.getTimeInMillis()));
                    if (Start_Time.getTimeInMillis() >= t.get(i).get_star_data().getTimeInMillis() && End_Time.getTimeInMillis() <= t.get(i).get_end_data().getTimeInMillis()) {
                        return true;
                    }

                }
                return false;
            }
            return true;
        }


    }

    //删除事件类
    class Delet_Data {
        int i = 0;

        Delet_Data() {
            manage_today_data = new Manage_Today_Data();
            Button_b = view.findViewById(R.id.action_b);

            delect_login();
        }

        public void delect_login() {
            Button_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i++;
                    if (i % 2 == 0) {
                        adapter.Edit_Model(0);
                        adapter.notifyDataSetChanged();
                    } else
                        adapter.Edit_Model(1);
                    adapter.notifyDataSetChanged();
                }
            });


        }
    }

//class Update_Data implements  todayAdapter.OnItemClickListener{
//        Update_Data(){
//
//        }
//        public  void Update_Date2(){
//            adapter.setOnItemClickListener(this);
//        }
//    public void update1(){
//        TimePicker timePicker;
//        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View dia_view = inflater.inflate(R.layout.dialog2, null);
//        // 设置自定义的对话框界面
//        builder.setView(dia_view);
//        Dialog cus_dialog = builder.create();
//        cus_dialog.show();
//
//    }
//
//    @Override
//    public void onClick(int position) {
//        update1();
//    }
//}
}//ending事件类


