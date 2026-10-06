package com.senati.modaapp.ui

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.R
import com.senati.modaapp.data.model.DetallePedido
import com.senati.modaapp.databinding.ItemDetallePedidoBinding
import java.io.File

class DetallePedidoAdapter(private val items: List<DetallePedido>) : RecyclerView.Adapter<DetallePedidoAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(ItemDetallePedidoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(items[position])
    class VH(private val binding: ItemDetallePedidoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetallePedido) {
            binding.tvModelo.text = item.modelo
            binding.tvDetalle.text = binding.root.context.getString(R.string.detalle_prenda_pedido, item.cantidad, item.talla, item.color)
            if (item.foto.isNotBlank() && File(item.foto).exists()) binding.ivFoto.setImageBitmap(BitmapFactory.decodeFile(item.foto))
            else binding.ivFoto.setImageResource(R.drawable.ic_shirt)
        }
    }
}
