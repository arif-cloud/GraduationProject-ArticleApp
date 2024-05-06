package com.example.articleapp.domain.use_case.article

import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.repository.ArticleRepository
import java.util.Calendar
import javax.inject.Inject

class GetDailyArticle @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke() : Article {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        return repository.getDailyArticle(day)
    }
}