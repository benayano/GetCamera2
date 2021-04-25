package com.example.getcamera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object{
        private const val  CAMERA_REQUEST_ID =10
    }

    private val imageView:ImageView by lazy {findViewById(R.id.imageView)  }
    private val button:Button by lazy {findViewById(R.id.button)  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestCameraPermission()
        button.setOnClickListener {
            if (permissionCameraGranted()){
                getCamera()
            }else{
                Toast.makeText(this," אין הרשאת גישה למצלמה!!!",Toast.LENGTH_LONG).show()
                requestCameraPermission()
            }
        }

    }
    private fun permissionCameraGranted():Boolean {
        return ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            CAMERA_REQUEST_ID->changeImage(data)
        }
    }

    private fun requestCameraPermission() {
        if (!permissionCameraGranted()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_ID)
        }
    }
    private fun getCamera(){
        val cameraInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraInt, CAMERA_REQUEST_ID)
    }

    private fun changeImage(data: Intent?){
        val images:Bitmap = data?.extras?.get("data") as Bitmap
        imageView.setImageBitmap(images)
    }
}
























