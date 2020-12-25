package com.example.bootydex.repository

import androidx.lifecycle.LiveData
import com.example.bootydex.data.BootyDao
import com.example.bootydex.model.BootyCall

class BootyRepository(private val bootyDao: BootyDao) {
    val readAllData : LiveData<List<BootyCall>> = bootyDao.readAllData()

    val isNotEmpty : Boolean = readAllData.value?.size != 0

    suspend fun addBootyCall(bootyCall: BootyCall){
        bootyDao.addBootyCall(bootyCall)
    }

    suspend fun updateBootyCall(bootyCall: BootyCall){
        bootyDao.updateBootyCall(bootyCall)
    }
    suspend fun deleteBootyCall(bootyCall: BootyCall){
        bootyDao.deleteBootyCall(bootyCall)
    }
    suspend fun deleteAllBootyCalls(){
        bootyDao.deleteAllBootyCalls()
    }


}