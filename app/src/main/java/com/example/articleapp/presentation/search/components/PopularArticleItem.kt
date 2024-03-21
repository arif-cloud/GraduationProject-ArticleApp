package com.example.articleapp.presentation.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.articleapp.R
import com.example.articleapp.domain.model.PopularArticle
import com.example.articleapp.presentation.home.components.TagList
import com.example.articleapp.presentation.theme.LightC
import com.example.articleapp.presentation.theme.Typography

@Composable
fun PopularArticleItem(
    popularArticle : PopularArticle,
    onItemClick : (PopularArticle) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().clickable { onItemClick(popularArticle) }, shape = RoundedCornerShape(size = 20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
        AsyncImage(model = popularArticle.imageUrl, contentDescription = null, modifier = Modifier
            .fillMaxWidth()
            .height(200.dp), contentScale = ContentScale.FillBounds)
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(text = popularArticle.title ?: "", style = Typography.bodyMedium, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = (popularArticle.description + "..."), style = Typography.bodyMedium, fontSize = 14.sp, maxLines = 5, overflow = TextOverflow.Ellipsis)
            TagList(popularArticle.tags)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Icon(painter = painterResource(id = R.drawable.favorite), contentDescription = null, tint = Color.Red)
                    Text(text = popularArticle.reactions.toString(), style = Typography.bodyMedium)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Icon(painter = painterResource(id = R.drawable.calendar), contentDescription = null, tint = Color.DarkGray)
                    Text(text = popularArticle.publishDate ?: "", style = Typography.bodyMedium)
                }
            }
        }
    }
}