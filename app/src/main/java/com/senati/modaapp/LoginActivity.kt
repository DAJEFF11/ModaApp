package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etUsuario.setText("admin")
        binding.btnIngresar.setOnClickListener {
            val usuario = binding.etUsuario.text?.toString()?.trim().orEmpty()
            val clave = binding.etClave.text?.toString().orEmpty()
            binding.tilUsuario.error = if (usuario.isBlank()) getString(R.string.error_usuario) else null
            binding.tilClave.error = if (clave.isBlank()) getString(R.string.error_clave) else null
            if (usuario.isBlank() || clave.isBlank()) return@setOnClickListener
            if (usuario == "admin" && clave == "1234") {
                startActivity(Intent(this, MenuActivity::class.java).putExtra("usuario", usuario))
                finish()
            } else Toast.makeText(this, R.string.credenciales_incorrectas, Toast.LENGTH_SHORT).show()
        }
        binding.btnCliente.setOnClickListener { startActivity(Intent(this, CatalogoActivity::class.java)) }
    }
}
