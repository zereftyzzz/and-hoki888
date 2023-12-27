package uts.c14210184.projectakhir_buddys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class DetCatalogue : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_det_catalogue)

        val _ivDetCatalogue = findViewById<ImageView>(R.id.ivDetCatalogue)
        val _tvDescription = findViewById<TextView>(R.id.etDescription)
        val _tvCategories = findViewById<TextView>(R.id.etCategories)
        val _tvTitles = findViewById<TextView>(R.id.tvTitles)
        val _ivBack = findViewById<ImageView>(R.id.ivBack)
        val _ivEdit = findViewById<ImageView>(R.id.ivEdit)


        val dataIntent = intent.getParcelableExtra<CatalogueData>("kirimData")

        _ivBack.setOnClickListener {
            this.onBackPressed()
        }

        _ivEdit.setOnClickListener {
            val intent = Intent(this@DetCatalogue, EditCatalogue::class.java)
            intent.putExtra("kirimData", dataIntent)
            startActivity(intent)
        }

//        val resourceId = resources.getIdentifier(
//            dataIntent?.gambar, "drawable", packageName
//        )
//        _ivDetCatalogue.setImageResource(resourceId)
        val context = this
        val imageRes = context.resources.getIdentifier(
            dataIntent?.gambar,
            "drawable",
            context.packageName)
        Picasso.get().load(imageRes).into(_ivDetCatalogue)
        _tvTitles.setText(dataIntent!!.item)
        _tvCategories.setText(dataIntent!!.categories)
        _tvDescription.setText(dataIntent!!.desc)
    }
}