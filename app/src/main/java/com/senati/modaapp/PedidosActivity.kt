package com.senati.modaapp
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.senati.modaapp.data.PedidoDao
import com.senati.modaapp.databinding.ActivityPedidosBinding
import com.senati.modaapp.ui.TextCard
import com.senati.modaapp.ui.TextCardAdapter
class PedidosActivity:ModaActivity(){private lateinit var b:ActivityPedidosBinding;private val adapter=TextCardAdapter{startActivity(Intent(this,DetallePedidoActivity::class.java).putExtra("id",it.id))};private var estado="PENDIENTE";override fun onCreate(s:Bundle?){super.onCreate(s);b=ActivityPedidosBinding.inflate(layoutInflater);setContentView(b.root);b.toolbar.setNavigationOnClickListener{finish()};b.recycler.layoutManager=LinearLayoutManager(this);b.recycler.adapter=adapter;b.toggle.addOnButtonCheckedListener{_,id,checked->if(checked){estado=if(id==R.id.btnPendientes)"PENDIENTE" else "ATENDIDO";cargar()}}};override fun onResume(){super.onResume();cargar()};private fun cargar(){val list=PedidoDao(this).listar(estado).map{TextCard(getString(R.string.pedido_fila,it.id,it.cliente),getString(R.string.pedido_detalle,it.fecha,it.prendas,it.total),it.id)};adapter.submitList(list);b.tvVacio.visibility=if(list.isEmpty())View.VISIBLE else View.GONE}}
