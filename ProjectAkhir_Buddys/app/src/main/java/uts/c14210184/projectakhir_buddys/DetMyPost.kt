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
        val name = intent.getStringExtra("userName")

        var _tvTitle= findViewById<TextView>(R.id.tvDetTitle)
        var _tvDesc= findViewById<TextView>(R.id.tvDetDesc)
        var _ivArticle= findViewById<ImageView>(R.id.ivDetArt)
        var _btnDel = findViewById<Button>(R.id.btnDel)
        var _ivBackDetPost: ImageView = findViewById(R.id.ivBackDetPost)

        val dataIntent = intent.getParcelableExtra<ArticleData>("kirimData")

        Picasso.get().load(dataIntent?.image).into(_ivArticle)
        _tvTitle.setText(dataIntent?.title)
        _tvDesc.setText(dataIntent?.description)

        _btnDel.setOnClickListener {
            dataIntent?.title?.let { articleTitle ->
                deleteArticle(articleTitle)
            }
            finish()
        }
        _ivBackDetPost.setOnClickListener {
            onBackPressed()
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

}
