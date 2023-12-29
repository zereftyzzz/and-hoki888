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
import com.google.firebase.firestore.FirebaseFirestore

class CatalogueLoved : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var dataCatalogue = ArrayList<CatalogueData>()
    private lateinit var _rvCatalogue: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue_loved)

        val _ivBack = findViewById<ImageView>(R.id.ivBack)
        _rvCatalogue = findViewById(R.id.rvCatalogue)
        _rvCatalogue.layoutManager = LinearLayoutManager(this)

        val _ivTambah = findViewById<ImageView>(R.id.ivTambah)

        val mainActivity = this as? MainActivity
        val admin = mainActivity?.admin
        val name = intent.getStringExtra("userName")
        val gambar = intent.getStringExtra("Gambar")


        if (admin == false) {
            _ivTambah.visibility = View.INVISIBLE
        }

        _ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val article = false
            intent.putExtra("article_back",article)
            intent.putExtra("userName", name)
            intent.putExtra("Gambar", gambar)
            startActivity(intent)
        }

        _ivTambah.setOnClickListener {
            val intent = Intent(this, PostCatalogue::class.java)
            val article = false
            intent.putExtra("article_back", article)
            intent.putExtra("userName", name)
            intent.putExtra("Gambar", gambar)
            startActivity(intent)
        }

        readData(admin, name, gambar)
    }

    private fun readData(admin: Boolean?, username: String?, gambar: String?) {
        db.collection("catalogue")
            .get()
            .addOnSuccessListener { result ->
                dataCatalogue.clear()
                for (document in result) {
                    val catalogueData = CatalogueData(
                        document.getString("image") ?: "",
                        document.getString("name") ?: "",
                        document.getString("categories") ?: "",
                        document.getString("desc") ?: "",
                        document.getBoolean("love") ?: false
                    )
                    dataCatalogue.add(catalogueData)
                }

                Log.d("CatalogueFragment", "Data Retrieved: ${dataCatalogue.size}")

                val layoutManager = GridLayoutManager(this, 2)
                _rvCatalogue.layoutManager = layoutManager

                val adapter: AdapterCatalogue = if (admin == true) {
                    AdapterCatalogue(dataCatalogue) { data ->
                        val intent = Intent(this, DetCatalogue::class.java)
                        intent.putExtra("userName", username)
                        intent.putExtra("kirimData", data)
                        intent.putExtra("Gambar", gambar)
                        startActivity(intent)
                    }
                } else {
                    AdapterCatalogue(dataCatalogue) { data ->
                        val intent = Intent(this, DetCatalogue2::class.java)
                        intent.putExtra("userName", username)
                        intent.putExtra("kirimData", data)
                        intent.putExtra("Gambar", gambar)
                        startActivity(intent)
                    }
                }

                _rvCatalogue.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
    }
}
