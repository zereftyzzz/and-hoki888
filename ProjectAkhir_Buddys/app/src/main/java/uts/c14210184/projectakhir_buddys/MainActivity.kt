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

//        Fragment Catalogue sebagai tampilan awal
        val mFragmentmanager = supportFragmentManager
        val mCatalogue = Catalogue()
        mFragmentmanager.findFragmentByTag(mCatalogue::class.java.simpleName)
        mFragmentmanager
            .beginTransaction()
            .add(R.id.frameContainer, mCatalogue, Catalogue::class.java.simpleName)
            .commit()

        //        Fragment Catalogue
        var _btnCatalogue = findViewById<ImageView>(R.id.Btn_Home)
        _btnCatalogue.setOnClickListener {
            mFragmentmanager.findFragmentByTag(mCatalogue::class.java.simpleName)
            mFragmentmanager
                .beginTransaction()
                .replace(R.id.frameContainer, mCatalogue, Catalogue::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }

        //        Fragment Article
        val mArticle = Article()
        var _btnArticle = findViewById<ImageView>(R.id.Btn_Article)
        _btnArticle.setOnClickListener {
            mFragmentmanager.findFragmentByTag(mArticle::class.java.simpleName)
            mFragmentmanager
                .beginTransaction()
                .replace(R.id.frameContainer, mArticle, Article::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }

        //        Fragment Post
        val mPost = PostArticle()
        var _btnPost = findViewById<ImageView>(R.id.Btn_Plus)
        _btnPost.setOnClickListener {
            mFragmentmanager.findFragmentByTag(mPost::class.java.simpleName)
            mFragmentmanager
                .beginTransaction()
                .replace(R.id.frameContainer, mPost, PostArticle::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }

        //        Fragment Profile
        val mProfile = Profile()
        var _btnProfile = findViewById<ImageView>(R.id.Btn_Profile)
        _btnProfile.setOnClickListener {
            mFragmentmanager.findFragmentByTag(mProfile::class.java.simpleName)
            mFragmentmanager
                .beginTransaction()
                .replace(R.id.frameContainer, mProfile, Profile::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }
    }


}