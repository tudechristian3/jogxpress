package com.jdashdemo.user.fragment;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jdashdemo.user.R;
import com.jdashdemo.user.activity.AllBeritaActivity;
import com.jdashdemo.user.activity.IntroActivity;
import com.jdashdemo.user.activity.MainActivity;
import com.jdashdemo.user.activity.PromoActivity;
import com.jdashdemo.user.activity.SeeAllNearbyMerchantActivity;
import com.jdashdemo.user.activity.TopupSaldoActivity;
import com.jdashdemo.user.activity.WalletActivity;
import com.jdashdemo.user.constants.BaseApp;
import com.jdashdemo.user.item.AllFiturItem;
import com.jdashdemo.user.item.BeritaItem;
import com.jdashdemo.user.item.CatMerchantItem;
import com.jdashdemo.user.item.CatMerchantNearItem;
import com.jdashdemo.user.item.FiturItem;
import com.jdashdemo.user.item.MerchantItem;
import com.jdashdemo.user.item.MerchantNearItem;
import com.jdashdemo.user.item.RatingItem;
import com.jdashdemo.user.item.SliderItem;
import com.jdashdemo.user.json.BeritaDetailRequestJson;
import com.jdashdemo.user.json.BeritaDetailResponseJson;
import com.jdashdemo.user.json.GetHomeRequestJson;
import com.jdashdemo.user.json.GetHomeResponseJson;
import com.jdashdemo.user.json.GetMerchantbyCatRequestJson;
import com.jdashdemo.user.json.MerchantByCatResponseJson;
import com.jdashdemo.user.json.MerchantByNearResponseJson;
import com.jdashdemo.user.models.AllFiturModel;
import com.jdashdemo.user.models.BeritaModel;
import com.jdashdemo.user.models.CatMerchantModel;
import com.jdashdemo.user.models.FiturDataModel;
import com.jdashdemo.user.models.FiturModel;
import com.jdashdemo.user.models.MerchantModel;
import com.jdashdemo.user.models.MerchantNearModel;
import com.jdashdemo.user.models.PayuModel;
import com.jdashdemo.user.models.User;
import com.jdashdemo.user.utils.DatabaseHelper;
import com.jdashdemo.user.utils.GpsUtils;
import com.jdashdemo.user.utils.SettingPreference;
import com.jdashdemo.user.utils.Utility;
import com.jdashdemo.user.utils.api.ServiceGenerator;
import com.jdashdemo.user.utils.api.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import io.realm.Realm;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jdashdemo.user.utils.GpsUtils.GPS_REQUEST;
import static java.util.Objects.requireNonNull;

public class HomeFragment extends Fragment {

    private Context context;
    private ViewPager viewPager, rvreview;
    private SliderItem adapter;
    private Integer[] colors = null;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private CircleIndicator circleIndicator, circleIndicatorreview;
    private RecyclerView rvCategory, rvberita,rvmerchant,rvcatmerchantpromo,rvcatmerchantnear,rvmerchantnear;
    private LinearLayout llslider, promoslider, llrating, llberita,llmerchant, llmerchantnear,shimlistpromo,shimlistcatpromo,shimlistnear,shimlistcatnear;
    private FiturItem fiturItem;
    private RatingItem ratingItem;
    private BeritaItem beritaItem;
    private MerchantItem merchantItem;
    private MerchantNearItem merchantNearItem;
    private CatMerchantNearItem catMerchantNearItem;
    private CatMerchantItem catMerchantItem;
    private ShimmerFrameLayout mShimmerCat, shimerPromo, shimerreview, shimberita,shimmerchantpromo,getShimmerchantnear;
    private TextView saldo;
    private TextView nodatapromo;
    private TextView nodatanear;
    private SettingPreference sp;
    private List<MerchantModel> click;
    private List<MerchantNearModel> clicknear;
    private ArrayList<FiturDataModel> fiturlist;
    private List<FiturModel> fiturdata;
    private List<AllFiturModel> allfiturdata;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private Location locationN;

    private  List<BeritaModel> disclaimerfeature;


    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private double wayLatitude,wayLongitude;
    private boolean isGPS = false;
    private Handler sliderHandler = new Handler();

    private Timer timer;
    private int current_position = 0;
    boolean dialogShown = true;

    private Boolean opendialog = false;

    private int feature_category = 0;

    SharedPreferences pref;

    //for disclaimer
    TextView title, tanggal, kategori;
    String Id;
    WebView description;
    ImageView backButton, images, favourite;
    RelativeLayout rlprogress;
    private DatabaseHelper databaseHelper;
    ArrayList<BeritaModel> list;

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View getView = inflater.inflate(R.layout.fragment_home, container, false);

        context = getContext();
        View bottom_sheet = getView.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        viewPager = getView.findViewById(R.id.viewPager);
        circleIndicator = getView.findViewById(R.id.indicator_unselected_background);
        circleIndicatorreview = getView.findViewById(R.id.indicator_unselected_background_review);
        viewPager = getView.findViewById(R.id.viewPager);
        rvCategory = getView.findViewById(R.id.category);
        rvreview = getView.findViewById(R.id.viewPagerreview);
        rvberita = getView.findViewById(R.id.berita);
        rvmerchant = getView.findViewById(R.id.merchantpromo);
        rvcatmerchantpromo = getView.findViewById(R.id.catmerchantpromo);
        rvcatmerchantnear = getView.findViewById(R.id.catmerchantnear);
        promoslider = getView.findViewById(R.id.rlslider);
        llslider = getView.findViewById(R.id.promoslider);
        saldo = getView.findViewById(R.id.saldo);
        RelativeLayout topup = getView.findViewById(R.id.topup);
//        RelativeLayout withdraw = getView.findViewById(R.id.withdraw);
        RelativeLayout detail = getView.findViewById(R.id.detail);
        llberita = getView.findViewById(R.id.llnews);
        llmerchant = getView.findViewById(R.id.llmerchantpromo);
        llmerchantnear = getView.findViewById(R.id.llmerchantnear);
        llrating = getView.findViewById(R.id.llrating);
        TextView showall = getView.findViewById(R.id.showall);
        TextView showallNearbyMerchant = getView.findViewById(R.id.showallNearbyMerchant);
        shimlistpromo = getView.findViewById(R.id.shimlistpromo);
        shimlistnear = getView.findViewById(R.id.shimlistnear);
        nodatapromo = getView.findViewById(R.id.nodatapromo);
        shimlistcatpromo = getView.findViewById(R.id.shimlistcatpromo);
        shimlistcatnear = getView.findViewById(R.id.shimlistcatnear);
        rvcatmerchantnear = getView.findViewById(R.id.catmerchantnear);
        rvmerchantnear = getView.findViewById(R.id.merchantnear);
        nodatanear = getView.findViewById(R.id.nodatanear);
        sp = new SettingPreference(context);
        RelativeLayout promo = getView.findViewById(R.id.promo);

        //getData();

        //Disclaimer
        SharedPreferences preferences = context.getSharedPreferences("PREFS", 0);
        boolean ifShowDialog = preferences.getBoolean("showDialog", true);

        //String category_name = "Insider updates";
        String category_status = sp.getSetting()[17];
        String category_description = sp.getSetting()[15];

        String category_name = sp.getSetting()[16];

        String cod_true = preferences.getString("checked", "");
//        Toast.makeText(context, cod_true, Toast.LENGTH_SHORT).show();

        if(ifShowDialog && category_name.equals("Popup") && category_status.equals("1")){
            disclaimer();
        }




        fiturlist = new ArrayList<>();

        mShimmerCat = getView.findViewById(R.id.shimmercat);
        shimerPromo = getView.findViewById(R.id.shimmepromo);
        shimerreview = getView.findViewById(R.id.shimreview);
        shimberita = getView.findViewById(R.id.shimberita);
        shimmerchantpromo = getView.findViewById(R.id.shimmerchantpromo);
        getShimmerchantnear = getView.findViewById(R.id.shimmerchantnear);

        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new GridLayoutManager(getActivity(), 4));


        rvberita.setHasFixedSize(true);
        rvberita.setNestedScrollingEnabled(false);
        rvberita.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvmerchant.setHasFixedSize(true);
        rvmerchant.setNestedScrollingEnabled(false);
        rvmerchant.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//        rvmerchant.setLayoutManager(new GridLayoutManager(getActivity(),2));


        rvcatmerchantnear.setHasFixedSize(true);
        rvcatmerchantnear.setNestedScrollingEnabled(false);
        rvcatmerchantnear.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rvmerchantnear.setHasFixedSize(true);
        rvmerchantnear.setNestedScrollingEnabled(false);
//        rvmerchantnear.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvmerchantnear.setLayoutManager(new GridLayoutManager(getActivity(),2));

        rvcatmerchantpromo.setHasFixedSize(true);
        rvcatmerchantpromo.setNestedScrollingEnabled(false);
        rvcatmerchantpromo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));




        Integer[] colors_temp = {
                getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.transparent)
        };

        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TopupSaldoActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PromoActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

//        withdraw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, WithdrawActivity.class);
//                i.putExtra("type", "withdraw");
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//
//            }
//        });

        showall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AllBeritaActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
        showallNearbyMerchant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SeeAllNearbyMerchantActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, WalletActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rvreview.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (ratingItem.getCount() - 1) && position < (colors.length - 1)) {
                    rvreview.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                } else {
                    rvreview.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        statusCheck();
//        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(context);
//        mFusedLocation.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    locationN = location;
//                    gethome(location);
//                    shimmertutup();
//                    Constants.LATITUDE = location.getLatitude();
//                    Constants.LONGITUDE = location.getLongitude();
//                    Log.e("BEARING:", String.valueOf(location.getBearing()));
//                }
////                else
////                    statusCheck();
//            }
//        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

        locationRequest = LocationRequest.create();
        locationRequest.setNumUpdates(1);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000); // 1000 seconds
        locationRequest.setFastestInterval(5 * 1000); // 500 seconds

        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    checkgps();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        gethome(location);
                    }
                }
            }
        };
        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(locationRequest, mLocationCallback, null);



//        getlocation();

        colors = colors_temp;
        shimmershow();



        return getView;

    }




    private void disclaimer(){
        SharedPreferences preferences = context.getSharedPreferences("PREFS", 0);
        String cod_true = preferences.getString("checked", "");
        String category_name = sp.getSetting()[16];
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_disclaimer);
        dialog.setCancelable(true);

//        final String berita = disclaimerfeature

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final ImageView close = dialog.findViewById(R.id.bt_close);
//        final LinearLayout email = dialog.findViewById(R.id.email);
//        final LinearLayout phone = dialog.findViewById(R.id.phone);
//        final LinearLayout website = dialog.findViewById(R.id.website);
        //if(cod_true.equals("false") && category_name.equals("Insider updates")){
            final WebView about = dialog.findViewById(R.id.disclaimerfeature);
            String mimeType = "text/html";
            String encoding = "utf-8";
            String htmlText;
            htmlText = sp.getSetting()[15];
            String text = "<html dir=" + "><head>"
                    + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/Arial.ttf\")}body{font-family: MyFont;color: #000000;text-align:justify;line-height:1.2}"
                    + "</style></head>"
                    + "<body>"
                    + htmlText
                    + "</body></html>";

            about.loadDataWithBaseURL(null, text, mimeType, encoding, null);
        //}

        //}


//        phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final int REQUEST_PHONE_CALL = 1;
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:" + (sp.getSetting()[3])));
//                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
//                    } else {
//                        startActivity(callIntent);
//                    }
//                }
//            }
//        });
//
//        email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String[] TO = {(sp.getSetting()[2])};
//                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                emailIntent.setType("text/plain");
//
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "halo");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "email" + "\n");
//                try {
//                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(getActivity(),
//                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        website.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = (sp.getSetting()[4]);
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//            }
//        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SharedPreferences preferences = context.getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("showDialog", false);
                editor.apply();
                //storeDialogStatus();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }

//    private void storeDialogStatus(){
//        SharedPreferences mSharedPreferences = context.getSharedPreferences("CheckItem", MODE_PRIVATE);
//        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
//        mEditor.putBoolean("item", false);
//        mEditor.apply();
//    }
//
//    private boolean getDialogStatus(){
//        SharedPreferences mSharedPreferences = context.getSharedPreferences("CheckItem", MODE_PRIVATE);
//        return mSharedPreferences.getBoolean("item", false);
//    }





    private void checkgps() {

        new GpsUtils(context).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getlocation() {
            if (isGPS)
                mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) context, location -> {
                    if (location != null) {
                        wayLatitude = location.getLatitude();
                        wayLongitude = location.getLongitude();
//                        txtLocation.setText(String.format(Locale.US, "%s - %s", wayLatitude, wayLongitude));
                        gethome(location);
                    }
                    else{
                        checkgps();
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                    startActivity(intent);
                    }
                });
            else {
                checkgps();
            }

    }


    public void statusCheck() {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }
    }
    public void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("GPS Disabled");
        builder.setMessage("Gps is disabled, in order to use the application properly you need to enable GPS of your device");
        builder.setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 123);
            }
        }).setNegativeButton("No, Just Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buildAlertMessageNoGps();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
//            updateLastLocation();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
        if (requestCode == GPS_REQUEST) {
            isGPS = true;
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

//            getlocation();
        }
    }


    private void shimmershow() {
        rvCategory.setVisibility(View.GONE);
        rvreview.setVisibility(View.GONE);
        rvberita.setVisibility(View.GONE);
        rvmerchant.setVisibility(View.GONE);
        rvmerchantnear.setVisibility(View.GONE);
        rvcatmerchantpromo.setVisibility(View.GONE);
        shimmerchantpromo.startShimmerAnimation();
        getShimmerchantnear.startShimmerAnimation();
        shimberita.startShimmerAnimation();
        mShimmerCat.startShimmerAnimation();
        shimerreview.startShimmerAnimation();
        shimerPromo.startShimmerAnimation();
        saldo.setVisibility(View.GONE);
        //disclaimer();
    }

    private void shimmertutup() {
        rvreview.setVisibility(View.VISIBLE);
        rvCategory.setVisibility(View.VISIBLE);
        rvberita.setVisibility(View.VISIBLE);
        rvmerchant.setVisibility(View.VISIBLE);
        rvcatmerchantpromo.setVisibility(View.VISIBLE);
        rvcatmerchantnear.setVisibility(View.VISIBLE);
        rvmerchantnear.setVisibility(View.VISIBLE);
        shimberita.stopShimmerAnimation();
        shimberita.setVisibility(View.GONE);
        shimmerchantpromo.stopShimmerAnimation();
        shimmerchantpromo.setVisibility(View.GONE);
        mShimmerCat.setVisibility(View.GONE);
        mShimmerCat.stopShimmerAnimation();
        shimerreview.setVisibility(View.GONE);
        shimerreview.stopShimmerAnimation();
        shimerPromo.setVisibility(View.GONE);
        shimerPromo.stopShimmerAnimation();
        getShimmerchantnear.stopShimmerAnimation();
        getShimmerchantnear.setVisibility(View.GONE);
        saldo.setVisibility(View.VISIBLE);
    }

    private void gethome(final Location location) {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        GetHomeRequestJson param = new GetHomeRequestJson();
        param.setId(loginUser.getId());
        param.setLat(String.valueOf(location.getLatitude()));
        param.setLon(String.valueOf(location.getLongitude()));
        param.setPhone(loginUser.getNoTelepon());
        userService.home(param).enqueue(new Callback<GetHomeResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<GetHomeResponseJson> call, @NonNull Response<GetHomeResponseJson> response) {
                if (response.isSuccessful()) {
                    if (requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        shimmertutup();
                        PayuModel payu = response.body().getPayu().get(0);
                        BeritaModel berita = response.body().getBerita().get(0);
                        sp.updateCurrency(response.body().getCurrency());
                        sp.updateabout(response.body().getAboutus());
                        sp.updateemail(response.body().getEmail());
                        sp.updatephone(response.body().getPhone());
                        sp.updateweb(response.body().getWebsite());
                        sp.updatePaypal(response.body().getPaypalkey());
                        sp.updatepaypalmode(response.body().getPaypalmode());
                        sp.updatepaypalactive(response.body().getPaypalactive());
                        sp.updatestripeactive(response.body().getStripeactive());
                        sp.updatecurrencytext(response.body().getCurrencytext());
                        sp.updatePayudebug(payu.getPayudebug());
                        sp.updatePayumerchantid(payu.getPayuid());
                        sp.updatePayusalt(payu.getPayusalt());
                        sp.updatePayumerchantkey(payu.getPayukey());
                        sp.updatePayuActive(payu.getActive());
                        sp.updatedisclaimer(berita.getContent());
                        sp.updatedisclaimercategory(berita.getKategori());
                        sp.updatedisclaimerstatus(berita.getStatus_berita());
                        //sp.updatedisclaimercategoryid(berita.getid_kategori());

                        //beritaItem
//                        String latest_news = String.valueOf(berita.getKategori().equals("Insider updates"));
//                        String latest_content = berita.getContent();
//                        String latest = berita.toString();
//
//                        SharedPreferences preferences = context.getSharedPreferences("PREFS", 0);
//                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.putString("checked", latest);
//                        editor.commit();

                        Utility.currencyTXT2(saldo, response.body().getSaldo(), context);

                        if (response.body().getSlider().isEmpty()) {
                            llslider.setVisibility(View.GONE);
                        } else {
                            promoslider.setVisibility(View.VISIBLE);
                            adapter = new SliderItem(response.body().getSlider(), getActivity());
                            viewPager.setAdapter(adapter);
                            sliderHandler.removeCallbacks(sliderRunnable);
                            sliderHandler.postDelayed(sliderRunnable, 3000);
                            circleIndicator.setViewPager(viewPager);
                            viewPager.setPadding(50, 0, 50, 0);

                        }
                        fiturdata = response.body().getFitur();
                        allfiturdata = response.body().getAllfitur();
                        for (int i = 0; i < fiturdata.size(); i++) {
                            FiturDataModel fiturmodel = new FiturDataModel();
                            fiturmodel.setIdFitur(fiturdata.get(i).getIdFitur());
                            fiturmodel.setFitur(fiturdata.get(i).getFitur());
                            fiturmodel.setIcon(fiturdata.get(i).getIcon());
                            fiturmodel.setHome(fiturdata.get(i).getHome());
                            fiturlist.add(fiturmodel);
                        }

                        if (fiturdata.size() > 6) {
                            FiturDataModel fiturmodel = new FiturDataModel();
                            fiturmodel.setIdFitur(100);
                            fiturmodel.setFitur("More");
                            fiturmodel.setHome("0");
                            fiturlist.add(fiturmodel);
                        }

                        fiturItem = new FiturItem(getActivity(), fiturlist, R.layout.item_fitur, new FiturItem.OnItemClickListener() {
                            @Override
                            public void onItemClick(FiturDataModel item) {
                                sheetlist();
                            }
                        });
                        rvCategory.setAdapter(fiturItem);
                        if (response.body().getRating().isEmpty()) {
                            llrating.setVisibility(View.GONE);
                        } else {

                            ratingItem = new RatingItem(response.body().getRating(), context);
                            rvreview.setAdapter(ratingItem);
                            circleIndicatorreview.setViewPager(rvreview);
                            rvreview.setPadding(50, 0, 50, 0);
                        }
                        if (response.body().getBerita().isEmpty()) {
                            llberita.setVisibility(View.GONE);
                        } else {
                            beritaItem = new BeritaItem(getActivity(), response.body().getBerita(), R.layout.item_grid);
                            rvberita.setAdapter(beritaItem);
                        }


                        if (response.body().getMerchantpromo().isEmpty()) {
                            llmerchant.setVisibility(View.GONE);
                        } else {

                            click = response.body().getMerchantpromo();
                            merchantItem = new MerchantItem(getActivity(), click, R.layout.item_merchant);
                            rvmerchant.setAdapter(merchantItem);
                            catMerchantItem = new CatMerchantItem(getActivity(), response.body().getCatmerchant(), R.layout.item_cat_merchant, new CatMerchantItem.OnItemClickListener() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onItemClick(final CatMerchantModel item) {

                                    click.clear();
                                    shimlistpromo.setVisibility(View.VISIBLE);
                                    shimmerchantpromo.setVisibility(View.VISIBLE);
                                    shimlistcatpromo.setVisibility(View.GONE);
                                    rvmerchant.setVisibility(View.GONE);
                                    nodatapromo.setVisibility(View.GONE);
                                    shimmerchantpromo.startShimmerAnimation();
                                    FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(context);
                                    mFusedLocation.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            if (location != null) {
                                                getmerchntbycatpromo(location, item.getId_kategori_merchant());
                                            }
                                        }
                                    });

                                }
                            });
                            rvcatmerchantpromo.setAdapter(catMerchantItem);

                        }



                        if (response.body().getMerchantnear().isEmpty()) {
                            llmerchantnear.setVisibility(View.GONE);
                        } else {
                            clicknear = response.body().getMerchantnear();
                            merchantNearItem = new MerchantNearItem(getActivity(), clicknear, R.layout.item_merchant);
                            rvmerchantnear.setAdapter(merchantNearItem);

                            catMerchantNearItem = new CatMerchantNearItem(getActivity(), response.body().getCatmerchant(), R.layout.item_cat_merchant, new CatMerchantNearItem.OnItemClickListener() {
                                @SuppressLint("MissingPermission")
                                @Override
                                public void onItemClick(final CatMerchantModel item) {
                                    clicknear.clear();
                                    shimlistnear.setVisibility(View.VISIBLE);
                                    getShimmerchantnear.setVisibility(View.VISIBLE);
                                    shimlistcatnear.setVisibility(View.GONE);
                                    rvmerchantnear.setVisibility(View.GONE);
                                    nodatanear.setVisibility(View.GONE);
                                    getShimmerchantnear.startShimmerAnimation();
                                    FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(context);
                                    mFusedLocation.getLastLocation().addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                                        @Override
                                        public void onSuccess(Location location) {
                                            if (location != null) {
                                                getmerchntbycatnear(location, item.getId_kategori_merchant());
                                            }
                                        }
                                    });
                                }
                            });

                            rvcatmerchantnear.setAdapter(catMerchantNearItem);
                        }
                        User user = response.body().getData().get(0);
                        saveUser(user);
                        if (HomeFragment.this.getActivity() != null) {
                            Realm realm = BaseApp.getInstance(HomeFragment.this.getActivity()).getRealmInstance();
                            User loginUser = BaseApp.getInstance(HomeFragment.this.getActivity()).getLoginUser();
                            realm.beginTransaction();
                            loginUser.setWalletSaldo(Long.parseLong(response.body().getSaldo()));
                            realm.commitTransaction();
                            //disclaimer();
                        }

                    } else {
                        Realm realm = BaseApp.getInstance(context).getRealmInstance();
                        realm.beginTransaction();
                        realm.delete(User.class);
                        realm.commitTransaction();
                        BaseApp.getInstance(context).setLoginUser(null);
                        startActivity(new Intent(context, IntroActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        requireActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetHomeResponseJson> call, @NonNull Throwable t) {

            }
        });
    }



    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        }
    };

//    private void createSlide(){
//        final Handler handler = new Handler();
//        final Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                current_position = 0;
//                viewPager.setCurrentItem(current_position++, true);
//            }
//        };
//
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(runnable);
//            }
//        }, 250, 2500);
//    }


    private void getmerchntbycatpromo(final Location location, String cat) {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        GetMerchantbyCatRequestJson param = new GetMerchantbyCatRequestJson();
        param.setId(loginUser.getId());
        param.setLat(String.valueOf(location.getLatitude()));
        param.setLon(String.valueOf(location.getLongitude()));
        param.setPhone(loginUser.getNoTelepon());
        param.setKategori(cat);
        userService.getmerchanbycat(param).enqueue(new Callback<MerchantByCatResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<MerchantByCatResponseJson> call, @NonNull Response<MerchantByCatResponseJson> response) {
                if (response.isSuccessful()) {
                    if (requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        click = response.body().getData();
                        shimmerchantpromo.setVisibility(View.GONE);
                        rvmerchant.setVisibility(View.VISIBLE);
                        shimmerchantpromo.stopShimmerAnimation();
                        if (response.body().getData().isEmpty()) {
                            nodatapromo.setVisibility(View.VISIBLE);
                            rvmerchant.setVisibility(View.GONE);
                        } else {
                            nodatapromo.setVisibility(View.GONE);
                            merchantItem = new MerchantItem(getActivity(), click, R.layout.item_merchant);
                            rvmerchant.setAdapter(merchantItem);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MerchantByCatResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    private void getmerchntbycatnear(final Location location, String cat) {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        UserService userService = ServiceGenerator.createService(
                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
        GetMerchantbyCatRequestJson param = new GetMerchantbyCatRequestJson();
        param.setId(loginUser.getId());
        param.setLat(String.valueOf(location.getLatitude()));
        param.setLon(String.valueOf(location.getLongitude()));
        param.setPhone(loginUser.getNoTelepon());
        param.setKategori(cat);
        userService.getmerchanbynear(param).enqueue(new Callback<MerchantByNearResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<MerchantByNearResponseJson> call, @NonNull Response<MerchantByNearResponseJson> response) {
                if (response.isSuccessful()) {
                    if (requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
                        clicknear = response.body().getData();
                        getShimmerchantnear.setVisibility(View.GONE);
                        rvmerchantnear.setVisibility(View.VISIBLE);
                        getShimmerchantnear.stopShimmerAnimation();
                        if (response.body().getData().isEmpty()) {
                            nodatanear.setVisibility(View.VISIBLE);
                            rvmerchantnear.setVisibility(View.GONE);
                        } else {
                            nodatanear.setVisibility(View.GONE);
                            merchantNearItem = new MerchantNearItem(getActivity(), clicknear, R.layout.item_grid_full);
                            rvmerchantnear.setAdapter(merchantNearItem);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MerchantByNearResponseJson> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        Utility.currencyTXT2(saldo, String.valueOf(loginUser.getWalletSaldo()), context);

    }

    private void saveUser(User user) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.delete(User.class);
        realm.copyToRealm(user);
        realm.commitTransaction();
        BaseApp.getInstance(context).setLoginUser(user);
        //disclaimer();
    }

    private void sheetlist() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        @SuppressLint("InflateParams") final View mDialog = getLayoutInflater().inflate(R.layout.sheet_category, null);
        RecyclerView view = mDialog.findViewById(R.id.category);

        view.setHasFixedSize(true);
        view.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        AllFiturItem allfiturItem = new AllFiturItem(getActivity(), allfiturdata, R.layout.item_fitur);
        view.setAdapter(allfiturItem);

        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setContentView(mDialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requireNonNull(mBottomSheetDialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }

   // private void getData() {
//        User loginUser = BaseApp.getInstance(context).getLoginUser();
//        UserService userService = ServiceGenerator.createService(
//                UserService.class, loginUser.getNoTelepon(), loginUser.getPassword());
//        GetHomeRequestJson param = new GetHomeRequestJson();
//        param.setId(loginUser.getId());
//        param.setPhone(loginUser.getNoTelepon());
//        userService.home(param).enqueue(new Callback<GetHomeResponseJson>() {
//            @Override
//            public void onResponse(@NonNull Call<GetHomeResponseJson> call, @NonNull Response<GetHomeResponseJson> response) {
//                if (response.isSuccessful()) {
//                    if (requireNonNull(response.body()).getMessage().equalsIgnoreCase("success")) {
//                        BeritaModel berita = response.body().getBerita().get(0);
//                        //sp.updatedisclaimer(berita.getContent());
//                        berita.getContent();
//                        Toast.makeText(getContext(), berita.getContent(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<GetHomeResponseJson> call, @NonNull Throwable t) {
//
//            }
//        });
//    }




}
