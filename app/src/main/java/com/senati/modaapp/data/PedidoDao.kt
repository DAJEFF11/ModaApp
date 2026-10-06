package com.senati.modaapp.data

import android.content.ContentValues
import android.content.Context
import com.senati.modaapp.data.model.*
import java.text.SimpleDateFormat
import java.util.*

class PedidoDao(context:Context){
    private val h=DBHelper(context)
    fun registrar(clienteId:Long,items:List<ItemCarrito>):Long{require(items.isNotEmpty());val db=h.writableDatabase;db.beginTransaction();try{val total=items.sumOf{it.subtotal};val id=db.insertOrThrow("pedido",null,ContentValues().apply{put("id_cliente",clienteId);put("fecha",ahora());put("total",total);put("estado","PENDIENTE")});items.forEach{db.insertOrThrow("detalle_pedido",null,ContentValues().apply{put("id_pedido",id);put("id_ropa",it.ropa.id);put("cantidad",it.cantidad);put("precio_unit",it.ropa.precio);put("subtotal",it.subtotal)})};db.setTransactionSuccessful();return id}finally{db.endTransaction()}}
    fun listar(estado:String):List<PedidoResumen>{val sql="SELECT p.id,c.nombres||' '||c.apellidos,c.telefono,p.fecha,p.total,p.estado,COALESCE(SUM(d.cantidad),0) FROM pedido p JOIN cliente c ON c.id=p.id_cliente LEFT JOIN detalle_pedido d ON d.id_pedido=p.id WHERE p.estado=? GROUP BY p.id ORDER BY p.id DESC";return h.readableDatabase.rawQuery(sql,arrayOf(estado)).use{c->buildList{while(c.moveToNext())add(PedidoResumen(c.getLong(0),c.getString(1),c.getString(2),c.getString(3),c.getDouble(4),c.getString(5),c.getInt(6)))}}}
    fun obtener(id:Long):PedidoResumen?{val sql="SELECT p.id,c.nombres||' '||c.apellidos,c.telefono,p.fecha,p.total,p.estado,COALESCE(SUM(d.cantidad),0) FROM pedido p JOIN cliente c ON c.id=p.id_cliente LEFT JOIN detalle_pedido d ON d.id_pedido=p.id WHERE p.id=? GROUP BY p.id";return h.readableDatabase.rawQuery(sql,arrayOf(id.toString())).use{c->if(c.moveToFirst())PedidoResumen(c.getLong(0),c.getString(1),c.getString(2),c.getString(3),c.getDouble(4),c.getString(5),c.getInt(6))else null}}
    fun detalles(id:Long):List<DetallePedido>{val sql="SELECT r.modelo,r.talla,r.color,d.cantidad,d.precio_unit,r.foto FROM detalle_pedido d JOIN ropa r ON r.id=d.id_ropa WHERE d.id_pedido=?";return h.readableDatabase.rawQuery(sql,arrayOf(id.toString())).use{c->buildList{while(c.moveToNext())add(DetallePedido(c.getString(0),c.getString(1),c.getString(2),c.getInt(3),c.getDouble(4),c.getString(5)))}}}
    fun contarPendientes():Int=h.readableDatabase.rawQuery("SELECT COUNT(*) FROM pedido WHERE estado='PENDIENTE'",null).use{it.moveToFirst();it.getInt(0)}
    fun atender(id:Long):String?{val db=h.writableDatabase;db.beginTransaction();try{db.rawQuery("SELECT r.id,r.modelo,r.cantidad,d.cantidad FROM detalle_pedido d JOIN ropa r ON r.id=d.id_ropa WHERE d.id_pedido=?",arrayOf(id.toString())).use{c->while(c.moveToNext()){if(c.getInt(2)<c.getInt(3))return "Stock insuficiente: ${c.getString(1)}"}};db.execSQL("UPDATE ropa SET cantidad=cantidad-(SELECT cantidad FROM detalle_pedido WHERE id_pedido=? AND id_ropa=ropa.id) WHERE id IN (SELECT id_ropa FROM detalle_pedido WHERE id_pedido=?)",arrayOf(id,id));db.update("pedido",ContentValues().apply{put("estado","ATENDIDO");put("fecha_atencion",ahora())},"id=? AND estado='PENDIENTE'",arrayOf(id.toString()));db.setTransactionSuccessful();return null}finally{db.endTransaction()}}
    fun mensaje(id:Long,paraTienda:Boolean=false):String{val p=obtener(id)?:return "";return buildString{appendLine("ModaApp · Pedido #${p.id}");if(paraTienda)appendLine("Cliente: ${p.cliente} · ${p.telefono}");detalles(id).forEach{appendLine("- ${it.cantidad} ${it.modelo} ${it.talla} ${it.color}")};appendLine("Total: S/ %.2f".format(p.total));append("Estado: ${p.estado}")}}
    private fun ahora()=SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault()).format(Date())
}
