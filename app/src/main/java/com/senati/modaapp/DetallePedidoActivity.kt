package com.senati.modaapp
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.senati.modaapp.data.PedidoDao
import com.senati.modaapp.databinding.ActivityDetallePedidoBinding
class DetallePedidoActivity:AppCompatActivity(){override fun onCreate(s:Bundle?){super.onCreate(s);val b=ActivityDetallePedidoBinding.inflate(layoutInflater);setContentView(b.root);val id=intent.getLongExtra("id",0);val dao=PedidoDao(this);val p=dao.obtener(id)?:run{finish();return};b.toolbar.setNavigationOnClickListener{finish()};b.tvCliente.text="${p.cliente}\n${p.telefono} · S/ %.2f".format(p.total);dao.detalles(id).forEach{d->b.contenedor.addView(TextView(this).apply{text="${d.cantidad} × ${d.modelo} · ${d.talla} · ${d.color}";setPadding(8,12,8,12)})};b.btnAtender.visibility=if(p.estado=="PENDIENTE")View.VISIBLE else View.GONE;b.btnAtender.setOnClickListener{MaterialAlertDialogBuilder(this).setMessage(R.string.confirmar_atender).setNegativeButton(R.string.cancelar,null).setPositiveButton(R.string.marcar_atendido){_,_->val error=dao.atender(id);Toast.makeText(this,error?:getString(R.string.pedido_atendido),Toast.LENGTH_LONG).show();if(error==null)finish()}.show()};b.btnLlamar.setOnClickListener{startActivity(Intent(Intent.ACTION_DIAL,Uri.parse("tel:${p.telefono}")))}}}
