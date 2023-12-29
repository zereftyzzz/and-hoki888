package uts.c14210184.projectakhir_buddys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class AdapterArticle(
    private val listArticle: ArrayList<ArticleData>,
    private var onItemClickCallback: (ArticleData) -> Unit
) : RecyclerView.Adapter<AdapterArticle.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        var _tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var _tvView: TextView = itemView.findViewById(R.id.tvView)
        var _ivArticle: ImageView = itemView.findViewById(R.id.ivArticle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.article, parent, false)
        return ListViewHolder(view)
    }

//    Menghitung jumlah article
    override fun getItemCount(): Int {
        return    listArticle.size
    }

//    Set isi text dan gambarnya
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val article = listArticle[position]

        holder._tvAuthor.setText(article.author)
        holder._tvTitle.text = article.title
        holder._tvView.text = article.view.toString()
        Picasso.get().load(article.image).into(holder._ivArticle)

        holder.itemView.setOnClickListener {
            onItemClickCallback.invoke(listArticle[position])
        }
    }

}