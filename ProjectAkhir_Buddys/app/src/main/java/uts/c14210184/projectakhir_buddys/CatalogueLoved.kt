package uts.c14210184.projectakhir_buddys

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore

class CatalogueLoved : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private var dataCatalogue = ArrayList<CatalogueData>()
    private lateinit var _rvCatalogue: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue_loved)

        _rvCatalogue = findViewById(R.id.rvCatalogue)
        _rvCatalogue.layoutManager = LinearLayoutManager(this)

        val _ivBack = findViewById<ImageView>(R.id.ivBack)

        val name = intent.getStringExtra("userName")
        val gambar = intent.getStringExtra("Gambar")

//        button back ke Catalogue
        _ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val article = false
            intent.putExtra("article_back",article)
            intent.putExtra("userName", name)
            intent.putExtra("Gambar", gambar)
            startActivity(intent)
        }

        readData(name, gambar)
    }

    private fun readData(username: String?, gambar: String?) {
        db.collection("catalogue")
            .get()
            .addOnSuccessListener { result ->
                dataCatalogue.clear()
                for (document in result) {
//                    filter untuk menampilkan item yang di love saja
                    val loveList = document.get("love") as? ArrayList<String> ?: ArrayList()
                    if (username != null && loveList.contains(username)) {
                        val catalogueData = CatalogueData(
                            document.getString("image") ?: "",
                            document.getString("name") ?: "",
                            document.getString("categories") ?: "",
                            document.getString("desc") ?: "",
                            loveList
                        )
                        dataCatalogue.add(catalogueData)
                    }
                }

                Log.d("CatalogueFragment", "Data Retrieved: ${dataCatalogue.size}")

                val layoutManager = GridLayoutManager(this, 2)
                _rvCatalogue.layoutManager = layoutManager

                val adapter: AdapterCatalogue =
                    AdapterCatalogue(dataCatalogue) { data ->
                        val intent = Intent(this, DetCatalogue2::class.java)
                        intent.putExtra("userName", username)
                        intent.putExtra("kirimData", data)
                        intent.putExtra("Gambar", gambar)
                        startActivity(intent)
                    }

                adapter.username = username
                _rvCatalogue.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
    }

}
