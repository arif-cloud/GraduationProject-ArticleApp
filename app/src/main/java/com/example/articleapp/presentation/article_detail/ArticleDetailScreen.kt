package com.example.articleapp.presentation.article_detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.articleapp.domain.model.Article
import com.example.articleapp.presentation.article_detail.components.ArticleDetailWebView
import com.example.articleapp.presentation.article_detail.components.ArticleSaveFabButton
import com.example.articleapp.presentation.article_detail.components.TopBar

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticleDetailScreen(
    navController: NavController,
    articleData: Article?,
    viewModel: ArticleDetailViewModel
) {
    val articleState by viewModel.articleState.collectAsState()
    val context = LocalContext.current
    var menuExpanded by remember { mutableStateOf(false) }
    var webViewUpdated by remember { mutableStateOf(false) }
    articleData?.let {article ->
        LaunchedEffect(key1 = Unit, block = { viewModel.fetchArticleState(article.id) })
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                menuExpanded = menuExpanded,
                onMenuExpandedChange = { menuExpanded = it },
                onRefreshClick = { webViewUpdated = true },
                onShareClick = { viewModel.shareLink(articleData?.url) },
                onClickBackButton = { navController.popBackStack() }
            )
        }, floatingActionButton = {
        articleData?.let {article ->
            ArticleSaveFabButton(
                articleState = articleState,
                onClickSaveButton = { if (articleState.saved) viewModel.removeArticleData(article) else viewModel.saveArticleData(article) }
            )
        }
    },  floatingActionButtonPosition = FabPosition.End,
        content = {innerPadding ->
        articleData?.let {article ->
            ArticleDetailWebView(
                article = article,
                webViewUpdated = webViewUpdated,
                onWebViewUpdatedChange = { webViewUpdated = it },
                innerPadding = innerPadding
            )
        }
    })
    articleState.error?.let {
        LaunchedEffect(key1 = articleState.error, block = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() })
    }
}