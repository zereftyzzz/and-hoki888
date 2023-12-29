package uts.c14210184.projectakhir_buddys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso

class DetCatalogue2 : AppCompatActivity() {
    private val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_det_catalogue2)
        val name = intent.getStringExtra("userName")
        val gambar = intent.getStringExtra("Gambar")



        val _ivDetCatalogue = findViewById<ImageView>(R.id.ivDetCatalogue)
        val _tvDescription = findViewById<TextView>(R.id.etDescription)
        val _tvCategories = findViewById<TextView>(R.id.etCategories)
        val _tvTitles = findViewById<TextView>(R.id.tvTitles)
        val _ivBack = findViewById<ImageView>(R.id.ivBack)


        val dataIntent = intent.getParcelableExtra<CatalogueData>("kirimData")

        _ivBack.setOnClickListener {
            val pass_name = name.toString()
            val intent = Intent(this, MainActivity::class.java)
            val article = false
            intent.putExtra("article_back",article)
            intent.putExtra("userName", pass_name)
            intent.putExtra("Gambar", gambar)
            startActivity(intent)
        }

        val documentId = dataIntent?.name ?: ""
        db.collection("catalogue")
            .document(documentId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val catalogueData = CatalogueData(
                        documentSnapshot.getString("image") ?: "",
                        documentSnapshot.getString("name") ?: "",
                        documentSnapshot.getString("categories") ?: "",
                        documentSnapshot.getString("desc") ?: ""
                    )

                    // Populate views with retrieved data
                    val context = this
                    val imageRes = context.resources.getIdentifier(
                        catalogueData.image,
                        "drawable",
                        context.packageName
                    )
                    Picasso.get().load(imageRes).into(_ivDetCatalogue)
                    _tvTitles.text = catalogueData.name
                    _tvCategories.text = catalogueData.categories
                    _tvDescription.text = catalogueData.desc
                }
            }
            .addOnFailureListener { exception ->
                Log.w("DetCatalogue", "Error getting document", exception)
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }

    }
}