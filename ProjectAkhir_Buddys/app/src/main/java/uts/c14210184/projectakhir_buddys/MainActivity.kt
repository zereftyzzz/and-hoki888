package uts.c14210184.projectakhir_buddys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mFragmentmanager = supportFragmentManager
        val mCatalogue = Catalogue()
        mFragmentmanager.findFragmentByTag(mCatalogue::class.java.simpleName)
        mFragmentmanager
            .beginTransaction()
            .add(R.id.frameContainer, mCatalogue, Catalogue::class.java.simpleName)
            .commit()
    }
}