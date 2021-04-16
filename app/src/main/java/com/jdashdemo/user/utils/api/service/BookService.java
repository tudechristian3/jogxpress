package com.jdashdemo.user.utils.api.service;

import com.jdashdemo.user.json.CheckStatusTransaksiRequest;
import com.jdashdemo.user.json.CheckStatusTransaksiResponse;
import com.jdashdemo.user.json.DetailRequestJson;
import com.jdashdemo.user.json.DetailTransResponseJson;
import com.jdashdemo.user.json.GetNearRideCarRequestJson;
import com.jdashdemo.user.json.GetNearRideCarResponseJson;
import com.jdashdemo.user.json.ItemRequestJson;
import com.jdashdemo.user.json.LokasiDriverRequest;
import com.jdashdemo.user.json.LokasiDriverResponse;
import com.jdashdemo.user.json.RideCarRequestJson;
import com.jdashdemo.user.json.RideCarResponseJson;
import com.jdashdemo.user.json.SendRequestJson;
import com.jdashdemo.user.json.SendResponseJson;
import com.jdashdemo.user.json.fcm.CancelBookRequestJson;
import com.jdashdemo.user.json.fcm.CancelBookResponseJson;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Ourdevelops Team on 10/17/2019.
 */

public interface BookService {

    @POST("pelanggan/list_ride")
    Call<GetNearRideCarResponseJson> getNearRide(@Body GetNearRideCarRequestJson param);

    @POST("pelanggan/list_car")
    Call<GetNearRideCarResponseJson> getNearCar(@Body GetNearRideCarRequestJson param);

    @POST("pelanggan/request_transaksi")
    Call<RideCarResponseJson> requestTransaksi(@Body RideCarRequestJson param);

    @POST("pelanggan/inserttransaksimerchant")
    Call<RideCarResponseJson> requestTransaksiMerchant(@Body ItemRequestJson param);

    @POST("pelanggan/request_transaksi_send")
    Call<SendResponseJson> requestTransaksisend(@Body SendRequestJson param);

    @POST("pelanggan/check_status_transaksi")
    Call<CheckStatusTransaksiResponse> checkStatusTransaksi(@Body CheckStatusTransaksiRequest param);

    @POST("pelanggan/user_cancel")
    Call<CancelBookResponseJson> cancelOrder(@Body CancelBookRequestJson param);

    @POST("pelanggan/liat_lokasi_driver")
    Call<LokasiDriverResponse> liatLokasiDriver(@Body LokasiDriverRequest param);

    @POST("pelanggan/detail_transaksi")
    Call<DetailTransResponseJson> detailtrans(@Body DetailRequestJson param);


}
