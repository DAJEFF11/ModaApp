package com.senati.modaapp

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

/** Mantiene el contenido fuera de la cámara, barra de estado y navegación. */
abstract class ModaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val content = findViewById<ViewGroup>(android.R.id.content)
        val root = content.getChildAt(0) ?: content
        val toolbar = findViewById<View?>(R.id.toolbar)
        val rootPadding = intArrayOf(root.paddingLeft, root.paddingTop, root.paddingRight, root.paddingBottom)
        val toolbarHeight = toolbar?.layoutParams?.height ?: 0
        val toolbarPaddingTop = toolbar?.paddingTop ?: 0
        ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
            val safe = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            if (toolbar != null) {
                toolbar.layoutParams = toolbar.layoutParams.apply { height = toolbarHeight + safe.top }
                toolbar.setPadding(toolbar.paddingLeft, toolbarPaddingTop + safe.top, toolbar.paddingRight, toolbar.paddingBottom)
                root.setPadding(rootPadding[0] + safe.left, rootPadding[1], rootPadding[2] + safe.right, rootPadding[3] + safe.bottom)
            } else {
                root.setPadding(rootPadding[0] + safe.left, rootPadding[1] + safe.top, rootPadding[2] + safe.right, rootPadding[3] + safe.bottom)
            }
            insets
        }
        ViewCompat.requestApplyInsets(root)
    }
}
