package uts.c14210184.projectakhir_buddys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterCatalogue( private val listCatalogue: ArrayList<CatalogueData>) :
        RecyclerView.Adapter<AdapterCatalogue.ListViewHolder>(){

        private lateinit var onItemClickCallback: AdapterCatalogue.OnItemClickCallback

        inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var _tvName: TextView = itemView.findViewById(R.id.item_name)
            var _tvCategories: TextView = itemView.findViewById(R.id.item_categories)
            var _ivCatalogue: ImageView = itemView.findViewById(R.id.ivCatalogue)

            init {
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listCatalogue[adapterPosition])
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.catalogue, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (listCatalogue.isNotEmpty()) {
            listCatalogue.size
        } else {
            // Handle the empty list case, e.g., display a message
            0  // Return 0 to indicate an empty list
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val catalogue = listCatalogue[position]

        holder._tvName.setText(catalogue.item)
//        holder._ivCatalogue.setImageResource(catalogue.gambar)
        holder._tvCategories.setText(catalogue.categories)

        val resourceId = holder.itemView.context.resources.getIdentifier(
            catalogue.gambar, "drawable", holder.itemView.context.packageName
        )

        // Check if the resource ID is valid, then set the image resource
        if (resourceId != 0) {
            holder._ivCatalogue.setImageResource(resourceId)
        } else {
            // Handle case where resource ID is not found (optional)
            // You can set a default image or handle it as needed
            holder._ivCatalogue.setImageResource(R.drawable.bajup)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: CatalogueData)
//        fun delData(pos: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

}