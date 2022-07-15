package com.exsample.androidmvi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.exsample.androidmvi.activity.helper.CreateHelperImpl
import com.exsample.androidmvi.activity.intentstate.CreateIntent
import com.exsample.androidmvi.activity.intentstate.CreateState
import com.exsample.androidmvi.activity.viewmodel.CreateViewModel
import com.exsample.androidmvi.activity.viewmodel.CreateViewModelFactory
import com.exsample.androidmvi.databinding.ActivityCreateBinding
import com.exsample.androidmvi.model.Post
import com.exsample.androidmvi.network.RetrofitHttp
import kotlinx.coroutines.launch
import kotlin.random.Random


class CreateActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCreateBinding.inflate(layoutInflater) }

    private lateinit var viewModel: CreateViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewModel()

        binding.apply {

            btnSave.setOnClickListener {
                if (etTitle.text.isNotEmpty() && etBody.text.isNotEmpty()) {
                    val title = etTitle.text.toString()
                    val body = etBody.text.toString()

                    val post = Post(title = title, body = body, userId = Random.nextInt())
                    createPost(post)

                }

            }

        }

        observeViewModel()


    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is CreateState.Init -> Log.d("CreateActivity", "Init")
                    is CreateState.Loading -> {
                        Log.d("CreateActivity", "Loading")
                        binding.progress.visibility = View.VISIBLE
                    }
                    is CreateState.CreatePost -> {
                        binding.progress.visibility = View.GONE
                        Log.d("CreateActivity", "${it.post}")
                        finish()

                    }
                    is CreateState.Error -> Log.d("CreateActivity", "DeletePost" + it.error)
                }
            }
        }
    }

    private fun createPost(post: Post) {
        lifecycleScope.launch {
            viewModel.createIntent.send(CreateIntent.CreatePost(post))
        }

    }

    private fun setupViewModel() {
        val factory = CreateViewModelFactory(CreateHelperImpl(RetrofitHttp.postService))
        viewModel = ViewModelProvider(this, factory)[CreateViewModel::class.java]
    }
}