package com.example.articleapp.presentation.article_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.articleapp.R
import com.example.articleapp.common.Graph
import com.example.articleapp.domain.model.Article
import com.example.articleapp.presentation.article_detail.components.ArticleDetailWebView
import com.example.articleapp.presentation.article_detail.components.ArticleSaveFabButton
import com.example.articleapp.presentation.article_detail.components.TopBar
import com.example.articleapp.presentation.theme.Typography

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticleDetailScreen(
    navController: NavController,
    articleData: Article?,
    viewModel: ArticleDetailViewModel = hiltViewModel()
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