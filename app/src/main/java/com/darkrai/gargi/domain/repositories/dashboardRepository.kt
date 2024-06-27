package com.darkrai.gargi.domain.repositories

import com.darkrai.gargi.data.models.OrderDto
import com.darkrai.gargi.data.models.Plant
import com.darkrai.gargi.data.models.PlantDto
import com.darkrai.gargi.data.models.UserDto


interface DashboardRepository {
    suspend fun getPendingOrders():List<OrderDto>
    suspend fun addNewPlant(plant:Plant):Boolean
    suspend fun searchAPlant(table:String,column:String,query:String):List<PlantDto>
    suspend fun searchAUser(table:String,column:String,query:String):List<UserDto>
}
