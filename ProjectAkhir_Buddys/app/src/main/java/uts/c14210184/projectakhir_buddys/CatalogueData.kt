package uts.c14210184.projectakhir_buddys

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatalogueData(
    var image: String,
    var name: String,
    var categories: String,
    var desc: String,
    var love: ArrayList<String>
) : Parcelable
