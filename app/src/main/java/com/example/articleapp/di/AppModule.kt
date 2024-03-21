package com.example.articleapp.di

import com.example.articleapp.BuildConfig
import android.content.Context
import android.content.SharedPreferences
import com.example.articleapp.common.Constants
import com.example.articleapp.data.firebase.implementation.FirebaseRepositoryImpl
import com.example.articleapp.data.remote.api.ArticleApi
import com.example.articleapp.data.remote.implementation.ArticleRepositoryImpl
import com.example.articleapp.domain.repository.ArticleRepository
import com.example.articleapp.domain.repository.FirebaseRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideArticleApi() : ArticleApi {
        return Retrofit.Builder().
        baseUrl(Constants.BASE_URL).
        client(OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build()).
        addConverterFactory(GsonConverterFactory.create()).
        build().
        create(ArticleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(api: ArticleApi) : ArticleRepository {
        return ArticleRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseRepository(firebaseAuth: FirebaseAuth, firebaseFirestore : FirebaseFirestore) : FirebaseRepository {
        return FirebaseRepositoryImpl(firebaseAuth, firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideGenerativeModel() = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context) : SharedPreferences {
        return context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
    }

}