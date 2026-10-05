package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.senati.modaapp.databinding.ActivityCatalogoBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.senati.modaapp.data.RopaDao
import com.senati.modaapp.ui.CatalogoAdapter
import android.widget.EditText
import android.text.InputType
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.senati.modaapp.data.model.Carrito

class CatalogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = ActivityCatalogoBinding.inflate(layoutInflater); setContentView(b.root)
        b.toolbar.setNavigationOnClickListener { finish() }
        b.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_cart) { startActivity(Intent(this, CarritoActivity::class.java)); true } else false
        }
        val dao=RopaDao(this);val adapter=CatalogoAdapter{r->val input=EditText(this).apply{inputType=InputType.TYPE_CLASS_NUMBER;setText("1")};MaterialAlertDialogBuilder(this).setTitle(R.string.agregar_carrito).setMessage(getString(R.string.disponible,r.cantidad)).setView(input).setNegativeButton(R.string.cancelar,null).setPositiveButton(R.string.agregar){_,_->val n=input.text.toString().toIntOrNull()?:0;if(n in 1..r.cantidad){Carrito.agregar(r,n);Toast.makeText(this,R.string.agregar_carrito,Toast.LENGTH_SHORT).show()}else Toast.makeText(this,getString(R.string.disponible,r.cantidad),Toast.LENGTH_SHORT).show()}.show()};b.recycler.layoutManager=GridLayoutManager(this,2);b.recycler.adapter=adapter
        fun cargar(id:Long?){adapter.submitList(dao.listarDisponibles(id));b.tvVacio.visibility=if(adapter.itemCount==0)android.view.View.VISIBLE else android.view.View.GONE}
        val todas=Chip(this).apply{text=getString(R.string.todas);isCheckable=true;isChecked=true;id=android.view.View.generateViewId()};b.chips.addView(todas)
        todas.setOnClickListener{cargar(null)};dao.categorias().forEach{cat->val chip=Chip(this).apply{text=cat.nombre;isCheckable=true;id=android.view.View.generateViewId()};chip.setOnClickListener{cargar(cat.id)};b.chips.addView(chip)};cargar(null)
    }
}
