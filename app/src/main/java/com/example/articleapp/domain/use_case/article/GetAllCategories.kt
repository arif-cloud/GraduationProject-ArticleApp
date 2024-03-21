package com.example.articleapp.domain.use_case.article

import com.example.articleapp.domain.model.Category
import com.example.articleapp.domain.repository.ArticleRepository
import javax.inject.Inject

class GetAllCategories @Inject constructor(
    private val repository: ArticleRepository
) {
    suspend operator fun invoke() : List<Category> {
        return repository.getAllCategories()
    }
}