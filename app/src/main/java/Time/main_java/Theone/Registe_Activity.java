package Time.main_java.Theone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import Time.main_java.R;
/**注册activity*/
public class Registe_Activity extends AppCompatActivity {
    Context Rcontext=this;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registe);
        WebView webView= (WebView)findViewById(R.id.registe_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new JStinterface(this),"android");
        webView.loadUrl("file:///android_asset/web/registe.html");
        hideActionBar();

        setFullScreen();
    }
    public class JStinterface {
        Context mContext;
        JStinterface(Context c){
            mContext=c;
        }
        @JavascriptInterface
        public void toJump(int flag) {
            if(flag==1){
                Intent toLogin = new Intent(Registe_Activity.this, Login_Active.class);
                startActivity(toLogin);
                Registe_Activity.this.finish();
            }
            if(flag==2) Toast.makeText(Rcontext,"已注册",Toast.LENGTH_SHORT).show();
        }


    }
}
