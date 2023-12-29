package uts.c14210184.projectakhir_buddys

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LovedData(
    var image: String,
    var name: String,
    var categories: String,
    var desc: String,
    var loved: String,
    var username: String
) : Parcelable
