package com.senati.modaapp.ui

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.R
import com.senati.modaapp.data.model.Ropa
import com.senati.modaapp.databinding.ItemRopaBinding
import java.io.File

class RopaAdapter(private val onClick: (Ropa) -> Unit = {}) : RecyclerView.Adapter<RopaAdapter.VH>() {
    private var items = emptyList<Ropa>()
    fun submitList(value: List<Ropa>) { items = value; notifyDataSetChanged() }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(ItemRopaBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])
    inner class VH(private val b: ItemRopaBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(r: Ropa) { b.tvModelo.text=r.modelo; b.tvDetalle.text=b.root.context.getString(R.string.detalle_ropa,r.talla,r.color); b.tvStock.text=b.root.context.getString(R.string.stock_formato,r.cantidad); mostrarFoto(b,r.foto); b.root.setOnClickListener { onClick(r) } }
    }
    companion object { fun mostrarFoto(b: ItemRopaBinding, path: String) { if(path.isNotBlank() && File(path).exists()) b.ivFoto.setImageBitmap(BitmapFactory.decodeFile(path)) else b.ivFoto.setImageResource(R.drawable.ic_shirt) } }
}
