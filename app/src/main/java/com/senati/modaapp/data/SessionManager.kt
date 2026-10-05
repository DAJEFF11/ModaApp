package com.senati.modaapp.data
import android.content.Context
class SessionManager(context:Context){private val p=context.getSharedPreferences("sesion",Context.MODE_PRIVATE);fun guardar(usuario:String,rol:String)=p.edit().putString("usuario",usuario).putString("rol",rol).apply();fun usuario():String?=p.getString("usuario",null);fun rol():String?=p.getString("rol",null);fun cerrar()=p.edit().clear().apply()}
