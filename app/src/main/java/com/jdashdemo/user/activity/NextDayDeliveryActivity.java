package com.jdashdemo.user.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.jdashdemo.user.R;
import com.jdashdemo.user.constants.BaseApp;
import com.jdashdemo.user.item.WalletItem;
import com.jdashdemo.user.json.WalletRequestJson;
import com.jdashdemo.user.json.WalletResponseJson;
import com.jdashdemo.user.models.User;
import com.jdashdemo.user.utils.api.ServiceGenerator;
import com.jdashdemo.user.utils.api.service.UserService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NextDayDeliveryActivity extends AppCompatActivity {

    private WebView webView;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_nextday);

        backbtn = findViewById(R.id.back_btn);

        webView = findViewById(R.id.webviewdelivery);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.jogx.ph/webview/index");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });



    }

}
