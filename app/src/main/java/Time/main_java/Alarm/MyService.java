package Time.main_java.Alarm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import Time.main_java.R;
import Time.main_java.TheFour.Today_Fragment;
import Time.main_java.today_data.Manage_Today_Data;
import Time.main_java.today_data.Today_Data;


public class MyService extends Service {
    private List<Today_Data> My_Date;
    private Context mcontext;
    private static final String TAG = "MyService";
    private static final String ID = "channel_1";
    private static final String NAME = "前台服务";


    public MyService() {
        mcontext = this;
    }

    /**
     * 设置数据
     */
    public void Set_Data() {
        List<Today_Data> temp;
        temp = new Manage_Today_Data().Get_From_Db();
        My_Date = new LinkedList<>();
        int i = 0;
        Calendar t = Calendar.getInstance();
        while (i <= temp.size() - 1) {
            if (!temp.isEmpty() && (temp.get(i).get_Finish_Type() == 0||temp.get(i).get_Finish_Type()==3) && temp.get(i).get_star_data().get(Calendar.MONTH) == t.get(Calendar.MONTH) && temp.get(i).get_star_data().get(Calendar.DATE) == t.get(Calendar.DATE) && temp.get(i).get_star_data().get(Calendar.HOUR_OF_DAY) == t.get(Calendar.HOUR_OF_DAY) && temp.get(i).get_star_data().get(Calendar.MINUTE) == t.get(Calendar.MINUTE)) {
                My_Date.add(temp.get(i));
            }
            i++;
        }
        while (i <= temp.size() - 1) {
            if (!temp.isEmpty() && (temp.get(i).get_Finish_Type() == 0||temp.get(i).get_Finish_Type()==3) && temp.get(i).get_end_data().get(Calendar.MONTH) == t.get(Calendar.MONTH) && temp.get(i).get_end_data().get(Calendar.DATE) == t.get(Calendar.DATE) && temp.get(i).get_end_data().get(Calendar.HOUR_OF_DAY) == t.get(Calendar.HOUR_OF_DAY) && temp.get(i).get_end_data().get(Calendar.MINUTE) == t.get(Calendar.MINUTE)) {
                My_Date.add(temp.get(i));
            }
            i++;
        }

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        if (Build.VERSION.SDK_INT >= 26) {
            setForeground26();
        } else {
            setForegroundLow();
        }


    }

    @TargetApi(26)
    private void setForeground26() {
        Intent intent = new Intent(this, Today_Fragment.class);
        PendingIntent pi =PendingIntent.getActivities(this,0, new Intent[]{intent},0);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(this, ID)
                .setContentTitle("时间碎片")
                .setContentText("山川是不卷收的文章，日月为你掌灯")
                .setSmallIcon(R.mipmap.tubiao)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.tubiao))
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);
    }

    private void setForegroundLow() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this).setContentTitle("收到一条重要通知")
                .setContentTitle("时间碎片正在运行")
                .setContentText("点击查看今日安排")
                .setSmallIcon(R.mipmap.tubiao)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.tubiao))
                .build();
        manager.notify(1, notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Send_Alarm_Message();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 此处仅为简单版，还未完全实现。。。。。。。。。。。。。。
     */


    @TargetApi(26)
    private void test(String m) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(ID, NAME, NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel(channel);
        Notification notification = new Notification.Builder(this, ID)
                .setContentText(m)
                .setSmallIcon(R.mipmap.tubiao)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.tubiao))
                .build();
        manager.notify(11111, notification);


    }

    private void test2(String m) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this).setContentTitle("收到一条重要通知")
                .setContentText(m)
                .setSmallIcon(R.mipmap.tubiao)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.tubiao))
                .build();
        manager.notify(11111, notification);
    }


    private void Send_Alarm_Message() {
        Set_Data();


        /**线程进行数据更新操作*/
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                while (true) {
                    Log.d(TAG, "run: ssssssssssssssss");
                    Set_Data();
                    if (!My_Date.isEmpty()) {
                        Intent intent = new Intent();
                        intent.setAction("Time.main_java.Alarm.MY");
                        intent.setComponent(new ComponentName("Time.main_java", "Time.main_java.Alarm.MyReceiver"));
                        PendingIntent pi = PendingIntent.getBroadcast(mcontext, 0, intent, 0);

                        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);


//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
                        //                System.out.println("s1"+formatter.format(System.currentTimeMillis()));
                        //                                       System.out.println("s0"+formatter.format(My_Date.get(i).get_star_data().getTimeInMillis()));
                        if (My_Date.get(0).getAlarm_type() == -1) {
                            Log.d(TAG, "run: 通知了");
                            if (Build.VERSION.SDK_INT >= 26) {
                                test(My_Date.get(0).getWord());
                            } else {
                                test2(My_Date.get(0).getWord());
                            }
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
                        } else am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
                    }
                    My_Date.clear();
                    try {
                        Thread.currentThread().sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "因系统资源占满，导致进程被杀，导致闹钟失效", Toast.LENGTH_LONG).show();
    }
}




