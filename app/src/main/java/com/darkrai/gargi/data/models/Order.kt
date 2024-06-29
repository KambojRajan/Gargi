package com.darkrai.gargi.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class OrderDto(
    @SerialName("order_date") val orderDate: String,
    @SerialName("delivery_date") val deliveryDate: String,
    @SerialName("id") val id: Int,
    @SerialName("users") val user: UUser,
    @SerialName("plants") val plant: PPlant,
    @SerialName("order_pending") val pending:Boolean
)

@Serializable
data class UUser(
    @SerialName("id") val id: Int,
    @SerialName("username") val username: String,
    @SerialName("email") val email: String,
    @SerialName("profile_img") val profileImage: String
)

@Serializable
data class PPlant(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("price") val price: Int,
    @SerialName("for_rescue") val forRescue: Boolean,
    @SerialName("category") val category: String,
    @SerialName("images") val images: List<String>,
)