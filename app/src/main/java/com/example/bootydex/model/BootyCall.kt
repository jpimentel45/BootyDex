package com.example.bootydex.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//make parcelable to extend BootyCall Class
//to pass BootyCall object
@Parcelize
@Entity(tableName = "booty_table")
data class BootyCall(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val phoneNumber: String,
    @Embedded
    val address: Address? = null,
    val image: String?

//var socialMediaHandels: List<String>?
) : Parcelable{
    //in class body so no need to declaring when creating booty object
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0
}