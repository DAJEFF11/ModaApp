package com.senati.modaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.databinding.ActivitySimpleBinding

class CarritoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = ActivitySimpleBinding.inflate(layoutInflater); setContentView(b.root)
        b.toolbar.title = getString(R.string.carrito); b.toolbar.setNavigationOnClickListener { finish() }
        b.tvMensaje.setText(R.string.lista_vacia)
    }
}
