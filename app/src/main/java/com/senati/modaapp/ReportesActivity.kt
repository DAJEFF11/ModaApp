package com.senati.modaapp
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.senati.modaapp.data.ReporteDao
import com.senati.modaapp.databinding.ActivityReportesBinding
class ReportesActivity:ModaActivity(){override fun onCreate(s:Bundle?){super.onCreate(s);val b=ActivityReportesBinding.inflate(layoutInflater);setContentView(b.root);b.toolbar.setNavigationOnClickListener{finish()};val r=ReporteDao(this).resumen();b.tvVendido.text=getString(R.string.moneda,r.vendido);b.tvAtendidos.text=getString(R.string.reporte_atendidos,r.atendidos);b.tvPendientes.text=getString(R.string.reporte_pendientes,r.pendientes);r.stock.forEach{item->b.contenedorStock.addView(TextView(this).apply{text=getString(R.string.stock_linea,item.modelo,item.cantidad);textSize=15f;setPadding(8,12,8,12);setTextColor(ContextCompat.getColor(this@ReportesActivity,if(item.cantidad<=3)R.color.red_accent else R.color.black))})}}}
