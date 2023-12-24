package uts.c14210184.projectakhir_buddys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Pindah ke MainActivity saat tombol btnStart diklik
        val _etName = findViewById<EditText>(R.id.etName)
        val btnStart = findViewById<Button>(R.id.btnStart)
        btnStart.setOnClickListener {
            val name = _etName.text.toString()

            val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("userName", name)
            startActivity(intent)
            finish() // Optional: finish the Welcome activity to prevent going back to it on back press
        }
    }
}
