package com.senati.modaapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.senati.modaapp.data.ClienteDao
import com.senati.modaapp.data.DBHelper
import com.senati.modaapp.data.PedidoDao
import com.senati.modaapp.data.ReporteDao
import com.senati.modaapp.data.RopaDao
import com.senati.modaapp.data.UsuarioDao
import com.senati.modaapp.data.model.ItemCarrito
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ModaAppDatabaseTest {
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun baseLimpia() {
        context.deleteDatabase(DBHelper.DB_NAME)
    }

    @Test
    fun creaCatalogoYValidaLoginDeFormaParametrizada() {
        assertNotNull(UsuarioDao(context).validar("admin", "1234"))
        assertNull(UsuarioDao(context).validar("admin' OR 1=1 --", "x"))
        val ropaDao = RopaDao(context)
        assertEquals(5, ropaDao.categorias().size)
        assertEquals(4, ropaDao.listarDisponibles().size)
        assertTrue(ropaDao.listarDisponibles(1).all { it.categoria == "Polos" })
    }

    @Test
    fun registraPedidoYAtiendeDescontandoStockEnTransaccion() {
        val ropaDao = RopaDao(context)
        val prenda = ropaDao.listarDisponibles().first()
        val clienteId = ClienteDao(context).insertar("987654321", "Lucía", "Torres Medina")
        val pedidoDao = PedidoDao(context)
        val pedidoId = pedidoDao.registrar(clienteId, listOf(ItemCarrito(prenda, 2)))

        assertEquals(1, pedidoDao.contarPendientes())
        assertTrue(pedidoDao.mensaje(pedidoId).contains("Pedido #$pedidoId"))
        assertTrue(pedidoDao.mensaje(pedidoId).contains(prenda.modelo))
        assertTrue(pedidoDao.mensaje(pedidoId).contains("PENDIENTE"))
        assertTrue(pedidoDao.mensaje(pedidoId, true).contains("987654321"))
        assertNull(pedidoDao.atender(pedidoId))
        assertEquals("ATENDIDO", pedidoDao.obtener(pedidoId)?.estado)
        assertEquals(prenda.cantidad - 2, ropaDao.obtener(prenda.id)?.cantidad)
        assertEquals(1, ReporteDao(context).resumen().atendidos)
        assertEquals(1, ReporteDao(context).clientes().first().pedidos)
    }

    @Test
    fun noAtiendeCuandoElStockEsInsuficiente() {
        val prenda = RopaDao(context).listarDisponibles().first()
        val clienteId = ClienteDao(context).insertar("912345678", "Jorge", "Ruiz")
        val pedidoDao = PedidoDao(context)
        val pedidoId = pedidoDao.registrar(clienteId, listOf(ItemCarrito(prenda, prenda.cantidad + 1)))

        assertTrue(pedidoDao.atender(pedidoId)?.startsWith("Stock insuficiente") == true)
        assertEquals("PENDIENTE", pedidoDao.obtener(pedidoId)?.estado)
        assertEquals(prenda.cantidad, RopaDao(context).obtener(prenda.id)?.cantidad)
    }

    @Test
    fun recuerdaYCierraSesionDelAdministrador() {
        val sesion = com.senati.modaapp.data.SessionManager(context)
        sesion.cerrar()
        sesion.guardar("admin", "ADMIN")
        assertEquals("admin", sesion.usuario())
        assertEquals("ADMIN", sesion.rol())
        sesion.cerrar()
        assertNull(sesion.usuario())
    }
}
