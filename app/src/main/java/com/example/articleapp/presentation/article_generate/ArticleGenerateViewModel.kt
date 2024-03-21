package com.example.articleapp.presentation.article_generate

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleGenerateViewModel @Inject constructor(
    private val generativeModel: GenerativeModel
) : ViewModel() {

    private val _articleGenerateState = mutableStateOf(ArticleGenerateState())
    val articleGenerateState : State<ArticleGenerateState> get() = _articleGenerateState

    fun generateArticle(prompt : String) {
        viewModelScope.launch {
            try {
                _articleGenerateState.value = ArticleGenerateState(isLoading = true)
                val result = generativeModel.generateContent(prompt)
                _articleGenerateState.value = ArticleGenerateState(data = result.text)
            } catch (e : Exception) {
                _articleGenerateState.value = ArticleGenerateState(error = e.localizedMessage)
            }
        }
    }

}