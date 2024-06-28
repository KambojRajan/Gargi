package com.darkrai.gargi.data.repositories

import android.util.Log
import com.darkrai.gargi.data.models.PlantDto
import com.darkrai.gargi.data.models.UserDto
import com.darkrai.gargi.data.models.toUser
import com.darkrai.gargi.domain.repositories.PlantRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.filter.TextSearchType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.concurrent.CancellationException
import javax.inject.Inject

class plantRepositoryImplement @Inject constructor(
    private val postgrest: Postgrest
):PlantRepository {
    companion object {
        private const val TAG = "PlantRepositoryImplement"
    }

    override suspend fun getSearchedPlants(query: String): List<PlantDto> {
        return try {
            withContext(Dispatchers.IO){
                postgrest.from("plants").select {
                    filter {
                        textSearch("name",query,TextSearchType.WEBSEARCH)
                    }
                }.decodeList<PlantDto>()
            }
        }catch (e:Exception){
            if(e is CancellationException) throw  e
            Log.e(TAG, e.toString())
            emptyList()
        }
    }

    override suspend fun getPlantsByCategory(cat: String): List<PlantDto> {
        return try {
            withContext(Dispatchers.IO){
                postgrest.from("plants").select {
                    filter {
                        eq("category",cat)
                    }
                }.decodeList<PlantDto>()
            }
        }catch (e:Exception){
            if(e is CancellationException) throw  e
            Log.e(TAG, e.toString())
            emptyList()
        }
    }

    override suspend fun getTopPlants(): List<PlantDto> {
        return try {
            withContext(Dispatchers.IO){
                postgrest.from("plants").select{
                    limit(count=15)
                }.decodeList<PlantDto>()
            }
        }catch (e:Exception){
            Log.e(TAG, e.toString())
            if(e is  CancellationException)throw e
            emptyList()
        }
    }

    override suspend fun getPlantById(id: Int): PlantDto? {
        try {
            val plant = postgrest.from("plants").select {
                filter {
                    eq("id",id)
                }
            }.decodeSingle<PlantDto>()
            if(plant.donatedBy != null)return plant
            return plant
        }catch (e:Exception){
            Log.e(TAG, e.toString())
            if(e is  CancellationException)throw e
            return null
        }
    }

    override suspend fun addDonation(plantId: Int, userId: String): Boolean {
        @Serializable
        data class Donation(
            @SerialName("plant_id")
            val plantId:Int,
            @SerialName("user_id")
            val userId:String
        )
       try {
           if(getPlantById(plantId) != null){
                postgrest.from("donations").insert(Donation(
                    plantId = plantId,
                    userId = userId
                ))
               val user = postgrest.from("users").select {
                   filter {
                       eq("user_id",userId)
                   }
               }.decodeSingleOrNull<UserDto>()?.toUser()
               if(user != null){
                   postgrest.from("users").update(
                       {
                           set("balance",user.balance + 10)
                       }
                   ){
                       filter {
                           eq("use_id",user.userId)
                       }
                   }
               }
               return true
           }
           return false
       }catch (e:Exception){
           Log.e(TAG, e.toString())
           if(e is  CancellationException)throw e
           return false
       }
    }
}