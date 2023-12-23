package uts.c14210184.projectakhir_buddys

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleData(
    var author: String,
    var description: String,
    var image: String,
    var title: String,
    var view: Int
) : Parcelable

