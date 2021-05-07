package ipvc.estg.ecgm.diogo_araujo.roadalert.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface EndPoints {

    @FormUrlEncoded
    @POST("/myslim/users/login")
    fun login(@Field("username") username: String?, @Field("password") password: String?): Call<OutputPost>

    @FormUrlEncoded
    @POST("/myslim/users/regist")
    fun regist(@Field("username") username: String?,
               @Field("name") name: String?,
               @Field("email") email: String?,
               @Field("password") password: String?
               ): Call<OutputPostRegist>

    @GET("/myslim/reports")
    fun getReports(): Call<List<OutputReports>>

    @FormUrlEncoded
    @POST("/myslim/reports/tit")
    fun getReportsTIT(): Call<List<OutputReports>>

    @FormUrlEncoded
    @POST("/myslim/report")
    fun getReportEdit(@Field("id") id: Int?
                     ): Call<OutputReports>

    @FormUrlEncoded
    @POST("/myslim/reports/add")
    fun addReport(@Field("username") username: String?,
                  @Field("title") title: String,
                  @Field("situation") situation: String,
                  @Field("location") location: String,
                  @Field("image") image: String,
                  @Field("date") date: String
                  ): Call<OutputReport>

    @FormUrlEncoded
    @POST("/myslim/reports/update")
    fun editReport(@Field("id") id: String?,
                  @Field("username") username: String,
                  @Field("title") title: String,
                  @Field("situation") situation: String,
                  @Field("location") location: String,
                  @Field("image") image: String,
                  @Field("date") date: String
                  ): Call<OutputReport>

    @FormUrlEncoded
    @POST("/myslim/reports/delete")
    fun deleteReport(@Field("id") id: String?
                    ): Call<OutputReport>
}