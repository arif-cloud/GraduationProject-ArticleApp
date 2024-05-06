package com.example.articleapp.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.paging.cachedIn
import com.example.articleapp.domain.use_case.article.GetAllCategories
import com.example.articleapp.domain.use_case.article.GetArticlesByCategory
import com.example.articleapp.domain.use_case.article.GetDailyArticle
import com.example.articleapp.domain.use_case.auth.GetUsername
import com.example.articleapp.presentation.root.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCategories: GetAllCategories,
    private val getArticlesByCategory : GetArticlesByCategory,
    private val getUsername: GetUsername,
    private val getDailyArticle: GetDailyArticle
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState : StateFlow<HomeState> get() = _homeState

    private val _notificationDestinationIsVisited = mutableStateOf(false)

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                _homeState.value = HomeState(isLoading = true)
                val categoryList = getAllCategories()
                val articleData = getArticlesByCategory(categoryList[0].name).cachedIn(viewModelScope)
                val username = getUsername()
                _homeState.value = HomeState(data = HomeData(username, categoryList, articleData))
            } catch (e : HttpException) {
                _homeState.value = HomeState(error = e.message())
            }
        }
    }

    fun getArticles(category : String) {
        viewModelScope.launch {
            val articleList = getArticlesByCategory(category)
            val newHomeData = _homeState.value.data?.copy(articleData = articleList)
            _homeState.value = HomeState(data = newHomeData)
        }
    }

    fun getDailyArticleDetail(scope : CoroutineScope, navController : NavController) = viewModelScope.launch {
        try {
            if (!_notificationDestinationIsVisited.value) {
                val dailyArticle = getDailyArticle()
                scope.launch {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "article",
                        value = dailyArticle
                    )
                    navController.navigate(Screen.ArticleDetail.route)
                }
                _notificationDestinationIsVisited.value = true
            }
        } catch (e : Exception) {
            _homeState.value = HomeState(error = e.localizedMessage.orEmpty())
        }
    }

}