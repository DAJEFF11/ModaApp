package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.databinding.ActivityCatalogoBinding

class CatalogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = ActivityCatalogoBinding.inflate(layoutInflater); setContentView(b.root)
        b.toolbar.setNavigationOnClickListener { finish() }
        b.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_cart) { startActivity(Intent(this, CarritoActivity::class.java)); true } else false
        }
    }
}
