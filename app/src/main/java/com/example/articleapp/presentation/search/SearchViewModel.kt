package com.example.articleapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articleapp.data.cache.CacheManager
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.model.PopularArticle
import com.example.articleapp.domain.use_case.article.GetArticlesBySearch
import com.example.articleapp.domain.use_case.article.GetPopularArticles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getPopularArticles: GetPopularArticles,
    private val getArticlesBySearch: GetArticlesBySearch,
    private val cacheManager: CacheManager
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState : StateFlow<SearchState> get() = _searchState

    private val _searchArticleListState = MutableStateFlow<List<Article>?>(null)
    val searchArticleListState : StateFlow<List<Article>?> get() = _searchArticleListState.asStateFlow()

    private val cachedData = cacheManager.getData<List<PopularArticle>>("search_data")

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            if (cachedData.isNullOrEmpty()) {
                try {
                    _searchState.value = SearchState(isLoading = true)
                    val popularArticleList = getPopularArticles()
                    _searchState.value = SearchState(data = popularArticleList)
                } catch (e : HttpException) {
                    _searchState.value = SearchState(error = e.message())
                }
            } else {
                _searchState.value = SearchState(data = cachedData)
            }
        }
    }

    fun searchArticle(q : String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchArticleListState.value = getArticlesBySearch(q)
        }
    }

    fun resetSearchedArticleListState() {
        _searchArticleListState.value = null
    }

    fun cacheData(popularArticleList : List<PopularArticle>) {
        cacheManager.putData("search_data", popularArticleList)
    }

}