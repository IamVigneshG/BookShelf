package com.gv.bookshelf.di

import android.content.Context
import androidx.room.Room
import com.gv.bookshelf.api.BookApi
import com.gv.bookshelf.db.BookDao
import com.gv.bookshelf.db.BookDatabase
import com.gv.bookshelf.main.DefaultMainRepository
import com.gv.bookshelf.main.MainRepository
import com.gv.bookshelf.utils.Constants.Companion.BASE_URL
import com.gv.bookshelf.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBookApi() : BookApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BookApi::class.java)



    @Singleton
    @Provides
    fun providesDatabase(db: BookDatabase) : BookDao {
        return db.getBookDao()
    }

    @Singleton
    @Provides
    fun provideMainRepository(api : BookApi , db : BookDatabase) : MainRepository = DefaultMainRepository(api,db)



    @Singleton
    @Provides
    fun provideDispatchers() : DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }

    @Provides
    @Singleton
    fun provideBookDao(appDatabase: BookDatabase): BookDao {
        return appDatabase.getBookDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): BookDatabase {
        return Room.databaseBuilder(
            appContext,
            BookDatabase::class.java,
            "books.db"
        ).build()
    }

}