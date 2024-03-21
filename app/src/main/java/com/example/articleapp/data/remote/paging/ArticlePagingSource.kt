package com.example.articleapp.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.articleapp.domain.model.Article
import com.example.articleapp.domain.repository.ArticleRepository

class ArticlePagingSource(
    private val repository: ArticleRepository,
    private val category : String
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val currentPage = params.key ?: 0
            val response = repository.getArticlesByCategory(category, currentPage)
            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 0) null else currentPage.minus(1),
                nextKey = if (response.isNotEmpty()) currentPage.plus(1) else null
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }

}