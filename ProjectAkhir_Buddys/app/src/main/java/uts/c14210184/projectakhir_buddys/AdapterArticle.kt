package uts.c14210184.projectakhir_buddys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterArticle(private val listArticle: ArrayList<ArticleData>) :
    RecyclerView.Adapter<AdapterArticle.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        var _tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var _tvView: TextView = itemView.findViewById(R.id.tvView)
        var _tvBy: TextView = itemView.findViewById(R.id.tvBy)
        var _ivArticle: ImageView = itemView.findViewById(R.id.ivArticle)

        init {
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listArticle[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.article, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (listArticle.isNotEmpty()) {
            listArticle.size
        } else {
            // Handle the empty list case, e.g., display a message
            0  // Return 0 to indicate an empty list
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val article = listArticle[position]

        holder._tvAuthor.setText(article.author)
//        holder._tvAuthor.text = article.author
        holder._tvTitle.text = article.title
        holder._tvView.text = article.view.toString()
        // Set image using appropriate method based on your image source
        // holder._ivArticle.setImageResource(article.image)
        val resourceId = holder.itemView.context.resources.getIdentifier(
            article.image, "drawable", holder.itemView.context.packageName
        )

        // Check if the resource ID is valid, then set the image resource
        if (resourceId != 0) {
            holder._ivArticle.setImageResource(resourceId)
        } else {
            // Handle case where resource ID is not found (optional)
            // You can set a default image or handle it as needed
            holder._ivArticle.setImageResource(R.drawable.bajup)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ArticleData)
//        fun delData(pos: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}