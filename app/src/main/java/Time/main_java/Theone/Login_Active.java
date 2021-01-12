package Time.main_java.Theone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import Time.main_java.R;

/**Programing By Diao*/
/**登录的activity*/
public class Login_Active extends AppCompatActivity {
    Manage_user user =new Manage_user();

    /**隐藏标题头*/
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
    private static final String TAG = "Login_Active";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__active);

        WebView webView=(WebView) findViewById(R.id.login_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JStinterface(this),"android");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("file:///android_asset/web/login.html");
        hideActionBar();
        setFullScreen();

    }

    public class JStinterface {
        Context mContext;
        int flag=0;

        JStinterface(Context c){
            mContext=c;
        }
        @JavascriptInterface
        public void toJump(int flag) {
            this.flag=flag;
            if(this.flag==1||this.flag==4){
            Intent totoday =new Intent(Login_Active.this, today.class);
            startActivity(totoday);
            Login_Active.this.finish();
            }
            if(this.flag==2)
            {
            Intent toLogin = new Intent(Login_Active.this, Registe_Activity.class);
                startActivity(toLogin);
            }
            if(this.flag==3)
            {
                Intent toRegIntent = new Intent(Login_Active.this,Registe_Activity.class);
                startActivity(toRegIntent);
            }

        }
        @JavascriptInterface
        public void get_from_js(String Email, String Password){
           String a[]=new String[7];
           a[0]=Email;
           a[1]=Password;
           a[2]="";
           a[3]="";
           a[4]="";
           a[5]="1";
           a[6]="";
            Log.d(TAG, "get_from_js: "+Email);
            user.Insert_To_Db(null,a);
        }


    }
}
