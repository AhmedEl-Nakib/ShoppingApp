package com.example.shoppingapp.FirestoreModel

import com.example.shoppingapp.entity.Cart

class FireStoreOrders(
    var totalCount: Int=0,
    var totalAmount: Int=0,
    //var cart: List<FireStoreCart>,
    var userID: String,
    var insertedDate: Long,
    var orderID: Long,
    var status: String = "Processing"
) {
}