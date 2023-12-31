package uts.c14210184.projectakhir_buddys

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class DetMyPost : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_det_my_post)

        var _tvTitle= findViewById<TextView>(R.id.tvDetTitle)
        var _tvDesc= findViewById<TextView>(R.id.tvDetDesc)
        var _ivArticle= findViewById<ImageView>(R.id.ivDetArt)
        var _btnDel = findViewById<Button>(R.id.btnDel)
        var _ivBackDetPost: ImageView = findViewById(R.id.ivBackDetPost)

        val dataIntent = intent.getParcelableExtra<ArticleData>("kirimData")

        Picasso.get().load(dataIntent?.image).into(_ivArticle)
        _tvTitle.text = dataIntent?.title
        _tvDesc.text = dataIntent?.description

        _btnDel.setOnClickListener {
            dataIntent?.title?.let { articleTitle ->
                deleteArticle(articleTitle)
            }
        }
        _ivBackDetPost.setOnClickListener {
            onBackPressed()
        }
    }

//    delete artikel
    private fun deleteArticle(articleTitle: String) {
        db.collection("article")
            .whereEqualTo("title", articleTitle) // mencari yang titlenya sama
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete() //delete artikel tsb
                }
                Toast.makeText(this, "Deletion successful", Toast.LENGTH_SHORT).show()
                val resultIntent = Intent().apply {
                    putExtra("refreshData", true) // refresh setelah delete
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                Toast.makeText(this, "Deletion failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
