package com.senati.modaapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.senati.modaapp.data.RopaDao
import com.senati.modaapp.databinding.ActivityRopaBinding
import com.senati.modaapp.ui.RopaAdapter

class RopaActivity:AppCompatActivity(){
    private lateinit var b:ActivityRopaBinding;private val adapter=RopaAdapter()
    override fun onCreate(s:Bundle?){super.onCreate(s);b=ActivityRopaBinding.inflate(layoutInflater);setContentView(b.root);b.toolbar.setNavigationOnClickListener{finish()};b.toolbar.setOnMenuItemClickListener{if(it.itemId==R.id.action_add){startActivity(Intent(this,RopaFormActivity::class.java));true}else false};b.recycler.layoutManager=LinearLayoutManager(this);b.recycler.adapter=adapter}
    override fun onResume(){super.onResume();adapter.submitList(RopaDao(this).listar());b.tvVacio.visibility=if(adapter.itemCount==0)android.view.View.VISIBLE else android.view.View.GONE}
}
