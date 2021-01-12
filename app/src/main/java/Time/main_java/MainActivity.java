package Time.main_java;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import Time.main_java.Alarm.MyService;
import Time.main_java.Synchronization.Download_From_Server;
import Time.main_java.Theone.Login_Active;
import Time.main_java.Theone.Manage_user;
import Time.main_java.Theone.User_bean;
import Time.main_java.Theone.today;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

/**用于启动的主activity*/
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Manage_user user =new Manage_user();
    User_bean user_bean=new User_bean();
    @Override
    protected  void   onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        user_bean=user.Query_From_Db();
        if(user_bean.getemail().equals("")){
            //调用login_Active
            Intent startLogin = new Intent(MainActivity.this, Login_Active.class);
            startActivity(startLogin);
        }
        if(!user_bean.getemail().equals("")){
            Intent startToday = new Intent(MainActivity.this, today.class);
            startActivity(startToday);
        }


        Intent Start_server = new Intent(this, MyService.class);
        if(!isServiceRunning(this,"Time.main_java.Alarm.MyService"))
        {
            Log.d(TAG, "Start_server");
            startService(Start_server);
        }

        SQLiteStudioService.instance().start(this);

    }
    /**
     * 判断是否有服务在运行
     * */
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            if (serviceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}

