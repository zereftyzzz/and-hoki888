package uts.c14210184.projectakhir_buddys

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleData(
    var author: String,
    var description: String,
    var image: String,
    var title: String,
    var view: Int,
    var love: ArrayList<String>
) : Parcelable {
    fun addLove(username: String?, name: String?) {
        username?.let {
            if (!love.contains(username)) {
                love.add(username)
                updateLoveInFirebase(name)
            }
        }
    }

    fun removeLove(username: String?, name: String?) {
        username?.let {
            love.remove(username)
            updateLoveInFirebase(name)
        }
    }

    private fun updateLoveInFirebase(name: String?) {
        val db = Firebase.firestore
        val documentReference = db.collection("article").document(name ?: "")
        documentReference.update("love", love)
    }
}

