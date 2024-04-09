package com.example.smarttrade


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrade.nonactivityclasses.category_representation

class CategoryAdapter(
    private val categories: MutableList<category_representation>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.category_select,
                parent,
                false
            )
        )
    }

    fun addAllCategories(list:MutableList<category_representation>){
        for(cat in list){
            categories.add(cat)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val curCat = categories[position]
        val image = holder.itemView.findViewById<ImageView>(R.id.imageViewCat)
        image.setImageResource(curCat.image)
        val catSelector = holder.itemView.findViewById<ConstraintLayout>(R.id.layout)

        catSelector.setOnClickListener{
            //TODO ir a pagina de la categoria

            val catName = curCat.name

            val intentS = Intent(holder.itemView.context, BrowseProductsFiltered::class.java)
            intentS.putExtra("categoryName", catName)

            holder.itemView.context.startActivity(intentS)

        }


    }
}

