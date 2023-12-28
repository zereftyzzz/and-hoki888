package uts.c14210184.projectakhir_buddys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    public var userName: String? = null
    public var defaultImageUrl: String? = "https://static.vecteezy.com/system/resources/previews/002/275/847/original/male-avatar-profile-icon-of-smiling-caucasian-man-vector.jpg"
    public var admin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent.getStringExtra("userName").isNullOrEmpty()){
            userName = "Guest"
        }
        else {
            userName = intent.getStringExtra("userName")
        }

        if (userName.toString().toUpperCase() == "ADMIN"){
            admin = true
        }

        val fragmentManager = supportFragmentManager

        val catalogueFragment = Catalogue()
        val bundle2 = Bundle()
        bundle2.putBoolean("admin",admin)
        catalogueFragment.arguments = bundle2
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


        val profileFragment = Profile()
        val bundle = Bundle()
        profileFragment.arguments = bundle
        findViewById<ImageView>(R.id.Btn_Profile).setOnClickListener {
            fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, profileFragment, Profile::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }
    }
    fun setUserData(userName: String?, imageUrl: String?) {
        this.userName = userName
        this.defaultImageUrl = imageUrl
    }
}