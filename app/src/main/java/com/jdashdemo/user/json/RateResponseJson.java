package com.jdashdemo.user.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ourdevelops Team on 11/28/2019.
 */

public class RateResponseJson {
    @Expose
    @SerializedName("message")
    public String mesage;

    @Expose
    @SerializedName("data")
    public List<String> data = new ArrayList<>();


}
