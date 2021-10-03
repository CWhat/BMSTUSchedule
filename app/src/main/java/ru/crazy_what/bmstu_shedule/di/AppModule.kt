package ru.crazy_what.bmstu_shedule.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.crazy_what.bmstu_shedule.data.remote.schedule.SchedulerService
import ru.crazy_what.bmstu_shedule.data.remote.schedule.SchedulerServiceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSchedulerService(): SchedulerService = SchedulerServiceImpl()
}