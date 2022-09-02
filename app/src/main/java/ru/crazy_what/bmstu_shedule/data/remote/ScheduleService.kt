package ru.crazy_what.bmstu_shedule.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.crazy_what.bmstu_shedule.data.remote.model.Group
import ru.crazy_what.bmstu_shedule.data.remote.model.GroupSchedule

interface ScheduleService {

    @GET("/list/group")
    suspend fun getGroups(): Response<List<Group>>

    @GET("/schedule/group/{id}")
    suspend fun getGroupSchedule(@Path("id") id: String): Response<GroupSchedule>

    // TODO сделать то же самое для преподавателей

}