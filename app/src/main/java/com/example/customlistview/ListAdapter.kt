package com.example.customlistview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter(context: Context, productList:MutableList<Product>) :
    ArrayAdapter<Product>(context,R.layout.list_item,productList){

    @SuppressLint("ResourceType")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val product = getItem(position)
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)

        }
        val imageView = view?.findViewById<ImageView>(R.id.productImageViewIV)
        val productNameTV = view?.findViewById<TextView>(R.id.productNameTV)
        val productPriceTV = view?.findViewById<TextView>(R.id.productPriceTV)

        imageView?.setImageDrawable(product?.image)
        productNameTV?.text = product?.name
        productPriceTV?.text = product?.price
        return view!!
    }
}

