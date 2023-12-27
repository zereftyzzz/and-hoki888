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
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.squareup.picasso.Picasso

class EditCatalogue : AppCompatActivity() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_catalogue)

        val _ivDetCatalogue = findViewById<ImageView>(R.id.ivDetCatalogue)
        val _etDescription = findViewById<EditText>(R.id.etDescription)
        val _etCategories = findViewById<EditText>(R.id.etCategories)
        val _tvTitles = findViewById<TextView>(R.id.tvTitles)
        val _ivBack = findViewById<ImageView>(R.id.ivBack)
        val _btSave = findViewById<Button>(R.id.btSave)

        val dataIntent = intent.getParcelableExtra<CatalogueData>("kirimData")

        _ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        _btSave.setOnClickListener {
            val Title = _tvTitles.text.toString()
            val newCategories = _etCategories.text.toString()
            val newDescription = _etDescription.text.toString()
            val Image = dataIntent?.image.toString()
            TambahData(Title, newDescription, newCategories, Image)
        }


        val context = this
        val imageRes = context.resources.getIdentifier(
            dataIntent?.image,
            "drawable",
            context.packageName)
        Picasso.get().load(imageRes).into(_ivDetCatalogue)
        _tvTitles.setText(dataIntent!!.name)
        _etCategories.setText(dataIntent!!.categories)
        _etDescription.setText(dataIntent!!.desc)
    }
    fun TambahData(Name: String, Description: String, Categories: String, Image: String) {
        val dataBaru = CatalogueData(Image, Name, Categories, Description)

        db.collection("catalogue")
            .document(Name)
            .set(dataBaru)
            .addOnSuccessListener {
                Toast.makeText(
                    this@EditCatalogue,
                    "Data Berhasil disimpan",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(this@EditCatalogue, DetCatalogue::class.java)
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
