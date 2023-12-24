package com.saha.genericdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.saha.genericdialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.imageDialog.setOnClickListener(this)
        binding.textDialog.setOnClickListener(this)
        binding.titleBarText.setOnClickListener(this)
        binding.iconDialog.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == binding.textDialog){
            val dialog = GenericDialog.builder(this)
            dialog.setBodyText("Test text")
            //dialog.setTitleText("Test Dialog")
            //getDrawable(R.drawable.ic_launcher_background)?.let { dialog.setImageDrawable(it) }

            /*dialog.setPositiveButton("OK"){
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            }

            dialog.setNegativeButton("Cancel"){
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show()
            }*/
            dialog.build().showDialog()
        }else if (v == binding.titleBarText){
            val dialog = GenericDialog.builder(this)
            dialog.setTitleText("Hello World")
            dialog.setBodyText("Test Body text")

            dialog.setPositiveButton("OK"){
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            }

            dialog.setNegativeButton("Cancel"){
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show()
            }
            dialog.build().showDialog()
        }else if (v == binding.imageDialog){
            val dialog = GenericDialog.builder(this)
            dialog.setTitleText("Hello World")
            dialog.setBodyText("Test Body text")

            getDrawable(R.drawable.ic_launcher_background)?.let { dialog.setImageDrawable(it) }

            dialog.setImageAction {
                Toast.makeText(this, "Image", Toast.LENGTH_SHORT).show()
            }

            dialog.setPositiveButton("OK"){
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            }

            dialog.setNegativeButton("Cancel"){
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show()
            }
            dialog.build().showDialog()
        }else if(v == binding.iconDialog){
            val dialog = GenericDialog.builder(this)
            dialog.setTitleText("Hello World")
            dialog.setBodyText("Test Body text")
            dialog.setIconType(GenericDialog.IconType.WARNING)
            getDrawable(R.drawable.ic_launcher_background)?.let { dialog.setImageDrawable(it) }
            //dialog.setCancelable(false)
            dialog.build().showDialog()
        }
    }
}