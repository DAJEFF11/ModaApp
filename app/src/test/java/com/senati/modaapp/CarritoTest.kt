package com.senati.modaapp
import com.senati.modaapp.data.model.Carrito
import com.senati.modaapp.data.model.Ropa
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
class CarritoTest{
    @After fun limpiar()=Carrito.limpiar()
    @Test fun `recalcula total y reemplaza cantidad de la misma prenda`(){val ropa=Ropa(1,"Polo",1,"Polos","M","Urban","Azul",39.9,12,"");Carrito.agregar(ropa,2);Carrito.agregar(ropa,3);assertEquals(1,Carrito.items.size);assertEquals(119.7,Carrito.total,0.001)}
}
