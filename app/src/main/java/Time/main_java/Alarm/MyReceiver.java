package Time.main_java.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import Time.main_java.today_data.Manage_Today_Data;
import Time.main_java.today_data.Today_Data;

public class MyReceiver extends BroadcastReceiver {
    private Context mcontext;
    @Override
    public void onReceive(Context context, Intent intent) {
        mcontext=context;
//       Toast.makeText(context,"广播收到",Toast.LENGTH_SHORT).show();


        Intent intent1 =new Intent(context,Alarm_Box.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);


    }



}


