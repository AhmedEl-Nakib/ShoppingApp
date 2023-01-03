package com.example.shoppingapp.view.AddNewAddressFeature

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.entity.Address
import kotlinx.coroutines.launch

class AddNewAddressVM (application: Application) : AndroidViewModel (application){

    var repo = AddNewAddressRepo(application)

    fun insertAddress(address: Address){
        viewModelScope.launch {
            repo.addAddress(address)
        }
    }

}