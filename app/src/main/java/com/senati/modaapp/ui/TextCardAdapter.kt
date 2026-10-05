package com.senati.modaapp.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.senati.modaapp.databinding.ItemTextCardBinding
data class TextCard(val title:String,val detail:String,val id:Long=0)
class TextCardAdapter(private val click:(TextCard)->Unit={}):RecyclerView.Adapter<TextCardAdapter.VH>(){private var items=emptyList<TextCard>();fun submitList(v:List<TextCard>){items=v;notifyDataSetChanged()};override fun getItemCount()=items.size;override fun onCreateViewHolder(p:ViewGroup,v:Int)=VH(ItemTextCardBinding.inflate(LayoutInflater.from(p.context),p,false));override fun onBindViewHolder(h:VH,p:Int)=h.bind(items[p]);inner class VH(private val b:ItemTextCardBinding):RecyclerView.ViewHolder(b.root){fun bind(i:TextCard){b.tvTitulo.text=i.title;b.tvDetalle.text=i.detail;b.root.setOnClickListener{click(i)}}}}
