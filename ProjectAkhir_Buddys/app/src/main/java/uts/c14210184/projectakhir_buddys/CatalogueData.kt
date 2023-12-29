package uts.c14210184.projectakhir_buddys

import android.os.Parcelable
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatalogueData(
    var image: String,
    var name: String,
    var categories: String,
    var desc: String,
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
        val documentReference = db.collection("catalogue").document(name ?: "")
        documentReference.update("love", love)
    }

}