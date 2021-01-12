package Time.main_java.TheFour;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import Time.main_java.R;
import Time.main_java.Theone.Login_Active;
import Time.main_java.today_data.Manage_Today_Data;
import Time.main_java.today_data.Today_Data;

public class intime_fragement extends Fragment {
    private View view;
    Context mContext=getActivity();
    List<Today_Data>mtoday;
    long used=0;
    long waste=0;
    WebView webView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.intime_fragement_view,container,false);
       return view;
    }

    private static final String TAG = "intime_fragement";
    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @SuppressLint("JavascriptInterface")
    public void init(){
        get_time();
        trans();
        webView=(WebView) view.findViewById(R.id.inttimeview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JStinterface(mContext),"android1");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("file:///android_asset/web/time.html");


    }
    public void get_time(){
        List<Today_Data> m =new Manage_Today_Data().Get_From_Db();
        if(!m.isEmpty()){
            for(int i=0;i<m.size();i++){
                if(m.get(i).get_Finish_Type()==1){
                   used=used+ (m.get(i).get_end_data().getTimeInMillis()-m.get(i).get_star_data().getTimeInMillis());
                }
                if(m.get(i).get_Finish_Type()==2){
                    waste=waste+(m.get(i).get_end_data().getTimeInMillis()-m.get(i).get_star_data().getTimeInMillis());
                }
            }
        }

    }
    public void trans(){
        used=used/60000;
        waste=waste/60000;
        Log.d(TAG, "trans: "+waste);
        Log.d(TAG, "trans: "+used);
    }

    class JStinterface{
        Context jContext;
        JStinterface(Context c){
            jContext=c;
        }
        @JavascriptInterface
        public long js_call_android_used(){

            return used;
        }
        @JavascriptInterface
        public long js_call_android_waste(){
            return waste;
        }

    }
}
