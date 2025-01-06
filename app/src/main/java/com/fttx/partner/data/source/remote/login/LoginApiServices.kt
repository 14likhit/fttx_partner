package com.fttx.partner.data.source.remote.login

import com.fttx.partner.data.model.LoginDto
import com.fttx.partner.data.network.util.EndPoints.Login.FORM_KEY_DEVICE_ID
import com.fttx.partner.data.network.util.EndPoints.Login.FORM_KEY_PASSWORD
import com.fttx.partner.data.network.util.EndPoints.Login.FORM_KEY_USERNAME
import com.fttx.partner.data.network.util.EndPoints.Login.LOGIN
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiServices {

    @FormUrlEncoded
    @POST(LOGIN)
    suspend fun login(
        @Field(FORM_KEY_USERNAME) username: String,
        @Field(FORM_KEY_PASSWORD) password: String,
        @Field(FORM_KEY_DEVICE_ID) deviceId: String
    ): Response<LoginDto>
}