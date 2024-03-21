package com.example.articleapp.presentation.article_detail.components

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.articleapp.domain.model.Article

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticleDetailWebView(
    article : Article,
    webViewUpdated : Boolean,
    onWebViewUpdatedChange: (Boolean) -> Unit,
    innerPadding : PaddingValues
) {
    article.url?.let {url ->
        AndroidView(factory = {context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        }, update = {
            if (webViewUpdated) {
                it.loadUrl(url)
                onWebViewUpdatedChange(false)
            }
        }, modifier = Modifier.fillMaxSize().padding(innerPadding))
    }
}