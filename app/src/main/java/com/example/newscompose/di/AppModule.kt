package com.example.newscompose.di

import android.app.Application
import androidx.room.Room
import com.example.newscompose.data.local.NewsDao
import com.example.newscompose.data.local.NewsDatabase
import com.example.newscompose.data.local.NewsTypeConvertor
import com.example.newscompose.data.manager.LocalUserManagerImpl
import com.example.newscompose.data.remote.NewsApi
import com.example.newscompose.data.repository.NewsRepositoryImpl
import com.example.newscompose.domain.manager.LocalUserManager
import com.example.newscompose.domain.repository.NewsRepository
import com.example.newscompose.domain.usecase.app_entry.AppEntryUseCases
import com.example.newscompose.domain.usecase.app_entry.ReadAppEntry
import com.example.newscompose.domain.usecase.app_entry.SaveAppEntry
import com.example.newscompose.domain.usecase.news.DeleteArticle
import com.example.newscompose.domain.usecase.news.GetArticle
import com.example.newscompose.domain.usecase.news.GetArticles
import com.example.newscompose.domain.usecase.news.GetNews
import com.example.newscompose.domain.usecase.news.NewsUseCases
import com.example.newscompose.domain.usecase.news.SearchNews
import com.example.newscompose.domain.usecase.news.UpsertArticle
import com.example.newscompose.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager,
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager),
    )

    @Provides
    @Singleton
    fun provideApiInstance(): NewsApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("User-Agent", "Mozilla/5.0")
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository {
        return NewsRepositoryImpl(newsApi, newsDao)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            getArticles = GetArticles(newsRepository),
            getArticle = GetArticle(newsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao
}
