package com.example.articleapp.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articleapp.domain.use_case.article.GetSavedArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getSavedArticle: GetSavedArticle
) : ViewModel() {

    private val _savedState = MutableStateFlow(SavedState())
    val savedState : StateFlow<SavedState> get() = _savedState

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                _savedState.value = SavedState(isLoading = true)
                val savedArticleList = getSavedArticle()
                _savedState.value = SavedState(data = savedArticleList)
            } catch (e : Exception) {
                _savedState.value = SavedState(error = e.localizedMessage)
            }
        }
    }

}