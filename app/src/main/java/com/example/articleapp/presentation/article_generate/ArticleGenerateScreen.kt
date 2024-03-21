package com.example.articleapp.presentation.article_generate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.articleapp.R
import com.example.articleapp.presentation.article_generate.components.GenerateButton
import com.example.articleapp.presentation.article_generate.components.PromptTextField
import com.example.articleapp.presentation.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleGenerateScreen(
    viewModel: ArticleGenerateViewModel = hiltViewModel()
) {
    val state = viewModel.articleGenerateState.value
    var prompt by remember { mutableStateOf("Could you please write an article on ") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Article Generator", style = Typography.titleMedium) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(titleContentColor = MaterialTheme.colorScheme.onSurface),
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.clip(CircleShape),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.primary),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.gemini),
                                contentDescription = "gemini_icon"
                            )
                        }
                    )
                }
            )
        },
        content = {paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    PromptTextField(
                        prompt = prompt,
                        onPromptChange = { prompt = it }
                    )
                    GenerateButton(
                        articleGenerateState = viewModel.articleGenerateState.value,
                        onClickGenerateButton = {
                            viewModel.generateArticle(prompt)
                        }
                    )
                    state.data?.let {
                        Text(text = it, style = Typography.bodyMedium, modifier = Modifier.verticalScroll(rememberScrollState()))
                    }
                }
                if (state.error.isNotEmpty()) {
                    Text(text = state.error, modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleGenerateScreenContent() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Article Generator", style = Typography.titleMedium) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(titleContentColor = MaterialTheme.colorScheme.onSurface),
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.clip(CircleShape),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.primary),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.gemini),
                                contentDescription = "gemini_icon"
                            )
                        }
                    )
                }
            )
        },
        content = {paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    PromptTextField(
                        prompt = "",
                        onPromptChange = {  }
                    )
                    GenerateButton(
                        articleGenerateState = ArticleGenerateState(),
                        onClickGenerateButton = {}
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun Prev() {
    ArticleGenerateScreenContent()
}