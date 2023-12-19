package uts.c14210184.projectakhir_buddys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

//tesssssss
class MainActivity : AppCompatActivity() {

    private lateinit var _rvPahlawan : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _rvPahlawan = findViewById(R.id.rvStore)

        val mFragmentmanager = supportFragmentManager
        val mCatalogue = Catalogue()
        mFragmentmanager.findFragmentByTag(mCatalogue::class.java.simpleName)
        mFragmentmanager
            .beginTransaction()
            .add(R.id.frameContainer, mCatalogue, Catalogue::class.java.simpleName)
            .commit()
    }
}