package com.example.customlistview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailsActivity : AppCompatActivity() {

    private lateinit var toolbarTB:androidx.appcompat.widget.Toolbar

    private lateinit var detailsImageViewIW:ImageView
    private lateinit var detailsNameET:EditText
    private lateinit var detailsPriceET:EditText
    private lateinit var detailsDescriptionET:EditText

    private val CALLERY_REQUEST = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        detailsImageViewIW = findViewById(R.id.detailsImageViewIW)
        detailsNameET = findViewById(R.id.detailsNameET)
        detailsPriceET = findViewById(R.id.detailsPriceET)
        detailsDescriptionET = findViewById(R.id.detailsDescriptionET)

        val product:Product = intent.extras?.getSerializable("product") as Product
        val products = intent.getSerializableExtra("products")
        val item = intent.extras?.getInt("position")
        var check = intent.extras?.getBoolean("check")

        val name = product.name
        val price = product.price
        val image:Uri? = Uri.parse(product.image)
        val description = product.description
        detailsNameET.setText(name)
        detailsPriceET.setText(price)
        detailsImageViewIW.setImageURI(image)
        detailsDescriptionET.setText(description)

        detailsImageViewIW.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            startActivityForResult(photoPicker, CALLERY_REQUEST)
        }
    }

    private fun swap(item:Int,product: Product, products:MutableList<Product>){
        products.add(item+1,product)
        products.removeAt(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.backItem -> {
                val intent = Intent(this,MainActivity2::class.java)
                startActivity(intent)
                finish()
            }
            R.id.exitItem -> {
                finish()
                Toast.makeText(this,"Программа завершена",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CALLERY_REQUEST -> {
                if(resultCode == RESULT_OK){
                    val newSelectedImage:Uri? = data?.data
                    detailsImageViewIW.setImageURI(newSelectedImage)
                }
            }
        }
    }
}