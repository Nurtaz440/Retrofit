package mening.dasturim.retrofitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import mening.dasturim.retrofitapp.adapter.CommentAdapter
import mening.dasturim.retrofitapp.databinding.ActivityCommentBinding
import mening.dasturim.retrofitapp.model.CommentsItems
import mening.dasturim.retrofitapp.model.InfoItems
import mening.dasturim.retrofitapp.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener{
    private lateinit var activityCommentBinding: ActivityCommentBinding
    private lateinit var commentAdapter:CommentAdapter
    lateinit var user: InfoItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCommentBinding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(activityCommentBinding.root)


        activityCommentBinding.swipeComment.setOnRefreshListener(this)
        activityCommentBinding.swipeComment.isRefreshing = true

        user = intent.getSerializableExtra("data_comment") as InfoItems

        loadUsers()
    }

    override fun onRefresh() {
        loadUsers()
    }

    fun loadUsers() {
        activityCommentBinding.swipeComment.isRefreshing = false


        ApiService.apiClient().getComments(user.id)
            .enqueue(object : Callback<BaseResponse<List<CommentsItems>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<CommentsItems>>>,
                    response: Response<BaseResponse<List<CommentsItems>>>
                ) {
                    Log.d("postUser", response.body()?.data.toString())

                    activityCommentBinding.swipeComment.isRefreshing = false
                    activityCommentBinding.rvComment.setHasFixedSize(true)
                    activityCommentBinding.rvComment.layoutManager =
                        LinearLayoutManager(this@CommentActivity)
                    commentAdapter = CommentAdapter(response.body()?.data ?: listOf())
                    activityCommentBinding.rvComment.adapter = commentAdapter
                }

                override fun onFailure(call: Call<BaseResponse<List<CommentsItems>>>, t: Throwable) {
                    activityCommentBinding.swipeComment.isRefreshing = false

                }

            })
    }
}