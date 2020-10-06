package com.bonoj.flickrphoto.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Photo(
    val id: Long,
    val owner: String,
    val secret: String,
    val server: Long,
    val farm: Long,
    val title: String,
    val ispublic: Int,
    val isfriend: Int,
    val url_s: String,
    val height_s: Int,
    val width_s: Int
) : Parcelable
