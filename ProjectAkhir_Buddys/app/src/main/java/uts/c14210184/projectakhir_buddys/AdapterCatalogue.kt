// AdapterCatalogue.kt

package uts.c14210184.projectakhir_buddys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterCatalogue(
    private val listCatalogue: ArrayList<CatalogueData>,
    private val onItemClickCallback: (CatalogueData) -> Unit
) : RecyclerView.Adapter<AdapterCatalogue.ListViewHolder>() {
    var username: String? = null
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var _tvName: TextView = itemView.findViewById(R.id.item_name)
        var _tvCategories: TextView = itemView.findViewById(R.id.item_categories)
        var _ivCatalogue: ImageView = itemView.findViewById(R.id.ivCatalogue)
        val _ivLoveBTN = itemView.findViewById<ImageView>(R.id.ivLoveBTN)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.catalogue, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listCatalogue.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val catalogue = listCatalogue[position]

        //cek username
        val usernameExists = catalogue.love?.contains(username)
        if (usernameExists == false) {
            holder._ivLoveBTN.setImageResource(R.drawable.unfilledheart)
        } else {
            holder._ivLoveBTN.setImageResource(R.drawable.filledheart)

        }


//        //cek love
//        if(lovetrue){
//            holder._ivLoveBTN.setImageResource(R.drawable.filledheart)
//        }
//        else{
//            holder._ivLoveBTN.setImageResource(R.drawable.unfilledheart)
//        }

        holder._tvName.text = catalogue.name
        holder._tvCategories.text = catalogue.categories

        val resourceId = holder.itemView.context.resources.getIdentifier(
            catalogue.image, "drawable", holder.itemView.context.packageName
        )
        if (resourceId != 0) {
            holder._ivCatalogue.setImageResource(resourceId)
        } else {
            holder._ivCatalogue.setImageResource(R.drawable.bajup)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.invoke(listCatalogue[position])
        }
    }
}
