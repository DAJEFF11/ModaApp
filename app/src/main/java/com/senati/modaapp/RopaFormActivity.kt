package com.senati.modaapp

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.data.RopaDao
import com.senati.modaapp.data.model.Categoria
import com.senati.modaapp.data.model.Ropa
import com.senati.modaapp.databinding.ActivityRopaFormBinding
import java.io.File
import android.database.sqlite.SQLiteConstraintException
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RopaFormActivity:AppCompatActivity(){
    private lateinit var b:ActivityRopaFormBinding;private lateinit var dao:RopaDao;private var foto="";private lateinit var categorias:List<Categoria>;private var id=0L
    private val picker=registerForActivityResult(ActivityResultContracts.PickVisualMedia()){it?.let(::copiarFoto)}
    override fun onCreate(s:Bundle?){super.onCreate(s);b=ActivityRopaFormBinding.inflate(layoutInflater);setContentView(b.root);dao=RopaDao(this);categorias=dao.categorias();b.toolbar.setNavigationOnClickListener{finish()};b.spCategoria.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,categorias);b.spTalla.adapter=ArrayAdapter.createFromResource(this,R.array.tallas,android.R.layout.simple_spinner_dropdown_item);id=intent.getLongExtra("id",0);if(id>0)cargar();b.btnFoto.setOnClickListener{picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))};b.btnGuardar.setOnClickListener{guardar()};b.btnEliminar.setOnClickListener{MaterialAlertDialogBuilder(this).setMessage(R.string.confirmar_eliminar).setNegativeButton(R.string.cancelar,null).setPositiveButton(R.string.eliminar){_,_->eliminar()}.show()}}
    private fun cargar(){dao.obtener(id)?.let{r->b.toolbar.setTitle(R.string.editar_prenda);b.btnGuardar.setText(R.string.actualizar);b.btnEliminar.visibility=View.VISIBLE;b.etModelo.setText(r.modelo);b.etMarca.setText(r.marca);b.etColor.setText(r.color);b.etPrecio.setText(r.precio.toString());b.etCantidad.setText(r.cantidad.toString());foto=r.foto;categorias.indexOfFirst{it.id==r.idCategoria}.takeIf{it>=0}?.let(b.spCategoria::setSelection);resources.getStringArray(R.array.tallas).indexOf(r.talla).takeIf{it>=0}?.let(b.spTalla::setSelection);if(foto.isNotBlank())b.ivFoto.setImageBitmap(android.graphics.BitmapFactory.decodeFile(foto))}}
    private fun copiarFoto(uri:Uri){val dir=File(filesDir,"prendas").apply{mkdirs()};val out=File(dir,"prenda_${System.currentTimeMillis()}.jpg");contentResolver.openInputStream(uri)?.use{input->out.outputStream().use{input.copyTo(it)}};foto=out.absolutePath;b.ivFoto.setImageURI(uri);b.tvErrorFoto.text=""}
    private fun guardar(){val modelo=b.etModelo.text.toString().trim();val precio=b.etPrecio.text.toString().toDoubleOrNull();val cantidad=b.etCantidad.text.toString().toIntOrNull();b.tilModelo.error=if(modelo.isBlank())getString(R.string.error_modelo)else null;b.tilPrecio.error=if(precio==null||precio<=0)getString(R.string.error_precio)else null;b.tilCantidad.error=if(cantidad==null||cantidad<0)getString(R.string.error_cantidad)else null;b.tvErrorFoto.text=if(foto.isBlank())getString(R.string.error_foto)else "";if(modelo.isBlank()||precio==null||precio<=0||cantidad==null||cantidad<0||foto.isBlank())return;val cat=categorias[b.spCategoria.selectedItemPosition];val r=Ropa(id,modelo,cat.id,cat.nombre,b.spTalla.selectedItem.toString(),b.etMarca.text.toString().trim(),b.etColor.text.toString().trim(),precio,cantidad,foto);if(id>0)dao.actualizar(r)else dao.insertar(r);Toast.makeText(this,if(id>0)R.string.prenda_actualizada else R.string.prenda_guardada,Toast.LENGTH_SHORT).show();finish()}
    private fun eliminar(){try{dao.eliminar(id);Toast.makeText(this,R.string.prenda_eliminada,Toast.LENGTH_SHORT).show();finish()}catch(_:SQLiteConstraintException){Toast.makeText(this,R.string.no_eliminar_pedidos,Toast.LENGTH_LONG).show()}}
}
