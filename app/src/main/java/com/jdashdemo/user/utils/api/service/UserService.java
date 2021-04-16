package com.jdashdemo.user.utils.api.service;

import com.jdashdemo.user.json.AllMerchantByNearResponseJson;
import com.jdashdemo.user.json.AllMerchantbyCatRequestJson;
import com.jdashdemo.user.json.AllMerchantsRequestJson;
import com.jdashdemo.user.json.AllMerchantsResponseJson;
import com.jdashdemo.user.json.AllTransResponseJson;
import com.jdashdemo.user.json.BankResponseJson;
import com.jdashdemo.user.json.BeritaDetailRequestJson;
import com.jdashdemo.user.json.BeritaDetailResponseJson;
import com.jdashdemo.user.json.ChangePassRequestJson;
import com.jdashdemo.user.json.DetailRequestJson;
import com.jdashdemo.user.json.EditprofileRequestJson;
import com.jdashdemo.user.json.GetAllMerchantbyCatRequestJson;
import com.jdashdemo.user.json.GetFiturResponseJson;
import com.jdashdemo.user.json.GetHomeRequestJson;
import com.jdashdemo.user.json.GetHomeResponseJson;
import com.jdashdemo.user.json.GetMerchantbyCatRequestJson;
import com.jdashdemo.user.json.LoginRequestJson;
import com.jdashdemo.user.json.LoginResponseJson;
import com.jdashdemo.user.json.MerchantByCatResponseJson;
import com.jdashdemo.user.json.MerchantByIdResponseJson;
import com.jdashdemo.user.json.MerchantByNearResponseJson;
import com.jdashdemo.user.json.MerchantbyIdRequestJson;
import com.jdashdemo.user.json.PayMongoPaymentRequestJson;
import com.jdashdemo.user.json.PayMongoPaymentResponseJson;
import com.jdashdemo.user.json.PayMongoRequestJson;
import com.jdashdemo.user.json.PayMongoResponseJson;
import com.jdashdemo.user.json.PrivacyRequestJson;
import com.jdashdemo.user.json.PrivacyResponseJson;
import com.jdashdemo.user.json.PromoRequestJson;
import com.jdashdemo.user.json.PromoResponseJson;
import com.jdashdemo.user.json.RateRequestJson;
import com.jdashdemo.user.json.RateResponseJson;
import com.jdashdemo.user.json.RegisterRequestJson;
import com.jdashdemo.user.json.RegisterResponseJson;
import com.jdashdemo.user.json.ResponseJson;
import com.jdashdemo.user.json.SearchAllMerchantRequestJson;
import com.jdashdemo.user.json.SearchAllMerchantResponseJson;
import com.jdashdemo.user.json.SearchMerchantbyCatRequestJson;
import com.jdashdemo.user.json.TopupRequestJson;
import com.jdashdemo.user.json.TopupResponseJson;
import com.jdashdemo.user.json.WalletRequestJson;
import com.jdashdemo.user.json.WalletResponseJson;
import com.jdashdemo.user.json.WithdrawRequestJson;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public interface UserService {

    @POST("pelanggan/login")
    Call<LoginResponseJson> login(@Body LoginRequestJson param);

    @POST("pelanggan/kodepromo")
    Call<PromoResponseJson> promocode(@Body PromoRequestJson param);

    @POST("pelanggan/listkodepromo")
    Call<PromoResponseJson> listpromocode(@Body PromoRequestJson param);

    @POST("pelanggan/list_bank")
    Call<BankResponseJson> listbank(@Body WithdrawRequestJson param);

    @POST("pelanggan/changepass")
    Call<LoginResponseJson> changepass(@Body ChangePassRequestJson param);

    @POST("pelanggan/register_user")
    Call<RegisterResponseJson> register(@Body RegisterRequestJson param);

    @GET("pelanggan/detail_fitur")
    Call<GetFiturResponseJson> getFitur();

    @POST("pelanggan/forgot")
    Call<LoginResponseJson> forgot(@Body LoginRequestJson param);

    @POST("pelanggan/privacy")
    Call<PrivacyResponseJson> privacy(@Body PrivacyRequestJson param);

    @POST("pelanggan/home")
    Call<GetHomeResponseJson> home(@Body GetHomeRequestJson param);

    @POST("pelanggan/topupstripe")
    Call<TopupResponseJson> topup(@Body TopupRequestJson param);

    @POST("pelanggan/paymongo_source")
    Call<PayMongoResponseJson> paymongo(@Body PayMongoRequestJson param);

    @POST("pelanggan/paymongo_payments")
    Call<PayMongoPaymentResponseJson> payment(@Body PayMongoPaymentRequestJson param);

    @POST("pelanggan/withdraw")
    Call<ResponseJson> withdraw(@Body WithdrawRequestJson param);

    @POST("pelanggan/topuppaypal")
    Call<ResponseJson> topuppaypal(@Body WithdrawRequestJson param);

    @POST("pelanggan/rate_driver")
    Call<RateResponseJson> rateDriver(@Body RateRequestJson param);

    @POST("pelanggan/edit_profile")
    Call<RegisterResponseJson> editProfile(@Body EditprofileRequestJson param);

    @POST("pelanggan/wallet")
    Call<WalletResponseJson> wallet(@Body WalletRequestJson param);

    @POST("pelanggan/history_progress")
    Call<AllTransResponseJson> history(@Body DetailRequestJson param);

    @POST("pelanggan/detail_berita")
    Call<BeritaDetailResponseJson> beritadetail(@Body BeritaDetailRequestJson param);

    @POST("pelanggan/all_berita")
    Call<BeritaDetailResponseJson> allberita(@Body BeritaDetailRequestJson param);

    @POST("pelanggan/merchantbykategoripromo")
    Call<MerchantByCatResponseJson> getmerchanbycat(@Body GetMerchantbyCatRequestJson param);

    @POST("pelanggan/merchantbykategori")
    Call<MerchantByNearResponseJson> getmerchanbynear(@Body GetMerchantbyCatRequestJson param);

    @POST("pelanggan/allmerchantbykategori")
    Call<AllMerchantByNearResponseJson> getallmerchanbynear(@Body GetAllMerchantbyCatRequestJson param);

    @POST("pelanggan/itembykategori")
    Call<MerchantByIdResponseJson> getitembycat(@Body GetAllMerchantbyCatRequestJson param);

    @POST("pelanggan/searchmerchant")
    Call<AllMerchantByNearResponseJson> searchmerchant(@Body SearchMerchantbyCatRequestJson param);

    @POST("pelanggan/allmerchant")
    Call<AllMerchantByNearResponseJson> allmerchant(@Body AllMerchantbyCatRequestJson param);

    @POST("pelanggan/merchantbyid")
    Call<MerchantByIdResponseJson> merchantbyid(@Body MerchantbyIdRequestJson param);

    @POST("pelanggan/merchants")
    Call<AllMerchantsResponseJson> merchants(@Body AllMerchantsRequestJson param);

    @POST("pelanggan/merchants_search")
    Call<SearchAllMerchantResponseJson> searchallmerchant(@Body SearchAllMerchantRequestJson param);

}
