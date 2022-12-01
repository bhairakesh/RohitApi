package com.rateApi.rohitApi.service

import com.rateApi.rohitApi.jsonData.Markets
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("rohit/{id}")
    fun getFancyData(@Path("id") id: String): Call<Markets>


    @GET("rohit/{id}")
    fun getBookMaker(@Path("id") id: String): Call<Markets>
}