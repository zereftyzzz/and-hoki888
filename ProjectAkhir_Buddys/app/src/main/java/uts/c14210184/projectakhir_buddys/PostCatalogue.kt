package uts.c14210184.projectakhir_buddys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class PostCatalogue : AppCompatActivity() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_catalogue)
        val name = intent.getStringExtra("userName")
        val gambar = intent.getStringExtra("Gambar")


        val _etNameCat = findViewById<EditText>(R.id.etNameCat)
        val _etDescription = findViewById<EditText>(R.id.etDescription)
        val _etCategories = findViewById<EditText>(R.id.etCategories)
        val _etImage = findViewById<TextView>(R.id.etImage)
        val _ivBack = findViewById<ImageView>(R.id.ivBack)
        val _btSave = findViewById<Button>(R.id.btSave)

        val dataIntent = intent.getParcelableExtra<CatalogueData>("kirimData")

        _ivBack.setOnClickListener {
            val pass_name = name.toString()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userName", pass_name)
            intent.putExtra("Gambar", gambar)
            startActivity(intent)
        }

        _btSave.setOnClickListener {
            val pass_name = name.toString()
            val Title = _etNameCat.text.toString()
            val newCategories = _etCategories.text.toString()
            val newDescription = _etDescription.text.toString()
            val Image = _etImage.text.toString()
            val love: ArrayList<String> = ArrayList()
            TambahData(Title, newDescription, newCategories, Image,pass_name,love)
        }


//        val context = this
//        val imageRes = context.resources.getIdentifier(
//            dataIntent?.image,
//            "drawable",
//            context.packageName)
//        Picasso.get().load(imageRes).into(_ivDetCatalogue)
//        _tvTitles.setText(dataIntent!!.name)
//        _etCategories.setText(dataIntent!!.categories)
//        _etDescription.setText(dataIntent!!.desc)
    }
    fun TambahData(Name: String, Description: String, Categories: String, Image: String, username:String, Loved: ArrayList<String>?) {
        val dataBaru = CatalogueData(Image, Name, Categories, Description,Loved ?: ArrayList())

        db.collection("catalogue")
            .document(Name)
            .set(dataBaru)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Data Berhasil disimpan",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userName", username)
                intent.putExtra("kirimData", dataBaru)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Log.w(
                    "PROJ_DMFIREBASE",
                    "Error adding document",
                    e
                )
            }
    }


}