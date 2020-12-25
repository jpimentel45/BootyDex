package com.example.bootydex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.bootydex.data.BootyDatabase
import com.example.bootydex.repository.BootyRepository
import com.example.bootydex.model.BootyCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Contains application reference
class BootyCallViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData : LiveData<List<BootyCall>>
    private val repository: BootyRepository
//    var isNotEmpty : Boolean = false
    init {
        val bootyDao = BootyDatabase.getDatabase(
            application
        ).bootyDao()
        repository = BootyRepository(bootyDao)
        readAllData = repository.readAllData
//        isNotEmpty = repository.isNotEmpty
    }

    fun isNotEmpty() : Boolean{
           return repository.isNotEmpty
    }
    fun addBootyCall(bootyCall: BootyCall){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBootyCall(bootyCall)
        }
    }
    fun updateBootyCall(bootyCall: BootyCall){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBootyCall(bootyCall)
        }
    }
    fun deleteBootyCall(bootyCall: BootyCall){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteBootyCall(bootyCall)
        }
    }
    fun deleteAllBootyCalls(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllBootyCalls()
        }
    }
}