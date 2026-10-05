package com.senati.modaapp
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.data.PedidoDao
import com.senati.modaapp.data.UsuarioDao
import com.senati.modaapp.databinding.ActivityPedidoConfirmadoBinding
class PedidoConfirmadoActivity:AppCompatActivity(){override fun onCreate(s:Bundle?){super.onCreate(s);val b=ActivityPedidoConfirmadoBinding.inflate(layoutInflater);setContentView(b.root);val id=intent.getLongExtra("id",0);val dao=PedidoDao(this);val p=dao.obtener(id)?:run{finish();return};b.toolbar.setNavigationOnClickListener{finish()};b.tvConfirmacion.text=getString(R.string.pedido_registrado,id);b.tvResumen.text=dao.mensaje(id);b.btnCliente.setOnClickListener{abrirWhatsApp(p.telefono,dao.mensaje(id))};b.btnTienda.setOnClickListener{abrirWhatsApp(UsuarioDao(this).telefonoAdmin(),dao.mensaje(id,true))}}
private fun abrirWhatsApp(telefono:String,mensaje:String){try{startActivity(Intent(Intent.ACTION_VIEW,Uri.parse("https://wa.me/51$telefono?text=${Uri.encode(mensaje)}")).setPackage("com.whatsapp"))}catch(_:ActivityNotFoundException){Toast.makeText(this,R.string.whatsapp_no_instalado,Toast.LENGTH_LONG).show()}}}
