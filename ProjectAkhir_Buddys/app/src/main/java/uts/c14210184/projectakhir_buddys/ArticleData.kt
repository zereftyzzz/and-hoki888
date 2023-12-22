package uts.c14210184.projectakhir_buddys

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleData(
    var author: String,
    var description: String,
    var image: String,
    var title: String,
    var view: Int
) : Parcelable
