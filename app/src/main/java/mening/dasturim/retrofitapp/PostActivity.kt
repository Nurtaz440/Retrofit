package mening.dasturim.retrofitapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout import mening.dasturim.retrofitapp.adapter.InfoAdapter
import mening.dasturim.retrofitapp.adapter.PostAdapter
import mening.dasturim.retrofitapp.databinding.ActivityPostBinding
import mening.dasturim.retrofitapp.model.DataPostItem
import mening.dasturim.retrofitapp.model.InfoItems
import mening.dasturim.retrofitapp.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener{

    lateinit var user :DataPostItem
    private  lateinit var activityMainBinding:ActivityPostBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        activityMainBinding.swipePost.setOnRefreshListener(this)
        activityMainBinding.swipePost.isRefreshing=true

        user = intent.getSerializableExtra("extra_data") as DataPostItem
        activityMainBinding.postName.text=user.firstName+ " " + user.lastName
        loadUsers()
    }

    override fun onRefresh() {
        loadUsers()
    }
    fun loadUsers(){
        activityMainBinding.swipePost.isRefreshing=false
        ApiService.apiClient().getPostsByUser(user.id).enqueue(object :Callback<BaseResponse<List<InfoItems>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<InfoItems>>>,
                response: Response<BaseResponse<List<InfoItems>>>
            ) {
                activityMainBinding.swipePost.isRefreshing=false
                activityMainBinding.rvPost.layoutManager=LinearLayoutManager(this@PostActivity)
                activityMainBinding.rvPost.adapter=InfoAdapter(response.body()?.data ?: listOf())
            }

            override fun onFailure(call: Call<BaseResponse<List<InfoItems>>>, t: Throwable) {
                activityMainBinding.swipePost.isRefreshing=false

            }

        })
    }
}