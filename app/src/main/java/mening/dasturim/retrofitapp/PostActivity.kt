package mening.dasturim.retrofitapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import mening.dasturim.retrofitapp.adapter.CommentAdapterListener
import mening.dasturim.retrofitapp.adapter.InfoAdapter
import mening.dasturim.retrofitapp.databinding.ActivityPostBinding
import mening.dasturim.retrofitapp.model.DataPostItem
import mening.dasturim.retrofitapp.model.InfoItems
import mening.dasturim.retrofitapp.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var infoAdapter: InfoAdapter
    lateinit var user: DataPostItem
    private lateinit var activityMainBinding: ActivityPostBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding= ActivityPostBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.swipePost.setOnRefreshListener(this)
        activityMainBinding.swipePost.isRefreshing = true

        user = intent.getSerializableExtra("extra_data") as DataPostItem
        activityMainBinding.postName.text = user.firstName + " " + user.lastName
        loadUsers()
    }

    override fun onRefresh() {
        loadUsers()
    }

    fun loadUsers() {
        activityMainBinding.swipePost.isRefreshing = false


        ApiService.apiClient().getPostsByUser(user.id)
            .enqueue(object : Callback<BaseResponse<List<InfoItems>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<InfoItems>>>,
                    response: Response<BaseResponse<List<InfoItems>>>
                ) {

                    activityMainBinding.swipePost.isRefreshing = false
                    activityMainBinding.rvPost.setHasFixedSize(true)
                    activityMainBinding.rvPost.layoutManager =
                        LinearLayoutManager(this@PostActivity)
                    infoAdapter = InfoAdapter(response.body()?.data ?: listOf(),object :
                        CommentAdapterListener {
                        override fun onClick(item: InfoItems) {

                        }

                    })
                    activityMainBinding.rvPost.adapter = infoAdapter
                }

                override fun onFailure(call: Call<BaseResponse<List<InfoItems>>>, t: Throwable) {
                    activityMainBinding.swipePost.isRefreshing = false

                }

            })
    }
}