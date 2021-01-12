package Time.main_java;

import android.app.Application;
import android.content.Context;
/**获得app的context方便使用*/
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
