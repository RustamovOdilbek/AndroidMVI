package com.exsample.androidmvi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.exsample.androidmvi.activity.helper.UpdateHelperImpl
import com.exsample.androidmvi.activity.intentstate.UpdateIntent
import com.exsample.androidmvi.activity.intentstate.UpdateState
import com.exsample.androidmvi.activity.viewmodel.UpdateViewModel
import com.exsample.androidmvi.activity.viewmodel.UpdateViewModelFactory
import com.exsample.androidmvi.databinding.ActivityUpdateBinding
import com.exsample.androidmvi.model.Post
import com.exsample.androidmvi.network.RetrofitHttp

import kotlinx.coroutines.launch
import kotlin.random.Random

class UpdateActivity : AppCompatActivity() {

    private lateinit var viewModel: UpdateViewModel
    private val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }

    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewModel()

        val post = intent.getSerializableExtra("post") as Post

        binding.etBody.setText(post.body)
        binding.etTitle.setText(post.title)
        id = post.id!!

        binding.apply {

            btnUpdate.setOnClickListener {
                if (etBody.text.isNotEmpty() && etTitle.text.isNotEmpty()) {
                    val post = Post(title = etTitle.text.toString(), body = etBody.text.toString(), userId = Random.nextInt())
                    updatePost(id, post)
                }
            }
        }

        observeViewModel()
    }

    private fun updatePost(id: Int, post: Post) {
        lifecycleScope.launch {
            viewModel.updateIntent.send(UpdateIntent.UpdatePost(id, post))
        }

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is UpdateState.Init -> Log.d("UpdateActivity", "Init")
                    is UpdateState.Loading -> {
                        binding.progress.visibility = View.VISIBLE

                        Log.d("UpdateActivity", "Loading")
                    }
                    is UpdateState.UpdatePost -> {
                        binding.progress.visibility = View.GONE
                        Log.d("UpdateActivity", "${it.post}")
                        finish()
                    }
                    is UpdateState.Error -> Log.d("UpdateActivity", "DeletePost" + it.error)
                }
            }
        }

    }

    private fun setupViewModel() {
        val factory = UpdateViewModelFactory(UpdateHelperImpl(RetrofitHttp.postService))
        viewModel = ViewModelProvider(this, factory)[UpdateViewModel::class.java]
    }
}