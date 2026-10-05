package com.senati.modaapp.data.model
object Carrito { val items= mutableListOf<ItemCarrito>();val total get()=items.sumOf{it.subtotal};fun agregar(ropa:Ropa,cantidad:Int){val actual=items.find{it.ropa.id==ropa.id};if(actual==null)items.add(ItemCarrito(ropa,cantidad))else actual.cantidad=cantidad};fun limpiar()=items.clear() }
