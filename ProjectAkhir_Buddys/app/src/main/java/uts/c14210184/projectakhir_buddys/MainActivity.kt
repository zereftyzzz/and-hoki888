package uts.c14210184.projectakhir_buddys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager

        val catalogueFragment = Catalogue()
        fragmentManager.beginTransaction()
            .replace(R.id.frameContainer, catalogueFragment, Catalogue::class.java.simpleName)
            .commit()

        findViewById<ImageView>(R.id.Btn_Home).setOnClickListener {
            fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, catalogueFragment, Catalogue::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }

        val articleFragment = Article()
        findViewById<ImageView>(R.id.Btn_Article).setOnClickListener {
            fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, articleFragment, Article::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }

        val postFragment = PostArticle()
        findViewById<ImageView>(R.id.Btn_Plus).setOnClickListener {
            fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, postFragment, PostArticle::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }


        val userName = intent.getStringExtra("userName")
        val profileFragment = Profile()
        val bundle = Bundle()
        bundle.putString("userName", userName)
        profileFragment.arguments = bundle
        findViewById<ImageView>(R.id.Btn_Profile).setOnClickListener {
            fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, profileFragment, Profile::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }
    }
}
