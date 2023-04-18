package com.example.myapplication

import android.graphics.BlendMode
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.*
import androidx.appcompat.widget.LinearLayoutCompat

class MainActivity : AppCompatActivity() {
    private lateinit var frameLayout: FrameLayout
    private var appMode = "text"
    private lateinit var penColor: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val canvasView = CanvasView(this)

        val editText = EditText(this).apply {
            hint = "Enter Highlighter Color"
        }
        val colorBtn = Button(this).apply {
            text = "Use Color"
            setOnClickListener {
                penColor = editText.text.toString()
                canvasView.setDrawColor(penColor, appMode)
            }
        }


        val button = Button(this).apply {
            text = "Clear"
            setOnClickListener {
                canvasView.clearAll()
            }
            setBackgroundColor(Color.RED)
            setTextColor(Color.WHITE)
        }
        val bgImg = ImageView(this).apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )

            setImageResource(R.drawable.img)
        }
        val copyright = TextView(this).apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            setText(R.string.img_copyright)
        }
        val textView = TextView(this).apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            setText(R.string.text_view)
        }
        frameLayout = FrameLayout(this).apply {
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            addView(textView)
            addView(canvasView)
        }
        val textBtn = Button(this).apply {
            text = "Highlight Text"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                1f
            )
            setOnClickListener {
                appMode == "text"
                canvasView.clearAll()
                frameLayout.removeAllViews()
                frameLayout.addView(textView)
                frameLayout.addView(canvasView)
                if (appMode != "text"){
                    canvasView.setDrawColor("550000ff", appMode)
                    canvasView.paint.blendMode = BlendMode.XOR
                }

            }
        }
        val imgBtn = Button(this).apply {
            text = "Draw on Image"
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                1f
            )
            setOnClickListener {
                appMode = "image"
                canvasView.clearAll()
                frameLayout.removeAllViews()
                frameLayout.addView(copyright)
                frameLayout.addView(bgImg)
                frameLayout.addView(canvasView)

                if (appMode != "image") {
                    canvasView.setDrawColor("0000ff", appMode)
                    canvasView.paint.blendMode = BlendMode.COLOR
                }
            }
        }
        val btnLayout  = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            weightSum = 2f
            addView(imgBtn)
            addView(textBtn)
        }

        val ll = LinearLayoutCompat(this).apply {
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            addView(button)
            addView(editText)
            addView(colorBtn)

            addView(btnLayout)

            addView(frameLayout)
        }
        setContentView(ll)
        hideSystemUI(ll)
    }

    //pasted from
    // https://github.com/material-components/material-components-android-examples/issues/56
    private fun hideSystemUI (view: View) {
        if (Build.VERSION.SDK_INT >= Build. VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }else {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
            }
        }
}