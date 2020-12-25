package com.example.bootydex.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bootydex.model.BootyCall

@Database(entities = [BootyCall::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BootyDatabase : RoomDatabase(){

    abstract fun bootyDao() : BootyDao
//visible to other classes
    companion object{
    //Making BootyDatabase a singleton
    //Volatile: Write to fields visible to other threads
        @Volatile
        private var INSTANCE : BootyDatabase? = null

        fun getDatabase(context: Context) : BootyDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }


    private fun buildDatabase(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            BootyDatabase::class.java, "booty_database"
        ).build()

    }
}