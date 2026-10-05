package com.senati.modaapp.ui
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.R
import com.senati.modaapp.data.model.ItemCarrito
import com.senati.modaapp.databinding.ItemCarritoBinding
import java.io.File
class CarritoAdapter(private val quitar:(ItemCarrito)->Unit):RecyclerView.Adapter<CarritoAdapter.VH>(){private var items=emptyList<ItemCarrito>();fun submitList(v:List<ItemCarrito>){items=v;notifyDataSetChanged()};override fun getItemCount()=items.size;override fun onCreateViewHolder(p:ViewGroup,v:Int)=VH(ItemCarritoBinding.inflate(LayoutInflater.from(p.context),p,false));override fun onBindViewHolder(h:VH,p:Int)=h.bind(items[p]);inner class VH(private val b:ItemCarritoBinding):RecyclerView.ViewHolder(b.root){fun bind(i:ItemCarrito){b.tvNombre.text=b.root.context.getString(R.string.item_carrito,i.ropa.modelo,i.ropa.talla);b.tvDetalle.text=b.root.context.getString(R.string.detalle_carrito,i.cantidad,i.ropa.precio);b.tvSubtotal.text=b.root.context.getString(R.string.moneda,i.subtotal);if(i.ropa.foto.isNotBlank()&&File(i.ropa.foto).exists())b.ivFoto.setImageBitmap(BitmapFactory.decodeFile(i.ropa.foto))else b.ivFoto.setImageResource(R.drawable.ic_shirt);b.root.setOnLongClickListener{quitar(i);true}}}}
