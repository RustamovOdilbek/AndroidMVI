package com.exsample.androidmvi

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exsample.androidmvi.helper.MainHelperIml
import com.exsample.androidmvi.intentstate.MainIntenet
import com.exsample.androidmvi.intentstate.MainState
import com.exsample.androidmvi.network.RetrofitHttp
import com.exsample.androidmvi.viewmodel.MainViewModel
import com.exsample.androidmvi.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        obcerveViewModel()
    }

    private fun obcerveViewModel() {
        lifecycleScope.launch{
            viewModel.state.collect{
                when(it){
                    is MainState.Init -> {
                        Log.d(TAG, "obcerveViewModel: Init")
                    }
                    is MainState.Loading -> {
                        Log.d(TAG, "obcerveViewModel: Loading")
                    }
                    is MainState.AllPosts -> {
                        Log.d(TAG, "obcerveViewModel: allposts ${it.posts.size}")
                    }
                    is MainState.DletePost -> {
                        Log.d(TAG, "obcerveViewModel: delete ${it.post}")
                    }
                    is MainState.Error -> {
                        Log.d(TAG, "obcerveViewModel: Error ${it.error}")
                    }
                }
            }
        }
    }

    private fun initViews() {
        val factory = MainViewModelFactory(MainHelperIml(RetrofitHttp.postService))

        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        intenetAllPosts()

    }

    private fun intenetAllPosts(){
        lifecycleScope.launch{
            viewModel.mainIntent.send(MainIntenet.AllPosts)
        }
    }

    private fun intenetDeletePost(){
        lifecycleScope.launch{
            viewModel.mainIntent.send(MainIntenet.DeletePost)
        }
    }
}