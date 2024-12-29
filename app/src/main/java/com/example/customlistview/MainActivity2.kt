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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class MainActivity2 : AppCompatActivity(),Removable,Updatable {

    private var product:Product? = null
    private var products:MutableList<Product> = mutableListOf()
    private var listAdapter:ListAdapter? = null
    private var item:Int? = null
    private var check = true
    private val CALLERY_REQUEST = 1
    private var selectedImage:Uri? = null

    private lateinit var toolbarTB:androidx.appcompat.widget.Toolbar

    private lateinit var imageViewIV:ImageView
    private lateinit var nameET:EditText
    private lateinit var priceET:EditText
    private lateinit var buttonAddBTN:Button
    private lateinit var listViewLV:ListView
    private lateinit var descriptionET:EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        identification()

        imageViewIV.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            startActivityForResult(photoPicker,CALLERY_REQUEST)
        }

        buttonAddBTN.setOnClickListener {
            val image = selectedImage
            product = Product(
                nameET.text.toString(),
                priceET.text.toString(),
                image.toString(),
                descriptionET.text.toString())
            products.add(product!!)
            listAdapter = ListAdapter(this,products)
            listViewLV.adapter = listAdapter
            listAdapter?.notifyDataSetChanged()
            clear()

        }
        listViewLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val product = listAdapter!!.getItem(position)
                item = position
                val dialog = MyAlertDialog()
                val args = Bundle()
                args.putSerializable("product", product)
                dialog.arguments = args
                dialog.show(supportFragmentManager, "custom")
            }
    }

//    override fun onResume() {
//        super.onResume()
//        check = intent.extras?.getBoolean("newCheck") ?: true
//        if(!check){
//            products = intent.getSerializableExtra("list") as MutableList<Product>
//            listAdapter = ListAdapter(this,products)
//            check = true
//        }
//        listViewLV.adapter = listAdapter
//    }

    private fun clear() {
        nameET.text.clear()
        priceET.text.clear()
        imageViewIV.setImageResource(R.drawable.baseline_add_24)
        descriptionET.text.clear()
    }

    private fun identification() {
        imageViewIV = findViewById(R.id.imageViewIV)
        nameET = findViewById(R.id.nameET)
        priceET = findViewById(R.id.priceET)
        buttonAddBTN = findViewById(R.id.buttonAddBTN)
        listViewLV = findViewById(R.id.listViewLV)
        descriptionET = findViewById(R.id.descriptionET)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            CALLERY_REQUEST -> {
                if (resultCode == RESULT_OK){
                    selectedImage = data?.data
                    imageViewIV.setImageURI(selectedImage)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitItem -> {
                finish()
                Toast.makeText(this,"Программа завершена", Toast.LENGTH_SHORT).show()
            }
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





