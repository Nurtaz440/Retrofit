package mening.dasturim.retrofitapp.retrofit


import mening.dasturim.retrofitapp.BaseResponse
import mening.dasturim.retrofitapp.model.CommentsItems
import mening.dasturim.retrofitapp.model.DataPostItem
import mening.dasturim.retrofitapp.model.InfoItems
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface Api {

    @Headers ("app-id:61cb49c1a5d0066d9c2d65d1")
    @GET ("user")
    fun getUsers(): retrofit2.Call<BaseResponse<List<DataPostItem>>>

    @Headers ("app-id:61cb49c1a5d0066d9c2d65d1")
    @GET ("post")
    fun getPost(): retrofit2.Call<BaseResponse<List<InfoItems>>>

    @Headers ("app-id:61cb49c1a5d0066d9c2d65d1")
    @GET ("user/{id}/post")
    fun getPostsByUser(@Path("id") id : String) : retrofit2.Call<BaseResponse<List<InfoItems>>>

    @Headers("app-id:61cb49c1a5d0066d9c2d65d1")
    @GET("post/{com_id}/comment")
    fun getComments(@Path("com_id") id : String) :retrofit2.Call<BaseResponse<List<CommentsItems>>>
}