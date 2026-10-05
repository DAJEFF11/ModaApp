package com.senati.modaapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onConfigure(db: SQLiteDatabase) { super.onConfigure(db); db.setForeignKeyConstraintsEnabled(true) }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE usuario(id INTEGER PRIMARY KEY AUTOINCREMENT, usuario TEXT UNIQUE NOT NULL, clave TEXT NOT NULL, rol TEXT NOT NULL, telefono TEXT NOT NULL)")
        db.execSQL("CREATE TABLE categoria(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT UNIQUE NOT NULL)")
        db.execSQL("CREATE TABLE ropa(id INTEGER PRIMARY KEY AUTOINCREMENT, modelo TEXT NOT NULL, id_categoria INTEGER NOT NULL REFERENCES categoria(id), talla TEXT NOT NULL, marca TEXT, color TEXT, precio REAL NOT NULL CHECK(precio > 0), cantidad INTEGER NOT NULL CHECK(cantidad >= 0), foto TEXT NOT NULL)")
        crearOperacion(db)
        db.execSQL("INSERT INTO usuario(usuario,clave,rol,telefono) VALUES(?,?,?,?)", arrayOf("admin", "1234", "ADMIN", "987654321"))
        listOf("Polos", "Pantalones", "Vestidos", "Casacas", "Zapatillas").forEach { db.execSQL("INSERT INTO categoria(nombre) VALUES(?)", arrayOf(it)) }
        db.execSQL("INSERT INTO ropa(modelo,id_categoria,talla,marca,color,precio,cantidad,foto) VALUES('Polo oversize',1,'M','Urban','Azul',39.90,12,'')")
        db.execSQL("INSERT INTO ropa(modelo,id_categoria,talla,marca,color,precio,cantidad,foto) VALUES('Polo básico',1,'S','Moda','Negro',29.90,24,'')")
        db.execSQL("INSERT INTO ropa(modelo,id_categoria,talla,marca,color,precio,cantidad,foto) VALUES('Polo estampado',1,'L','Street','Rojo',45.00,15,'')")
        db.execSQL("INSERT INTO ropa(modelo,id_categoria,talla,marca,color,precio,cantidad,foto) VALUES('Polo cuello V',1,'M','Classic','Blanco',32.00,10,'')")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) { if(oldVersion < 2) crearOperacion(db) }
    private fun crearOperacion(db:SQLiteDatabase){
        db.execSQL("CREATE TABLE IF NOT EXISTS cliente(id INTEGER PRIMARY KEY AUTOINCREMENT, telefono TEXT UNIQUE NOT NULL, nombres TEXT NOT NULL, apellidos TEXT NOT NULL, fecha_registro TEXT NOT NULL)")
        db.execSQL("CREATE TABLE IF NOT EXISTS pedido(id INTEGER PRIMARY KEY AUTOINCREMENT, id_cliente INTEGER NOT NULL REFERENCES cliente(id), fecha TEXT NOT NULL, total REAL NOT NULL, estado TEXT NOT NULL CHECK(estado IN ('PENDIENTE','ATENDIDO')), fecha_atencion TEXT)")
        db.execSQL("CREATE TABLE IF NOT EXISTS detalle_pedido(id INTEGER PRIMARY KEY AUTOINCREMENT, id_pedido INTEGER NOT NULL REFERENCES pedido(id) ON DELETE CASCADE, id_ropa INTEGER NOT NULL REFERENCES ropa(id), cantidad INTEGER NOT NULL CHECK(cantidad>0), precio_unit REAL NOT NULL, subtotal REAL NOT NULL)")
    }
    companion object { const val DB_NAME = "modaapp.db"; const val DB_VERSION = 2 }
}
