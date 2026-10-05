package com.senati.modaapp.data
import android.content.ContentValues
import android.content.Context
import com.senati.modaapp.data.model.ItemCarrito
import java.text.SimpleDateFormat
import java.util.*
class PedidoDao(context:Context){private val h=DBHelper(context);fun registrar(clienteId:Long,items:List<ItemCarrito>):Long{require(items.isNotEmpty());val db=h.writableDatabase;db.beginTransaction();try{val total=items.sumOf{it.subtotal};val id=db.insertOrThrow("pedido",null,ContentValues().apply{put("id_cliente",clienteId);put("fecha",SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault()).format(Date()));put("total",total);put("estado","PENDIENTE")});items.forEach{db.insertOrThrow("detalle_pedido",null,ContentValues().apply{put("id_pedido",id);put("id_ropa",it.ropa.id);put("cantidad",it.cantidad);put("precio_unit",it.ropa.precio);put("subtotal",it.subtotal)})};db.setTransactionSuccessful();return id}finally{db.endTransaction()}}}
