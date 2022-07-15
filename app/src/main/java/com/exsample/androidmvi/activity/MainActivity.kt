package com.exsample.androidmvi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.exsample.androidmvi.R
import com.exsample.androidmvi.activity.helper.MainHelperIml
import com.exsample.androidmvi.activity.intentstate.MainIntenet
import com.exsample.androidmvi.activity.intentstate.MainState
import com.exsample.androidmvi.activity.viewmodel.MainViewModel
import com.exsample.androidmvi.activity.viewmodel.MainViewModelFactory
import com.exsample.androidmvi.adapter.PostAdapter
import com.exsample.androidmvi.databinding.ActivityMainBinding
import com.exsample.androidmvi.model.Post
import com.exsample.androidmvi.network.RetrofitHttp
import com.exsample.androidmvi.utils.Utils
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var recyclerView: RecyclerView

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val postAdapter by lazy { PostAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        binding.recyclerView.adapter = postAdapter


        binding.ivAddPost.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }

        postAdapter.longClick = {
            deletePostDialog(it)
        }

        postAdapter.click = {
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("post", it)
            startActivity(intent)
        }

        observeViewModel()

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it) {
                    is MainState.Init -> Log.d("MainActivity", "Init")
                    is MainState.Loading -> {
                        binding.progress.visibility = View.VISIBLE

                        Log.d("MainActivity", "Loading")
                    }
                    is MainState.AllPosts -> {
                        binding.progress.visibility = View.GONE
                        Log.d("MainActivity", "${it.posts}")
                        postAdapter.submitData(it.posts)
                    }
                    is MainState.DletePost -> {
                        binding.progress.visibility = View.GONE
                        Log.d("MainActivity", "${it.post}")

                        intentAllPosts()
                    }
                    is MainState.Error -> Log.d("MainActivity", "DeletePost"+ it.error)
                }
            }
        }
    }



    private fun initViews() {
        val factory = MainViewModelFactory(MainHelperIml(RetrofitHttp.postService))
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerView)

        intentAllPosts()



    }

    private fun intentAllPosts() {
        lifecycleScope.launch {
            viewModel.mainIntent.send(MainIntenet.AllPosts)
        }
    }

    fun intentDeletePost(id: Int) {
        viewModel.postId = id
        lifecycleScope.launch {
            viewModel.mainIntent.send(MainIntenet.DeletePost)
        }
    }


    private fun deletePostDialog(post: Post) {
        val title = "Delete"
        val body = "Do you want to delete?"
        Utils.customDialog(this, title, body, object : Utils.DialogListener {
            override fun onPositiveClick() {
                intentDeletePost(post.id!!)
            }

            override fun onNegativeClick() {

            }
        })
    }
}