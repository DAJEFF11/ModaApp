package com.senati.modaapp.data
import android.content.ContentValues
import android.content.Context
import com.senati.modaapp.data.model.Cliente
import java.text.SimpleDateFormat
import java.util.*
class ClienteDao(context:Context){private val h=DBHelper(context);fun buscar(t:String):Cliente?=h.readableDatabase.rawQuery("SELECT id,telefono,nombres,apellidos,fecha_registro FROM cliente WHERE telefono=?",arrayOf(t)).use{c->if(c.moveToFirst())Cliente(c.getLong(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4))else null};fun insertar(t:String,n:String,a:String):Long=h.writableDatabase.insertOrThrow("cliente",null,ContentValues().apply{put("telefono",t);put("nombres",n);put("apellidos",a);put("fecha_registro",SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault()).format(Date()))})}
