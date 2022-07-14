package com.exsample.androidmvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exsample.androidmvi.intentstate.MainIntenet
import com.exsample.androidmvi.intentstate.MainState
import com.exsample.androidmvi.repository.PostRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PostRepository): ViewModel() {

    val mainIntent = Channel<MainIntenet>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Init)
    val state: StateFlow<MainState> get() = _state

    var postId: Int = 0

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect{
                when(it){
                    is MainIntenet.AllPosts -> apiAllPosts()
                    is MainIntenet.DeletePost -> apiDeletePost()
                }
            }
        }
    }

    private fun apiAllPosts() {
     viewModelScope.launch {
         _state.value = MainState.Loading
         _state.value = try {
             MainState.AllPosts(repository.allPosts())
         }catch (e: Exception){
             MainState.Error(e.localizedMessage)
         }
     }
    }

    private fun apiDeletePost() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.DletePost(repository.deletePost(postId))
            }catch (e: Exception){
                MainState.Error(e.localizedMessage)
            }
        }
    }


}