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

class RopaFormActivity:AppCompatActivity(){
    private lateinit var b:ActivityRopaFormBinding;private lateinit var dao:RopaDao;private var foto="";private lateinit var categorias:List<Categoria>
    private val picker=registerForActivityResult(ActivityResultContracts.PickVisualMedia()){it?.let(::copiarFoto)}
    override fun onCreate(s:Bundle?){super.onCreate(s);b=ActivityRopaFormBinding.inflate(layoutInflater);setContentView(b.root);dao=RopaDao(this);categorias=dao.categorias();b.toolbar.setNavigationOnClickListener{finish()};b.spCategoria.adapter=ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,categorias);b.spTalla.adapter=ArrayAdapter.createFromResource(this,R.array.tallas,android.R.layout.simple_spinner_dropdown_item);b.btnFoto.setOnClickListener{picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))};b.btnGuardar.setOnClickListener{guardar()}}
    private fun copiarFoto(uri:Uri){val dir=File(filesDir,"prendas").apply{mkdirs()};val out=File(dir,"prenda_${System.currentTimeMillis()}.jpg");contentResolver.openInputStream(uri)?.use{input->out.outputStream().use{input.copyTo(it)}};foto=out.absolutePath;b.ivFoto.setImageURI(uri);b.tvErrorFoto.text=""}
    private fun guardar(){val modelo=b.etModelo.text.toString().trim();val precio=b.etPrecio.text.toString().toDoubleOrNull();val cantidad=b.etCantidad.text.toString().toIntOrNull();b.tilModelo.error=if(modelo.isBlank())getString(R.string.error_modelo)else null;b.tilPrecio.error=if(precio==null||precio<=0)getString(R.string.error_precio)else null;b.tilCantidad.error=if(cantidad==null||cantidad<0)getString(R.string.error_cantidad)else null;b.tvErrorFoto.text=if(foto.isBlank())getString(R.string.error_foto)else "";if(modelo.isBlank()||precio==null||precio<=0||cantidad==null||cantidad<0||foto.isBlank())return;val cat=categorias[b.spCategoria.selectedItemPosition];dao.insertar(Ropa(modelo=modelo,idCategoria=cat.id,talla=b.spTalla.selectedItem.toString(),marca=b.etMarca.text.toString().trim(),color=b.etColor.text.toString().trim(),precio=precio,cantidad=cantidad,foto=foto));Toast.makeText(this,R.string.prenda_guardada,Toast.LENGTH_SHORT).show();finish()}
}
