package com.example.shoppingapp.FirestoreModel

class FireStoreAddress(
                       val fullName : String,
                       val country: String,
                       val city: String,
                       val area: Int,
                       val streetNumber: Int,
                       val house: Int,
                       val postalCode: Int,
                       val phoneNumber: Int,
                       val homeDeliveryAddress: Boolean
) {
}