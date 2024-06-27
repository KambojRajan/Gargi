package com.darkrai.gargi.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlantDto(
    @SerialName("id")
    val id:Int = 0,

    @SerialName("name")
    val name:String = "",

    @SerialName("description")
    val description:String = "",

    @SerialName("price")
    val price:Float = 0f,

    @SerialName("for_rescue")
    val forRescue:Boolean = false,

    @SerialName("donated_by")
    val donatedBy:String ?= "",

    @SerialName("category")
    val category: String,

    @SerialName("images")
    val images:  List<String> =  emptyList(),

    @SerialName("quantity")
    val quantity:Int = 0
)

fun PlantDto.toPlant():Plant{
    return Plant(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        forRescue = this.forRescue,
        donatedBy = this.donatedBy,
        category = this.category,
        images = this.images,
        quantity = this.quantity
    )
}

data class Plant(
    val id:Int = 0,
    val name:String = "",
    val description:String = "",
    val price:Float = 0f,
    val forRescue:Boolean = false,
    val donatedBy:String ?= "",
    val category: String,
    val images: List<String> =  emptyList(),
    val quantity: Int
)