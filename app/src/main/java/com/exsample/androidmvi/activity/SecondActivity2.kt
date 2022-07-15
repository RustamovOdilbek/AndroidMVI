package com.exsample.androidmvi.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exsample.androidmvi.R
import com.exsample.androidmvi.activity.helper.UpdateHelperImpl
import com.exsample.androidmvi.model.Post
import com.exsample.androidmvi.network.RetrofitHttp
import kotlinx.coroutines.launch

class SecondActivity2 : AppCompatActivity() {
    private lateinit var viewModel: SecondViewModel
    lateinit var recyclerView: RecyclerView
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        initViews()
        obcerveViewModel()
    }

    private fun obcerveViewModel() {
        lifecycleScope.launch{
            viewModel.state.collect{
                when(it){
                    is SecondState.Init -> {
                        Log.d(TAG, "obcerveViewModel: Init")
                    }
                    is SecondState.Loading -> {
                        Log.d(TAG, "obcerveViewModel: Loading")
                    }
                    is SecondState.AllPosts -> {
                        Log.d(TAG, "obcerveViewModel: allposts ${it.posts.size}")
                        refreshAdapter(it.posts)
                    }
                    is SecondState.DletePost -> {
                        Log.d(TAG, "obcerveViewModel: delete ${it.post}")
                    }
                    is SecondState.Error -> {
                        Log.d(TAG, "obcerveViewModel: Error ${it.error}")
                    }
                }
            }
        }
    }

    private fun initViews() {
        val factory = SecondViewModelFactory(UpdateHelperImpl(RetrofitHttp.postService))

        viewModel = ViewModelProvider(this, factory).get(SecondViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        intenetAllPosts()

    }



    private fun intenetAllPosts(){
        lifecycleScope.launch{
            viewModel.secondIntenet.send(SecondIntenet.AllPosts)
        }
    }

    fun intenetDeletePost(id: Int){
        lifecycleScope.launch {
            viewModel.postId = id
            lifecycleScope.launch {
                viewModel.secondIntenet.send(SecondIntenet.DeletePost)
            }
        }
    }


    private fun refreshAdapter(posters: ArrayList<Post>) {
        val adapter = PostAdapter2(this, posters)
        recyclerView.adapter = adapter
    }

}