package uts.c14210184.projectakhir_buddys

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class DetMyPost : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var adapter: AdapterArticle // Define adapter at the class level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_det_my_post)

        var _tvTitle: TextView = findViewById<TextView>(R.id.tvDetTitle)
        var _tvDesc: TextView = findViewById<TextView>(R.id.tvDetDesc)
        var _ivArticle: ImageView = findViewById<ImageView>(R.id.ivDetArt)
        var _btnDel: Button = findViewById<Button>(R.id.btnDel)

        val dataIntent = intent.getParcelableExtra<ArticleData>("kirimData")

        val context = this

        Picasso.get().load(dataIntent?.image).into(_ivArticle)
        _tvTitle.setText(dataIntent?.title)
        _tvDesc.setText(dataIntent?.description)

        _btnDel.setOnClickListener {
            dataIntent?.title?.let { articleTitle ->
                deleteArticle(articleTitle)
//                refreshData() // Call refresh after deleting
            }
            finish()
        }
    }

    private fun deleteArticle(articleTitle: String) {
        db.collection("article")
            .whereEqualTo("title", articleTitle)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }

//    private fun refreshData() {
//        db.collection("article")
//            .get()
//            .addOnSuccessListener { result ->
//                val dataArticle = ArrayList<ArticleData>()
//                for (document in result) {
//                    val articleData = ArticleData(
//                        document.getString("author") ?: "",
//                        document.getString("description") ?: "",
//                        document.getString("image") ?: "",
//                        document.getString("title") ?: "",
//                        (document.getLong("view") ?: 0).toInt()
//                    )
//                    dataArticle.add(articleData)
//                }
//                // Refresh the adapter with the updated data
//                adapter = AdapterArticle(dataArticle) { data ->
//                    val intent = Intent(this@DetMyPost, DetArticle::class.java)
//                    intent.putExtra("kirimData", data)
//                    startActivity(intent)
//                }
//                // Update RecyclerView with the refreshed adapter
//                val _rvMyPost: RecyclerView = findViewById(R.id.rvMyPost)
//                _rvMyPost.adapter = adapter
//            }
//            .addOnFailureListener { exception ->
//                exception.printStackTrace()
//            }
//    }
}
