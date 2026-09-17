package com.example.pertemuan_3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvNama = findViewById<TextView>(R.id.tvNama)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvPhone = findViewById<TextView>(R.id.tvPhone)
        val tvPortfolio = findViewById<TextView>(R.id.tvPortfolio)

        val btnWebsite = findViewById<Button>(R.id.btnKunjungiWebsite)
        val btnHubungi = findViewById<Button>(R.id.btnHubungiSaya)
        val btnBagikan = findViewById<Button>(R.id.btnBagikanKartu)

        // Ambil Data dari Intent
        val nama = intent.getStringExtra("EXTRA_NAMA") ?: ""
        val email = intent.getStringExtra("EXTRA_EMAIL") ?: ""
        val phone = intent.getStringExtra("EXTRA_PHONE") ?: ""
        val portfolio = intent.getStringExtra("EXTRA_PORTFOLIO") ?: ""

        tvNama.text = nama
        tvEmail.text = email
        tvPhone.text = phone
        tvPortfolio.text = portfolio

        btnWebsite.setOnClickListener {
            val webUrl = if (!portfolio.startsWith("http://") && !portfolio.startsWith("https://")) {
                "https://$portfolio"
            } else {
                portfolio
            }
            val intentUrl = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))
            startActivity(intentUrl)
        }

        // 2. Implicit Intent: Hubungi Saya (Dialer)
        btnHubungi.setOnClickListener {
            val intentDial = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phone")
            }
            startActivity(intentDial)
        }

        // 3. Implicit Intent: Bagikan Kartu (Share Sheet)
        btnBagikan.setOnClickListener {
            val textToShare = """
                Halo, saya $nama.
                
                Email: $email
                WA/HP: $phone
                Portofolio: $portfolio
            """.trimIndent()

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, textToShare)
            }
            startActivity(Intent.createChooser(shareIntent, "Bagikan kartu nama via"))
        }
    }
}