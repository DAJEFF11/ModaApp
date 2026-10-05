package com.senati.modaapp.data

import android.content.Context
import com.senati.modaapp.data.model.Usuario

class UsuarioDao(context: Context) {
    private val helper = DBHelper(context)
    fun validar(usuario: String, clave: String): Usuario? {
        helper.readableDatabase.rawQuery("SELECT id,usuario,rol,telefono FROM usuario WHERE usuario=? AND clave=?", arrayOf(usuario, clave)).use { c ->
            return if (c.moveToFirst()) Usuario(c.getLong(0), c.getString(1), c.getString(2), c.getString(3)) else null
        }
    }
    fun telefonoAdmin(): String = helper.readableDatabase.rawQuery("SELECT telefono FROM usuario WHERE rol='ADMIN' LIMIT 1", null).use { if (it.moveToFirst()) it.getString(0) else "" }
}
