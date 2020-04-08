package com.vignesh.assignment;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MInterface {
    String JSONURL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    @GET("facts.json")
    Call<String> getString();
}
