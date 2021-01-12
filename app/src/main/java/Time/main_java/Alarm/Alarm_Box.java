package Time.main_java.Alarm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Time.main_java.R;
import Time.main_java.Synchronization.Up_Today_Date;
import Time.main_java.Theone.Manage_user;
import Time.main_java.Theone.User_bean;
import Time.main_java.today_data.Manage_Today_Data;
import Time.main_java.today_data.Today_Data;


public class Alarm_Box extends AppCompatActivity {
    private User_bean myuser=new User_bean();
    private Manage_user manage_user=new Manage_user();
    private Context mcontext;
    private Alarm_Shock t;
    private Alarm_Shock.Alarm alarm;
    private List<Today_Data> mToday_Data;
    private Time.main_java.libary.CircleImageView tt;
    private Manage_Today_Data m1=new Manage_Today_Data();
    private Vibrator vibrator;
    private static final String TAG = "Alarm_Box";
    private Button To_Do, Not_To_Do, Delay;
    private TextView St,En,Wor;
    private android.support.constraint.ConstraintLayout constraintLayout ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm__box);
        init();
        hideActionBar();
        setFullScreen();
    }
    private void hideActionBar() {
        // Hide UI
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * 设置全屏
     */
    private void setFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        mcontext = this;
        t = new Alarm_Shock();
        alarm = t.new Alarm();
        alarm.initMediaPlayer(mcontext);
        mToday_Data = new LinkedList<>();
        St=findViewById(R.id.t1_st);
        En=findViewById(R.id.t2en);
        Wor=findViewById(R.id.wo1);
        tt=findViewById(R.id.tt);
        constraintLayout=findViewById(R.id.bgimag);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Get_Data();
        Init_Button();
        Judge_Alarm_Type();
        Set_View_W();
        set_img();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void set_img(){
        Drawable a[]=new Drawable[9];
       a[0] =getDrawable(R.drawable.a);
       a[1]=getDrawable(R.drawable.b);
       a[2]=getDrawable(R.drawable.c);
       a[3]=getDrawable(R.drawable.d);
       a[4]=getDrawable(R.drawable.e);
       a[5]=getDrawable(R.drawable.f);
       a[6]=getDrawable(R.drawable.g);
       a[7]=getDrawable(R.drawable.h);
       a[8]=getDrawable(R.drawable.i);
        int min=1;
        int max=9;
        Random random = new Random();
        int num = random.nextInt(max)%(max-min+1) + min;
        num-=1;
        Log.d(TAG, "set_img: "+num);
        constraintLayout.setBackgroundDrawable(a[num]);
    }

    private void Init_Button() {
        To_Do = findViewById(R.id.yes);
        Not_To_Do = findViewById(R.id.no);
        Delay = findViewById(R.id.delay);
        if(!mToday_Data.isEmpty()){
            for(int i=0;i<mToday_Data.size();i++){
                if(mToday_Data.get(i).get_Finish_Type()==3){
                    Delay.setVisibility(View.INVISIBLE);
                    Delay.setClickable(false);
                }
            }
        }
        if(!mToday_Data.isEmpty()){
        To_Do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    Up_Today_Date up_today_date =new Up_Today_Date();
                    String b=new String();
                    @Override
                    public void run() {
                        if (up_today_date.connectHost()){
                            b="%%%%%"+mToday_Data.get(0).get_star_data().getTimeInMillis()+"==="+myuser.getemail()+"==="+1;
                            up_today_date.sendData(b);
                        }else Log.d(TAG, "run: "+"服务器不能连接");
                    }
                }).start();
                To_DO();
                Toast.makeText(mcontext,"收到",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Not_To_Do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    Up_Today_Date up_today_date =new Up_Today_Date();
                    String b=new String();
                    @Override
                    public void run() {
                        if (up_today_date.connectHost()){
                            b="%%%%%"+mToday_Data.get(0).get_star_data().getTimeInMillis()+"==="+myuser.getemail()+"==="+2;
                            up_today_date.sendData(b);
                        }else Log.d(TAG, "run: "+"服务器不能连接");
                    }
                }).start();
                No_To_Do();
                Toast.makeText(mcontext,"不想做了呀",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Delay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    Up_Today_Date up_today_date =new Up_Today_Date();
                    String b=new String();
                    @Override
                    public void run() {
                        if (up_today_date.connectHost()){
                            b="%%%%%"+mToday_Data.get(0).get_star_data().getTimeInMillis()+"==="+myuser.getemail()+"==="+3;
                            up_today_date.sendData(b);
                        }else Log.d(TAG, "run: "+"服务器不能连接");
                    }
                }).start();
                Dela();
                Toast.makeText(mcontext,"已延迟10分钟",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        }
    }
    private void To_DO(){
        if(!mToday_Data.isEmpty()){
            m1.Update_TO_Db(mToday_Data.get(0).get_star_data(),1,0);
        }
    }
    private void No_To_Do(){
        if(!mToday_Data.isEmpty()){
            m1.Update_TO_Db(mToday_Data.get(0).get_star_data(),2,0);
        }
    }
    private void Dela(){
        if(!mToday_Data.isEmpty()){
            m1.Update_TO_Db(mToday_Data.get(0).get_star_data(),3,1);
        }
    }


    private void Get_Data() {
        myuser=manage_user.Query_From_Db();
        List<Today_Data> temp = m1.Get_From_Db();
        Calendar t = Calendar.getInstance();
        for (int i = 0; i < temp.size(); i++) {
            if ((temp.get(i).get_Finish_Type() == 0||temp.get(i).get_Finish_Type()==3) && temp.get(i).get_star_data().get(Calendar.MONTH) == t.get(Calendar.MONTH) && temp.get(i).get_star_data().get(Calendar.DATE) == t.get(Calendar.DATE) && temp.get(i).get_star_data().get(Calendar.HOUR_OF_DAY) == t.get(Calendar.HOUR_OF_DAY) && temp.get(i).get_star_data().get(Calendar.MINUTE) == t.get(Calendar.MINUTE)) {
                mToday_Data.add(temp.get(i));
            }
        }

    }
    private void Set_View_W(){
        if(!mToday_Data.isEmpty()){
            St.setText(mToday_Data.get(0).get_star_data().get(Calendar.HOUR_OF_DAY)+":"+mToday_Data.get(0).get_star_data().get(Calendar.MINUTE));
            En.setText(mToday_Data.get(0).get_end_data().get(Calendar.HOUR_OF_DAY)+":"+mToday_Data.get(0).get_end_data().get(Calendar.MINUTE));
            Wor.setText(mToday_Data.get(0).getWord());
        }
        if(!myuser.getImagAddr().isEmpty()){
            tt.setImageBitmap(base64ToBitmap(myuser.getImagAddr()));
        }
    }

    private void Judge_Alarm_Type() {
        if (!mToday_Data.isEmpty()) {
            Log.d(TAG, String.valueOf(mToday_Data.get(0).getAlarm_type()));
            switch (mToday_Data.get(0).getAlarm_type()) {
                case 0:
                    Log.d(TAG, "Judge_Alarm_Type:弹窗了 ");
                    break;
                case 1:
                    vibrator.vibrate(new long[]{500, 500, 500, 500}, 1);
                    Log.d(TAG, "Judge_Alarm_Type: 振动了");
                    break;
                case 2:
                    alarm.Start_Music();
                    Log.d(TAG, "Judge_Alarm_Type: 响铃了");
                    break;
                case 3:
                    vibrator.vibrate(new long[]{500, 500, 500, 500}, 1);
                    Log.d(TAG, "Judge_Alarm_Type: 弹窗振动");
                    break;
                case 4:
                    alarm.Start_Music();
                    Log.d(TAG, "Judge_Alarm_Type: 弹窗响铃");
                    break;
                case 5:
                    alarm.Start_Music();
                    vibrator.vibrate(new long[]{500, 500, 500, 500}, 1);
                    Log.d(TAG, "Judge_Alarm_Type: 振动响铃");
                    break;
                case 6:
                    alarm.Start_Music();
                    vibrator.vibrate(new long[]{500, 500, 500, 500}, 1);
                    break;

            }
        }
    }
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        alarm.Stop_Music(mcontext);
        mToday_Data.clear();
        vibrator.cancel();
    }
}

