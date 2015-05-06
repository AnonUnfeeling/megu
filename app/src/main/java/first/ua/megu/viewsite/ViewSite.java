package first.ua.megu.viewsite;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import first.ua.megu.R;

public class ViewSite extends Activity{
    private WebView webView;
    private String Url;

    public static final String HOME_PAGE="http://cybers.regi.rovno.ua/";
    public static final String APPLICANT="http://cybers.regi.rovno.ua/?cat=4";
    public static final String EDUCATIONAL="http://cybers.regi.rovno.ua/?cat=8";
    public static final String DEPARTMENTS="http://cybers.regi.rovno.ua/?cat=5";
    public static final String CONTACT="http://cybers.regi.rovno.ua/?cat=16";
    public static final String SCIENTIFIC_WORK="http://cybers.regi.rovno.ua/?cat=7";
    public static final String ABOUT_AS="http://cybers.regi.rovno.ua/?cat=6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_site);

        setUrl(getIntent().getFlags());

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(Url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:document.getElementById('sidebar-primary').style.display = 'none';" +
                        "javascript:document.getElementById('header').style.display = 'none';"+
                        "javascript:document.getElementById('footer-widgets-container').style.display = 'none';"+
                        "javascript:document.getElementById('respond').style.display = 'none';"+
                        "javascript:document.getElementById('footer-container').style.display = 'none';");
            }
        });
    }

    public void setUrl (int flag){
        if(flag==0) {
            return;
        }else if(flag==1){
            Url= HOME_PAGE;
        }else if(flag==2){
            Url = APPLICANT;
        }else if(flag==3){
            Url = EDUCATIONAL;
        }else if(flag==4){
            Url = DEPARTMENTS;
        }else if(flag==5){
            Url = CONTACT;
        }else if(flag==6){
            Url = SCIENTIFIC_WORK;
        }else if(flag==7){
            Url = ABOUT_AS;
        }
    }

    @Override
    public void onBackPressed() {
        webView = (WebView) findViewById(R.id.webView);
        if(webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}

