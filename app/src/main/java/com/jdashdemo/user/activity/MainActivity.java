package com.jdashdemo.user.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.jdashdemo.user.R;
import com.jdashdemo.user.constants.BaseApp;
import com.jdashdemo.user.constants.Constants;
import com.jdashdemo.user.constants.VersionChecker;
import com.jdashdemo.user.fragment.FavouriteFragment;
import com.jdashdemo.user.fragment.HistoryFragment;
import com.jdashdemo.user.fragment.HomeFragment;
import com.jdashdemo.user.fragment.MessageFragment;
import com.jdashdemo.user.fragment.ProfileFragment;
import com.jdashdemo.user.models.User;
import com.jdashdemo.user.utils.GpsUtils;
import com.jdashdemo.user.utils.SettingPreference;
import com.jdashdemo.user.utils.api.ServiceGenerator;
import com.jdashdemo.user.utils.api.service.UserService;
import com.jdashdemo.user.models.FiturModel;
import com.jdashdemo.user.json.GetFiturResponseJson;


import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jdashdemo.user.utils.GpsUtils.GPS_REQUEST;


public class MainActivity extends AppCompatActivity {
    long mBackPressed;


    public static String apikey;

    LinearLayout mAdViewLayout;

    @SuppressLint("StaticFieldLeak")
    public static MainActivity mainActivity;
    private FragmentManager fragmentManager;
    BottomNavigationView navigation;
    int previousSelect = 0;
    private boolean isGPS = false;
    SharedPreferences prf;

    public static MainActivity getInstance() {
        return mainActivity;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Menu menu = navigation.getMenu();
            menu.findItem(R.id.home).setIcon(R.drawable.ic_home);
            menu.findItem(R.id.order).setIcon(R.drawable.ic_transaksi);
            menu.findItem(R.id.favourite).setIcon(R.drawable.ic_favourites);
            menu.findItem(R.id.chat).setIcon(R.drawable.ic_pesan);
            menu.findItem(R.id.profile).setIcon(R.drawable.ic_profil);
            switch (item.getItemId()) {
                case R.id.home:
                    checkstatus();
                    if (isGPS){
                        HomeFragment homeFragment = new HomeFragment();
                        navigationItemSelected(0);
                        item.setIcon(R.drawable.ic_home_s);
                        loadFrag(homeFragment, getString(R.string.menu_home), fragmentManager);
                    }

                    return true;
                case R.id.order:
                    HistoryFragment listFragment = new HistoryFragment();
                    navigationItemSelected(1);
                    item.setIcon(R.drawable.ic_transaksi_s);
                    loadFrag(listFragment, getString(R.string.menu_home), fragmentManager);
                    return true;
                case R.id.favourite:
                    FavouriteFragment favFragment = new FavouriteFragment();
                    navigationItemSelected(1);
                    item.setIcon(R.drawable.ic_favourite);
                    loadFrag(favFragment, getString(R.string.menu_favourite), fragmentManager);
                    return true;
                case R.id.chat:
                    MessageFragment pesanFragment = new MessageFragment();
                    navigationItemSelected(2);
                    item.setIcon(R.drawable.ic_pesan_s);
                    loadFrag(pesanFragment, getString(R.string.menu_home), fragmentManager);
                    return true;
                case R.id.profile:
                    ProfileFragment profilFragment = new ProfileFragment();
                    navigationItemSelected(3);
                    item.setIcon(R.drawable.ic_profil_s);
                    loadFrag(profilFragment, getString(R.string.menu_home), fragmentManager);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdViewLayout = findViewById(R.id.adView);
        fragmentManager = getSupportFragmentManager();
        navigation = findViewById(R.id.navigation);
        navigation.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);
        Menu menu = navigation.getMenu();
        menu.findItem(R.id.home).setIcon(R.drawable.ic_home_s);

//        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
//            @Override
//            public void gpsStatus(boolean isGPSEnable) {
//                // turn on GPS
//                isGPS = isGPSEnable;
//            }
//        });
        checkstatus();

        HomeFragment homeFragment = new HomeFragment();
        loadFrag(homeFragment, getString(R.string.menu_home), fragmentManager);
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        Constants.TOKEN = loginUser.getToken();
        Constants.USERID = loginUser.getId();
        apikey = getString(R.string.google_maps_key);

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Constants.versionname = Objects.requireNonNull(packageInfo).versionName;


    }

    private void checkstatus() {
        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Check_version();
        update();
    }

    public void Check_version() {
        VersionChecker versionChecker = new VersionChecker(this);
        versionChecker.execute();
    }

    @Override
    public void onBackPressed() {
        int count = this.getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            if (mBackPressed + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
            } else {
                clickDone();

            }
        } else {
            super.onBackPressed();
        }
    }

    public void clickDone() {
        new AlertDialog.Builder(this, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.exit))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        prf = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = prf.edit();
                        editor.putBoolean("showDialog", true);
                        editor.clear();
                        editor.apply();
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    public void loadFrag(Fragment f1, String name, FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.Container, f1, name);
        ft.commit();
    }

    public void navigationItemSelected(int position) {
        previousSelect = position;
    }

    private void update() {
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        UserService userService = ServiceGenerator.createService(UserService.class,
                loginUser.getEmail(), loginUser.getPassword());
        userService.getFitur().enqueue(new Callback<GetFiturResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetFiturResponseJson> call, @NonNull Response<GetFiturResponseJson> response) {
                if (response.isSuccessful()) {
                    Realm realm = BaseApp.getInstance(MainActivity.this).getRealmInstance();
                    realm.beginTransaction();
                    realm.delete(FiturModel.class);
                    realm.copyToRealm(Objects.requireNonNull(response.body()).getData());
                    realm.commitTransaction();

                    Constants.CURRENCY = response.body().getCurrencyModel();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetFiturResponseJson> call, @NonNull Throwable t) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPS = true; // flag maintain before get location
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
//                HomeFragment homeFragment = new HomeFragment();
//                loadFrag(homeFragment, getString(R.string.menu_home), fragmentManager);
//                User loginUser = BaseApp.getInstance(this).getLoginUser();
//                Constants.TOKEN = loginUser.getToken();
//                Constants.USERID = loginUser.getId();
//                apikey = getString(R.string.google_maps_key);
            }
        }
    }

}
