package uts.c14210184.projectakhir_buddys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class DetArticle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_det_article)

        var _tvTitle: TextView = findViewById<TextView>(R.id.tvDetTitle)
        var _tvDesc: TextView = findViewById<TextView>(R.id.tvDetDesc)
        var _ivArticle: ImageView = findViewById<ImageView>(R.id.ivDetArt)

        val dataIntent = intent.getParcelableExtra<ArticleData>("kirimData")

        val context = this
        val imageRes = context.resources.getIdentifier(
            "det"+dataIntent?.image,
            "drawable",
            context.packageName)
        Picasso.get().load(imageRes).into(_ivArticle)
        _tvTitle.setText(dataIntent!!.title)
        _tvDesc.setText(dataIntent!!.description)
    }
}