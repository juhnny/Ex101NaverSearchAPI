package com.juhnny.ex101naversearchapi.network

import android.view.Display
import com.juhnny.ex101naversearchapi.model.NaverSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {
    // header 정보를 전달하는 방법은 두가지
    // 1. 파라미터로 직접 써서 전달하는 방법
    // 2. 한번에 Map으로 전달하는 방법

    // 1. 파라미터로 직접 써서 전달하는 방법
    @GET("/v1/search/shop.json")
    fun getShoppingSearchDataByString(@Header("X-Naver-Client-Id") clientId:String,
                                      @Header("X-Naver-Client-Secret") clientSecret:String,
                                      @Query("query") query:String,
                                      @Query("display") display: Int = 10,
                                      @Query("start") start:Int = 1,
                                      @Query("sort") sort:String = "sim"): Call<String>

    //header값이 자주 변경되는 게 아니라면 @Header 값을 파라미터로 전달받지 않고
    //@Headers()를 이용해 정적으로 값을 지정하는 게 편하다.
    @GET("/v1/search/shop.json")
    @Headers("X-Naver-Client-Id:eqcqvGF2mKixGnn_gfti", "X-Naver-Client-Secret:fUnNrqhDmO")
    fun getShoppingSearchDataByJson(@Query("query") query:String,
                                    @Query("display") display: Int = 10,
                                    @Query("start") start:Int = 1,
                                    @Query("sort") sort:String = "sim"): Call<NaverSearchResponse>
}
