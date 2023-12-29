package uts.c14210184.projectakhir_buddys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class DetArticle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_det_article)
        val name = intent.getStringExtra("userName")
        val gambar = intent.getStringExtra("Gambar")


        var _tvTitle: TextView = findViewById<TextView>(R.id.tvDetTitle)
        var _tvDesc: TextView = findViewById<TextView>(R.id.tvDetDesc)
        var _ivArticle: ImageView = findViewById<ImageView>(R.id.ivDetArt)
        val _ivBack = findViewById<ImageView>(R.id.ivBackArt)


        val dataIntent = intent.getParcelableExtra<ArticleData>("kirimData")
        Picasso.get().load(dataIntent?.image).into(_ivArticle)
        _tvTitle.setText(dataIntent!!.title)
        _tvDesc.setText(dataIntent!!.description)

        _ivBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val article = true
            intent.putExtra("article_back",article)
            intent.putExtra("userName", name)
            intent.putExtra("Gambar", gambar)
            startActivity(intent)
        }
    }
}