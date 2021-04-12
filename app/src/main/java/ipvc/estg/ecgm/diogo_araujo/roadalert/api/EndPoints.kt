package ipvc.estg.ecgm.diogo_araujo.roadalert.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface EndPoints {

    @FormUrlEncoded
    @POST("/myslim/users/login")
    fun login(@Field("username") username: String?, @Field("password") password: String?): Call<OutputPost>

    @FormUrlEncoded
    @POST("/myslim/users/regist")
    fun regist(@Field("username") username: String?, @Field("name") name: String?, @Field("email") email: String?, @Field("password") password: String?): Call<OutputPostRegist>
}