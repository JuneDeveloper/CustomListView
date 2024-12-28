package com.example.customlistview

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class MainActivity2 : AppCompatActivity(),Removable,Updatable {

    var product:Product? = null

    private var products:MutableList<Product> = mutableListOf()
    var listAdapter:ListAdapter? = null
    var item:Int? = null
    var uri:Uri? = null
    var check = true

    private lateinit var toolbarTB:androidx.appcompat.widget.Toolbar

    private lateinit var imageViewIV:ImageView
    private lateinit var nameET:EditText
    private lateinit var priceET:EditText
    private lateinit var buttonAddBTN:Button
    private lateinit var listViewLV:ListView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        identification()

        imageViewIV.setOnClickListener {
            getImage.launch("image/*")
        }
        buttonAddBTN.setOnClickListener {
            val image = imageViewIV.drawable
            uri = Uri.parse(image.toString())
            product = Product(nameET.text.toString(),priceET.text.toString(),uri.toString())
            products.add(product!!)
            listAdapter = ListAdapter(this,products)
            listViewLV.adapter = listAdapter
            listAdapter?.notifyDataSetChanged()
            clear()
            listAdapter?.notifyDataSetChanged()
        }
        listViewLV.onItemClickListener =
            AdapterView.OnItemClickListener{parent,view,position,id ->
                val product = listAdapter!!.getItem(position)
                item = position
                val dialog = MyAlertDialog()
                val args = Bundle()
                args.putSerializable("product",product)
                dialog.arguments = args
                dialog.show(supportFragmentManager,"custom")
            }
    }

    private fun clear() {
        nameET.text.clear()
        priceET.text.clear()
        imageViewIV.setImageResource(R.drawable.baseline_add_24)
    }

    private fun identification() {
        imageViewIV = findViewById(R.id.imageViewIV)
        nameET = findViewById(R.id.nameET)
        priceET = findViewById(R.id.priceET)
        buttonAddBTN = findViewById(R.id.buttonAddBTN)
        listViewLV = findViewById(R.id.listViewLV)
    }

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            try {
                val bitmap = contentResolver.openInputStream(uri)?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
                imageViewIV.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitItem -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun remove(product: Product) {
        listAdapter?.remove(product)
    }

    override fun update(product: Product) {
        val intent = Intent(this,DetailsActivity::class.java)
        intent.putExtra("product",product)
        intent.putExtra("products",this.products as ArrayList<Product>)
        intent.putExtra("position",item)
        intent.putExtra("check", check)
        startActivity(intent)
    }
}



