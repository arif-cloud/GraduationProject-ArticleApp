package com.example.articleapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.articleapp.data.cache.CacheManager
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.model.Category
import com.example.articleapp.domain.use_case.article.GetAllCategories
import com.example.articleapp.domain.use_case.article.GetArticlesByCategory
import com.example.articleapp.domain.use_case.auth.GetUsername
import com.example.articleapp.domain.use_case.settings.GetSettingsState
import com.example.articleapp.domain.use_case.settings.UpdateNotificationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCategories: GetAllCategories,
    private val getArticlesByCategory : GetArticlesByCategory,
    private val updateNotificationState: UpdateNotificationState,
    private val getSettingsState: GetSettingsState,
    private val getUsername: GetUsername,
    private val cacheManager: CacheManager
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState : StateFlow<HomeState> get() = _homeState

    private val cachedData = cacheManager.getData<HomeData>("home_data")

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            if (cachedData == null) {
                try {
                    _homeState.value = HomeState(isLoading = true)
                    val categoryList = getAllCategories()
                    val articleData = getArticlesByCategory(categoryList[0].name).cachedIn(viewModelScope)
                    val username = getUsername()
                    _homeState.value = HomeState(data = HomeData(username, categoryList, articleData))
                } catch (e : HttpException) {
                    _homeState.value = HomeState(error = e.message())
                }
            } else {
                _homeState.value = HomeState(data = cachedData)
            }
        }
    }

    fun onPermissionResult(isGranted : Boolean) {
        updateNotificationState(isGranted)
    }

    fun getArticles(category : String) {
        viewModelScope.launch(Dispatchers.IO) {
            val articleList = getArticlesByCategory(category)
            val newHomeData = _homeState.value.data?.copy(articleData = articleList)
            _homeState.value = HomeState(data = newHomeData)
        }
    }

    fun cacheData(username : String, categoryList: List<Category>, articleData : Flow<PagingData<Article>>) {
        val homeData = HomeData(username, categoryList, articleData)
        cacheManager.putData("home_data", homeData)
    }

    fun updateCacheData(articleData : Flow<PagingData<Article>>) {
        cachedData?.let {homeData ->
            cacheManager.updateData("home_data", homeData.copy(articleData = articleData))
        }
    }

}