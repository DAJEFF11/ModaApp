package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.databinding.ActivityMenuBinding
import com.senati.modaapp.data.SessionManager

class MenuActivity : ModaActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvSaludo.text = getString(R.string.hola_usuario, intent.getStringExtra("usuario") ?: "admin")
        binding.tvRol.text = getString(R.string.rol_usuario, intent.getStringExtra("rol") ?: SessionManager(this).rol() ?: "ADMIN")
        binding.cardRopa.setOnClickListener { abrir(RopaActivity::class.java) }
        binding.cardPedidos.setOnClickListener { abrir(PedidosActivity::class.java) }
        binding.cardClientes.setOnClickListener { abrir(ClientesActivity::class.java) }
        binding.cardReportes.setOnClickListener { abrir(ReportesActivity::class.java) }
        binding.btnSalir.setOnClickListener {
            SessionManager(this).cerrar()
            startActivity(Intent(this, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()
        }
    }
    override fun onResume() {
        super.onResume()
        binding.tvPendientes.text = getString(R.string.pedidos_pendientes_count, com.senati.modaapp.data.PedidoDao(this).contarPendientes())
    }
    private fun abrir(destino: Class<*>) = startActivity(Intent(this, destino))
}
