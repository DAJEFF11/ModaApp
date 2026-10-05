package com.senati.modaapp
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.senati.modaapp.data.model.Carrito
import com.senati.modaapp.databinding.ActivityCarritoBinding
import com.senati.modaapp.ui.CarritoAdapter
class CarritoActivity:AppCompatActivity(){private lateinit var b:ActivityCarritoBinding;private lateinit var adapter:CarritoAdapter;override fun onCreate(s:Bundle?){super.onCreate(s);b=ActivityCarritoBinding.inflate(layoutInflater);setContentView(b.root);b.toolbar.setNavigationOnClickListener{finish()};adapter=CarritoAdapter{item->MaterialAlertDialogBuilder(this).setMessage(R.string.quitar_carrito).setNegativeButton(R.string.cancelar,null).setPositiveButton(R.string.eliminar){_,_->Carrito.items.remove(item);refrescar()}.show()};b.recycler.layoutManager=LinearLayoutManager(this);b.recycler.adapter=adapter;b.btnPedido.setOnClickListener{startActivity(Intent(this,PedidoActivity::class.java))}};override fun onResume(){super.onResume();refrescar()};private fun refrescar(){adapter.submitList(Carrito.items.toList());b.tvTotal.text=getString(R.string.moneda,Carrito.total);b.tvVacio.visibility=if(Carrito.items.isEmpty())View.VISIBLE else View.GONE;b.btnPedido.isEnabled=Carrito.items.isNotEmpty()}}
