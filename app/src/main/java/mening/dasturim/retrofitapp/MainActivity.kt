package mening.dasturim.retrofitapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import mening.dasturim.retrofitapp.adapter.InfoAdapter
import mening.dasturim.retrofitapp.adapter.PostAdapter
import mening.dasturim.retrofitapp.adapter.UserAdapterListener
import mening.dasturim.retrofitapp.databinding.ActivityMainBinding
import mening.dasturim.retrofitapp.model.DataPostItem
import mening.dasturim.retrofitapp.model.InfoItems
import mening.dasturim.retrofitapp.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var postAdapter: PostAdapter
    private lateinit var infoAdapter: InfoAdapter
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


        activityMainBinding.swipe.setOnRefreshListener(this)
        activityMainBinding.swipe.isRefreshing = true

        loadUsers()
        loadPost()

    }

    fun loadUsers() {
        ApiService.apiClient().getUsers()
            .enqueue(object : Callback<BaseResponse<List<DataPostItem>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<DataPostItem>>>,
                    response: Response<BaseResponse<List<DataPostItem>>>
                ) {
                    Log.d("ResponsU",response.body()?.data.toString())


                    activityMainBinding.swipe.isRefreshing = false
                    activityMainBinding.rvHorizontal.setHasFixedSize(true)
                    activityMainBinding.rvHorizontal.layoutManager =
                        LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
                    postAdapter = PostAdapter(response.body()?.data ?: emptyList(), object :
                        UserAdapterListener {
                        override fun onClick(item: DataPostItem) {
                            Log.d("OnClick click",item.toString())

                            val intent = Intent(this@MainActivity, PostActivity::class.java)
                            intent.putExtra("extra_data", item)
                            startActivity(intent)
                        }

                    })
                    activityMainBinding.rvHorizontal.adapter = postAdapter
                }

                override fun onFailure(call: Call<BaseResponse<List<DataPostItem>>>, t: Throwable) {
                    activityMainBinding.swipe.isRefreshing = false
                    Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun loadPost() {
        ApiService.apiClient().getPost().enqueue(object : Callback<BaseResponse<List<InfoItems>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<InfoItems>>>,
                response: Response<BaseResponse<List<InfoItems>>>
            ) {
                Log.d("Respons loadPost",response.body()?.data.toString())

                activityMainBinding.swipe.isRefreshing = false
                activityMainBinding.rvVertical.setHasFixedSize(true)
                activityMainBinding.rvVertical.layoutManager =
                    LinearLayoutManager(this@MainActivity)
                infoAdapter = InfoAdapter(response.body()?.data ?: listOf())
                activityMainBinding.rvVertical.adapter = infoAdapter

            }

            override fun onFailure(call: Call<BaseResponse<List<InfoItems>>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onRefresh() {
        loadUsers()
        loadPost()
    }

}