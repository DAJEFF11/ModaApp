package com.senati.modaapp
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.data.ClienteDao
import com.senati.modaapp.data.PedidoDao
import com.senati.modaapp.data.model.Carrito
import com.senati.modaapp.data.model.Cliente
import com.senati.modaapp.databinding.ActivityPedidoBinding
class PedidoActivity:AppCompatActivity(){private lateinit var b:ActivityPedidoBinding;private lateinit var clientes:ClienteDao;private var cliente:Cliente?=null;override fun onCreate(s:Bundle?){super.onCreate(s);b=ActivityPedidoBinding.inflate(layoutInflater);setContentView(b.root);clientes=ClienteDao(this);b.toolbar.setNavigationOnClickListener{finish()};b.tvCantidad.text=getString(R.string.resumen_pedido,Carrito.items.sumOf{it.cantidad});b.tvTotal.text=getString(R.string.moneda,Carrito.total);b.btnContinuar.setOnClickListener{buscar()};b.btnConfirmar.setOnClickListener{confirmar()}}
private fun telefono():String=b.etTelefono.text.toString().trim();private fun buscar(){val t=telefono();b.tilTelefono.error=if(!t.matches(Regex("[0-9]{9}")))getString(R.string.error_telefono)else null;if(!t.matches(Regex("[0-9]{9}")))return;cliente=clientes.buscar(t);if(cliente!=null){b.tvEstado.text=getString(R.string.hola_cliente,cliente!!.nombres);b.grupoNuevo.visibility=View.GONE;b.btnConfirmar.isEnabled=true}else{b.tvEstado.setText(R.string.numero_nuevo);b.grupoNuevo.visibility=View.VISIBLE;b.btnConfirmar.isEnabled=true}}
private fun confirmar(){if(Carrito.items.isEmpty())return;var id=cliente?.id;if(id==null){val n=b.etNombres.text.toString().trim();val a=b.etApellidos.text.toString().trim();b.tilNombres.error=if(n.isBlank())getString(R.string.error_nombres)else null;b.tilApellidos.error=if(a.isBlank())getString(R.string.error_apellidos)else null;if(n.isBlank()||a.isBlank())return;id=clientes.insertar(telefono(),n,a)};val pedido=PedidoDao(this).registrar(id,Carrito.items.toList());Carrito.limpiar();startActivity(Intent(this,PedidoConfirmadoActivity::class.java).putExtra("id",pedido));finish()}}
