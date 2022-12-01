package com.rateApi.rohitApi.service

import com.rateApi.rohitApi.jsonData.Markets
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("getbm2")
    fun getFancyData(@Query("eventId") id: String): Call<Markets>

    @GET("getbm2")
    fun getBookMaker(@Query("eventId") id: String): Call<Markets>
}
