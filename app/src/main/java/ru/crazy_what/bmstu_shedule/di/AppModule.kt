package ru.crazy_what.bmstu_shedule.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.crazy_what.bmstu_shedule.BuildConfig
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.data.GroupScheduleRepositoryImpl
import ru.crazy_what.bmstu_shedule.data.db.*
import ru.crazy_what.bmstu_shedule.data.remote.ScheduleService
import ru.crazy_what.bmstu_shedule.domain.repository.BookmarksRepository
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduleRepository
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(2, TimeUnit.SECONDS)
        .connectTimeout(2, TimeUnit.SECONDS)
        .readTimeout(2, TimeUnit.SECONDS)
        .writeTimeout(2, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideScheduleService(okHttpClient: OkHttpClient): ScheduleService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ScheduleService::class.java)

    @Provides
    @Singleton
    fun provideBookmarksDatabase(@ApplicationContext context: Context): BookmarksDatabase =
        Room.databaseBuilder(context, BookmarksDatabase::class.java, Constants.BOOKMARKS_DATABASE)
            .build()

    @Provides
    @Singleton
    fun provideLessonsDatabase(@ApplicationContext context: Context): LessonsDatabase =
        Room.databaseBuilder(context, LessonsDatabase::class.java, Constants.LESSONS_DATABASE)
            .build()

    @Provides
    @Singleton
    fun provideBookmarksDao(db: BookmarksDatabase) = db.bookmarksDao()

    @Provides
    @Singleton
    fun provideLessonsDao(db: LessonsDatabase) = db.lessonsDao()

    @Provides
    @Singleton
    fun provideBookmarksRepository(bookmarksDao: BookmarksDao): BookmarksRepository =
        BookmarksRepositoryImpl(bookmarksDao)

    @Provides
    @Singleton
    fun provideGroupScheduleRepository(scheduleService: ScheduleService): GroupScheduleRepository =
        GroupScheduleRepositoryImpl(scheduleService)

}