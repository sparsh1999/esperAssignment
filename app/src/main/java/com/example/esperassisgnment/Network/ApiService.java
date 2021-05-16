package com.example.esperassisgnment.Network;

import com.example.esperassisgnment.Models.Responses.DataResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("esper-assignment/db/")
    Call<DataResponse> requestData();



}