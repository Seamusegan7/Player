package org.wit.player.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PlayerModel(var id: Long = 0, //identifying a player with individual ids
                       var title: String = "",
                       var description: String = "",
                       var playerRating: Float = 1.0f,
                       var image: String = "",
                        var lat : Double = 0.0,
                        var lng: Double = 0.0,
                         var zoom: Float = 0f) : Parcelable




@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable