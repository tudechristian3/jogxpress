package com.jdashdemo.user.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.jdashdemo.user.R;
import com.jdashdemo.user.constants.BaseApp;
import com.jdashdemo.user.json.PayMongoPaymentRequestJson;
import com.jdashdemo.user.json.PayMongoPaymentResponseJson;
import com.jdashdemo.user.models.User;
import com.jdashdemo.user.utils.api.ServiceGenerator;
import com.jdashdemo.user.utils.api.service.UserService;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GCashActivity extends AppCompatActivity {

    private String amount;
    private String id;
    private String nars;
    private String type;
    private RelativeLayout rlnotif, rlnotif2;
    private TextView notif, notif2;
    private String phone;
    private String name;
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_cash);

        WebView mywebview = (WebView) findViewById(R.id.webView);
        rlnotif = findViewById(R.id.rlnotif);
        notif = findViewById(R.id.textnotif);
        rlnotif2 = findViewById(R.id.rlnotif2);
        notif2 = findViewById(R.id.textnotif2);
        back_btn = findViewById(R.id.back_btn);


        nars = getIntent().getStringExtra("LINK");
        id = getIntent().getStringExtra("ID");
        amount = getIntent().getStringExtra("AMOUNT");
        type = getIntent().getStringExtra("TYPE");
        phone = getIntent().getStringExtra("PHONE");
        name = getIntent().getStringExtra("NAME");


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mywebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals("https://besicleta.com/api/pelanggan/paymongo_success")) {
                    dagandirinars();
//                    finish();
                } else if (url.equals("https://besicleta.com/api/pelanggan/paymongo_fail")) {
                    notif("error, there was a problem, please try again!");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notif("error, there was a problem, please try again!");
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 3000);


                }

                System.out.println(url);
            }
        });
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.loadUrl(nars);

    }


    private void dagandirinars() {
        final User user = BaseApp.getInstance(this).getLoginUser();
        PayMongoPaymentRequestJson request = new PayMongoPaymentRequestJson();
        request.setIdUser(user.getId());
        request.setIdSource(id);
        request.setType(type);
        request.setName(name);
        request.setPhone(phone);
        request.setAmount(Integer.valueOf(amount));

        UserService service = ServiceGenerator.createService(UserService.class, user.getNoTelepon(), user.getPassword());
        service.payment(request).enqueue(new Callback<PayMongoPaymentResponseJson>() {
            @Override
            public void onResponse(Call<PayMongoPaymentResponseJson> call, Response<PayMongoPaymentResponseJson> response) {
                if (response.isSuccessful()) {
                    if (Objects.requireNonNull(response.body()).getMessage().equalsIgnoreCase("Success")) {
                        notif2("Topup have been successful");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                notif2("Topup have been successful");
                            }
                        });
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(GCashActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }, 3000);
                    } else {
                        notif("error, there was a problem, please try again!");
                    }
                } else {
                    notif("error!");
                }
            }

            @Override
            public void onFailure(Call<PayMongoPaymentResponseJson> call, Throwable t) {

            }
        });

    }

    public void notif(String text) {
        rlnotif.setVisibility(View.VISIBLE);
        notif.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif.setVisibility(View.GONE);
            }
        }, 3000);
    }

    @SuppressLint("NewApi")
    public void notif2(String text) {
        rlnotif2.setVisibility(View.VISIBLE);
        notif2.setText(text);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                rlnotif2.setVisibility(View.GONE);
            }
        }, 3000);
    }

}