package com.example.articleapp.domain.use_case.article

import android.content.Context
import android.content.Intent
import javax.inject.Inject

class ShareArticleLink @Inject constructor(
    private val context : Context
) {
    operator fun invoke(url : String) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(shareIntent)
    }
}