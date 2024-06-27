package com.darkrai.gargi.data.repositories

import android.util.Log
import com.darkrai.gargi.data.models.PlantDto
import com.darkrai.gargi.domain.repositories.PlantRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.filter.TextSearchType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
}