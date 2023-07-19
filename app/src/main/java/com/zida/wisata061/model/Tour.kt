package com.zida.wisata061.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.net.Inet4Address

@Parcelize
@Entity(tableName = "Tour_table")
data class Tour (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address: String,
    val street: String,
    val latitude: Double?,
    val longitude: Double?
) : Parcelable
