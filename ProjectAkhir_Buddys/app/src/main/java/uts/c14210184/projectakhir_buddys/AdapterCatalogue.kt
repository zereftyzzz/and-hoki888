package uts.c14210184.projectakhir_buddys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView

class AdapterCatalogue( private val listCatalogue: ArrayList<CatalogueData>) :
    RecyclerView.Adapter<AdapterCatalogue.ListViewHolder>(){

//        private lateinit var onItemClickCallback: AdapterCatalogue.OnItemClickCallback
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var _tvName: TextView = itemView.findViewById(R.id.item_name)
            var _tvCategories: TextView = itemView.findViewById(R.id.item_categories)
            var _ivCatalogue: ImageView = itemView.findViewById(R.id.ivCatalogue)
//            var _ivBgcard: ImageView = itemView.findViewById(R.id.ivBg)

//            init {
//                itemView.setOnClickListener {
//                    onItemClickCallback.onItemClicked(listCatalogue[adapterPosition])
//                }
//            }
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
//        holder._ivBgcard.setImageResource(R.drawable.bgcard)
//        ImageCatalogue
        val context = holder.itemView.context
        val resourceId = holder.itemView.context.resources.getIdentifier(
            catalogue.gambar, "drawable", holder.itemView.context.packageName
        )
        if (resourceId != 0) {
            holder._ivCatalogue.setImageResource(resourceId)
        } else {
            holder._ivCatalogue.setImageResource(R.drawable.bajup)
        }
        holder._ivCatalogue.setOnClickListener {
//            Toast.makeText(holder.itemView.context, catalogue.item, Toast.LENGTH_SHORT).show()

            onItemClickCallback.onItemClicked(listCatalogue[position])
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