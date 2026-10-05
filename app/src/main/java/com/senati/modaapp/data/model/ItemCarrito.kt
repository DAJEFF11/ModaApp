package com.senati.modaapp.data.model
data class ItemCarrito(val ropa:Ropa,var cantidad:Int){val subtotal get()=ropa.precio*cantidad}
