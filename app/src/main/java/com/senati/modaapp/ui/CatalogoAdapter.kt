package com.senati.modaapp.ui

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.R
import com.senati.modaapp.data.model.Ropa
import com.senati.modaapp.databinding.ItemCatalogoBinding
import java.io.File

class CatalogoAdapter(private val onAgregar: (Ropa) -> Unit = {}) : RecyclerView.Adapter<CatalogoAdapter.VH>() {
    private var items=emptyList<Ropa>()
    fun submitList(v:List<Ropa>){items=v;notifyDataSetChanged()}
    override fun onCreateViewHolder(p:ViewGroup,v:Int)=VH(ItemCatalogoBinding.inflate(LayoutInflater.from(p.context),p,false))
    override fun getItemCount()=items.size
    override fun onBindViewHolder(h:VH,p:Int)=h.bind(items[p])
    inner class VH(private val b:ItemCatalogoBinding):RecyclerView.ViewHolder(b.root){ fun bind(r:Ropa){ b.tvModelo.text=r.modelo;b.tvDetalle.text=b.root.context.getString(R.string.detalle_ropa,r.talla,r.color);b.tvPrecio.text=b.root.context.getString(R.string.moneda,r.precio);if(r.foto.isNotBlank()&&File(r.foto).exists())b.ivFoto.setImageBitmap(BitmapFactory.decodeFile(r.foto))else b.ivFoto.setImageResource(R.drawable.ic_shirt);b.btnAgregar.setOnClickListener{onAgregar(r)} } }
}
