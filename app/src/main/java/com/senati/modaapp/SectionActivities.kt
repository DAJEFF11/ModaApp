package com.senati.modaapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.databinding.ActivitySimpleBinding

abstract class SimpleSectionActivity(private val titleRes: Int) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = ActivitySimpleBinding.inflate(layoutInflater); setContentView(b.root)
        b.toolbar.setTitle(titleRes); b.toolbar.setNavigationOnClickListener { finish() }
        b.tvMensaje.setText(R.string.seccion_pendiente)
    }
}
