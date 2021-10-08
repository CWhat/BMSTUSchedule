package ru.crazy_what.bmstu_shedule.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.crazy_what.bmstu_shedule.common.Constants
import ru.crazy_what.bmstu_shedule.data.db.*
import ru.crazy_what.bmstu_shedule.data.remote.schedule.SchedulerService
import ru.crazy_what.bmstu_shedule.data.remote.schedule.SchedulerServiceImpl
import ru.crazy_what.bmstu_shedule.domain.repository.BookmarksRepository
import ru.crazy_what.bmstu_shedule.domain.repository.GroupScheduleRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSchedulerService(): SchedulerService = SchedulerServiceImpl()

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
    fun provideGroupScheduleRepository(lessonsDao: LessonsDao): GroupScheduleRepository =
        GroupScheduleRepositoryImpl(lessonsDao)

}