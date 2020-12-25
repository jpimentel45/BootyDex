package com.example.bootydex.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val streetAddress: String?,
//    val streetNumber: Int?,
    val apartmentNumber: String?
): Parcelable