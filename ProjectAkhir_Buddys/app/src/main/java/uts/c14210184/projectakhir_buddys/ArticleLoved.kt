package uts.c14210184.projectakhir_buddys

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ArticleLoved : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private var dataArticle = ArrayList<ArticleData>()
    private lateinit var _rvArticle: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_loved)

        _rvArticle = findViewById(R.id.rvArticle)
        _rvArticle.layoutManager = LinearLayoutManager(this)

        val name = intent.getStringExtra("userName")
        val gambar = intent.getStringExtra("Gambar")

        val _ivBack = findViewById<ImageView>(R.id.ivBack)

        _ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val article = true
            intent.putExtra("article_back",article)
            intent.putExtra("userName", name)
            intent.putExtra("Gambar", gambar)
            startActivity(intent)
        }

        readData(name, gambar)
    }

    private fun readData(username: String?, gambar: String?) {
        db.collection("article")
            .get()
            .addOnSuccessListener { result ->
                dataArticle.clear()
                for (document in result) {
                    val loveList = document.get("love") as? ArrayList<String> ?: ArrayList()
                    if (username != null && loveList.contains(username)) {
                        val articleData = ArticleData(
                            document.getString("author") ?: "",
                            document.getString("description") ?: "",
                            document.getString("image") ?: "",
                            document.getString("title") ?: "",
                            (document.getLong("view") ?: 0).toInt(),
                            document.get("love") as? ArrayList<String> ?: ArrayList()
                        )
                        dataArticle.add(articleData)
                    }
                }

                Log.d("Article Fragment", "Data Retrieved: ${dataArticle.size}")

                val layoutManager = LinearLayoutManager(this)
                _rvArticle.layoutManager = layoutManager

                val adapter = AdapterArticle(dataArticle) { data ->
                    val intent = Intent(this, DetArticle::class.java)
                    intent.putExtra("userName", username)
                    intent.putExtra("kirimData", data)
                    intent.putExtra("Gambar", gambar)
                    startActivity(intent)
                }

                _rvArticle.adapter = adapter
                adapter.username = username
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
    }
}
