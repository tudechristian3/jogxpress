package com.jdashdemo.user.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jdashdemo.user.models.MerchantNearModel;

import java.util.List;

public class SearchAllMerchantResponseJson {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("merchants")
    @Expose
    private List<MerchantNearModel> merchants = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MerchantNearModel> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<MerchantNearModel> merchants) {
        this.merchants = merchants;
    }

}
