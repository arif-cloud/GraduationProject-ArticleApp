package com.example.articleapp.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articleapp.data.cache.CacheManager
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.use_case.article.GetSavedArticle
import com.example.articleapp.presentation.home.HomeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getSavedArticle: GetSavedArticle,
    private val cacheManager: CacheManager
) : ViewModel() {

    private val _savedState = MutableStateFlow(SavedState())
    val savedState : StateFlow<SavedState> get() = _savedState

    private val cachedData = cacheManager.getData<List<Article>>("saved_data")

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            if (cachedData.isNullOrEmpty()) {
                try {
                    _savedState.value = SavedState(isLoading = true)
                    val savedArticleList = getSavedArticle()
                    _savedState.value = SavedState(data = savedArticleList)
                } catch (e : Exception) {
                    _savedState.value = SavedState(error = e.localizedMessage)
                }
            } else {
                _savedState.value = SavedState(data = cachedData)
            }
        }
    }

    fun cacheData(savedArticleList : List<Article>) {
        cacheManager.putData("saved_data", savedArticleList)
    }

}