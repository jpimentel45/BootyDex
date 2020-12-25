package com.example.bootydex.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bootydex.model.BootyCall

@Dao
interface BootyDao {

    //If Exactly the same user then ignore
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBootyCall(bootyCall: BootyCall)

    @Update
    suspend fun updateBootyCall(bootyCall: BootyCall)
    @Delete
    suspend fun deleteBootyCall(bootyCall: BootyCall)

    @Query("DELETE FROM booty_table")
    suspend fun deleteAllBootyCalls()

    @Query("SELECT * FROM booty_table ORDER BY id ASC")
    fun readAllData() : LiveData<List<BootyCall>>


}