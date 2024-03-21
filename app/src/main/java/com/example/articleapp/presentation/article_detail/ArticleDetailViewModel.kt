package com.example.articleapp.presentation.article_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articleapp.data.remote.response.ArticleProcessResponse
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.use_case.article.GetArticleState
import com.example.articleapp.domain.use_case.article.RemoveArticle
import com.example.articleapp.domain.use_case.article.SaveArticle
import com.example.articleapp.domain.use_case.article.ShareArticleLink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val saveArticle: SaveArticle,
    private val removeArticle: RemoveArticle,
    private val getArticleState: GetArticleState,
    private val shareArticleLink: ShareArticleLink
) : ViewModel() {

    private val _articleState = MutableStateFlow(ArticleState())
    val articleState : StateFlow<ArticleState> get() = _articleState

    fun fetchArticleState(articleId : Int) {
        viewModelScope.launch {
            try {
                val saved = getArticleState(articleId).saved
                _articleState.value = ArticleState(saved = saved)
            } catch (e : Exception) {
                _articleState.value = ArticleState(error = e.localizedMessage)
            }
        }
    }


    fun saveArticleData(article: Article) {
        viewModelScope.launch {
            try {
                val response = saveArticle(article)
                checkResponseStatus(response, article.id)
            } catch (e : Exception) {
                _articleState.value = ArticleState(error = e.localizedMessage)
            }
        }
    }

    fun removeArticleData(article: Article) {
        viewModelScope.launch {
            try {
                val response = removeArticle(article.id)
                checkResponseStatus(response, article.id)
            } catch (e : Exception) {
                _articleState.value = ArticleState(error = e.localizedMessage)
            }
        }
    }

    fun shareLink(url : String?) {
        url?.let { shareArticleLink(it) }
    }

    private fun checkResponseStatus(response: ArticleProcessResponse, id : Int) {
        if (response.statusCode == 201)
            fetchArticleState(id)
        if (response.statusCode == 404)
            _articleState.value = ArticleState(error = response.statusMessage)
    }

}