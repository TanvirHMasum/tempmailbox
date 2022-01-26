package com.example.mailbox.api

import com.example.mailbox.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("accounts")
    fun addAccount(@Body createAccountData: CrateAccData): Call<CreateAccResponse>

    @POST("token")
    fun loginAccount(@Body loginAccData: LoginAccData): Call<LoginDataResponse>

    @GET("accounts/{userId}")
    fun getAccount(@Path("userId") userId:String) : Call<AccResponse>
}