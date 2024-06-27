package com.darkrai.gargi.data.repositories

import android.util.Log
import com.darkrai.gargi.data.models.OrderDto
import com.darkrai.gargi.data.models.Plant
import com.darkrai.gargi.data.models.PlantDto
import com.darkrai.gargi.data.models.UserDto
import com.darkrai.gargi.domain.repositories.DashboardRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.filter.TextSearchType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException
import javax.inject.Inject

class DashboardRepositoryImplement @Inject constructor(
    private val postgrest: Postgrest
):DashboardRepository{
    companion object{
        private const val TAG = "Order Repository Implement"
    }

    override suspend fun getPendingOrders(): List<OrderDto> {
        return try {
            withContext(Dispatchers.IO){
                val ans = withContext(Dispatchers.IO) {
                    postgrest
                        .from("orders")
                        .select(Columns.raw("order_date,delivery_date,id, users(id, username, email, profile_img), plants(name, description, price, for_rescue, category, images)"))
                }
                Log.i(TAG, ans.data.toString())
                ans.decodeList<OrderDto>()
            }
        }catch (e:Exception){
            if(e is CancellationException) throw  e
            Log.e(TAG, e.toString())
            emptyList()
        }
    }

    override suspend fun addNewPlant(plant: Plant): Boolean {
        return try {
            val plant = PlantDto(
                name = plant.name,
                description = plant.description,
                price = plant.price,
                forRescue = plant.forRescue,
                donatedBy = plant.donatedBy,
                category = plant.category,
                images = plant.images,
                quantity = plant.quantity
            )
            Log.i(TAG, plant.toString())
            postgrest.from("plants").insert(plant)
            true
        }catch (e:Exception){
            if(e is CancellationException) throw  e
            Log.e(TAG, e.toString())
            false
        }
    }

    override suspend fun searchAPlant(table:String,column: String, query: String): List<PlantDto> {
        return try {
            postgrest.from(table).select {
                filter {
                    textSearch(column,query,TextSearchType.WEBSEARCH)
                }
            }.decodeList<PlantDto>()
        }catch (e:Exception){
            if(e is CancellationException) throw  e
            Log.e(TAG, e.toString())
            emptyList()
        }
    }

    override suspend fun searchAUser(table: String, column: String, query: String): List<UserDto> {
        return try {
            postgrest.from(table).select {
                filter {
                    textSearch(column,query,TextSearchType.WEBSEARCH)
                }
            }.decodeList<UserDto>()
        }catch (e:Exception){
            if(e is CancellationException) throw  e
            Log.e(TAG, e.toString())
            emptyList()
        }
    }
}
