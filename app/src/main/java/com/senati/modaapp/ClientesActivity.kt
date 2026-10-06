package com.senati.modaapp
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.senati.modaapp.data.ReporteDao
import com.senati.modaapp.databinding.ActivityClientesBinding
import com.senati.modaapp.ui.TextCard
import com.senati.modaapp.ui.TextCardAdapter
class ClientesActivity:ModaActivity(){private lateinit var b:ActivityClientesBinding;private val adapter=TextCardAdapter();private val todos by lazy{ReporteDao(this).clientes()};override fun onCreate(s:Bundle?){super.onCreate(s);b=ActivityClientesBinding.inflate(layoutInflater);setContentView(b.root);b.toolbar.setNavigationOnClickListener{finish()};b.recycler.layoutManager=LinearLayoutManager(this);b.recycler.adapter=adapter;b.etBuscar.doAfterTextChanged{cargar(it.toString())};cargar("")};private fun cargar(q:String){adapter.submitList(todos.filter{it.nombre.contains(q,true)||it.telefono.contains(q)}.map{TextCard(it.nombre,getString(R.string.cliente_detalle,it.telefono,it.pedidos))})}}
