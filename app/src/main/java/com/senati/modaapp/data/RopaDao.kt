package com.senati.modaapp.data

import android.content.ContentValues
import android.content.Context
import com.senati.modaapp.data.model.Categoria
import com.senati.modaapp.data.model.Ropa

class RopaDao(context: Context) {
    private val helper = DBHelper(context)
    fun categorias(): List<Categoria> = helper.readableDatabase.rawQuery("SELECT id,nombre FROM categoria ORDER BY id", null).use { c -> buildList { while (c.moveToNext()) add(Categoria(c.getLong(0), c.getString(1))) } }
    fun insertar(ropa: Ropa): Long = helper.writableDatabase.insertOrThrow("ropa", null, values(ropa))
    fun listarDisponibles(idCategoria: Long? = null): List<Ropa> {
        val where = if (idCategoria == null) "r.cantidad > 0" else "r.cantidad > 0 AND r.id_categoria=?"
        val args = idCategoria?.let { arrayOf(it.toString()) }
        return consultar("SELECT r.id,r.modelo,r.id_categoria,c.nombre,r.talla,r.marca,r.color,r.precio,r.cantidad,r.foto FROM ropa r JOIN categoria c ON c.id=r.id_categoria WHERE $where ORDER BY r.id DESC", args)
    }
    fun listar(): List<Ropa> = consultar("SELECT r.id,r.modelo,r.id_categoria,c.nombre,r.talla,r.marca,r.color,r.precio,r.cantidad,r.foto FROM ropa r JOIN categoria c ON c.id=r.id_categoria ORDER BY r.id DESC", null)
    private fun consultar(sql: String, args: Array<String>?): List<Ropa> = helper.readableDatabase.rawQuery(sql, args).use { c -> buildList { while (c.moveToNext()) add(Ropa(c.getLong(0),c.getString(1),c.getLong(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getDouble(7),c.getInt(8),c.getString(9))) } }
    private fun values(r: Ropa) = ContentValues().apply { put("modelo",r.modelo); put("id_categoria",r.idCategoria); put("talla",r.talla); put("marca",r.marca); put("color",r.color); put("precio",r.precio); put("cantidad",r.cantidad); put("foto",r.foto) }
}
