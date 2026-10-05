package com.senati.modaapp.data.model
data class PedidoResumen(val id:Long,val cliente:String,val telefono:String,val fecha:String,val total:Double,val estado:String,val prendas:Int)
data class DetallePedido(val modelo:String,val talla:String,val color:String,val cantidad:Int,val precio:Double,val foto:String)
data class ClienteResumen(val nombre:String,val telefono:String,val pedidos:Int)
data class StockResumen(val modelo:String,val cantidad:Int)
data class ReporteResumen(val vendido:Double,val atendidos:Int,val pendientes:Int,val stock:List<StockResumen>)
