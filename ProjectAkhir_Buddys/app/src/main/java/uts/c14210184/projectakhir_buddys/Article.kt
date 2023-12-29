package uts.c14210184.projectakhir_buddys

import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Article : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private val db = FirebaseFirestore.getInstance()
    private var dataArticle = ArrayList<ArticleData>()
    private lateinit var _rvArticle: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as? MainActivity
        _rvArticle = view.findViewById(R.id.rvArticle)
        _rvArticle.layoutManager = LinearLayoutManager(requireContext())
        val username = mainActivity?.userName
        val gambar = mainActivity?.defaultImageUrl
        readData(username,gambar)
    }

    private fun readData(username:String?,gambar:String?) {
        db.collection("article")
            .get()
            .addOnSuccessListener {
                    result ->
                dataArticle.clear()
                for (document in result) {
                    val articleData = ArticleData(
                        document.getString("author") ?: "",
                        document.getString("description") ?: "",
                        document.getString("image") ?: "",
                        document.getString("title") ?: "",
                        (document.getLong("view") ?: 0).toInt()
                    )
                    dataArticle.add(articleData)
                }

                Log.d("Article Fragment", "Data Retrieved: ${dataArticle.size}") // Log the size of the data

                val layoutManager = LinearLayoutManager(requireContext()) // 1 columns
                _rvArticle.layoutManager = layoutManager

                val adapter = AdapterArticle(dataArticle) { data ->
                    val intent = Intent(activity, DetArticle::class.java)
                    intent.putExtra("userName", username)
                    intent.putExtra("kirimData", data)
                    intent.putExtra("Gambar", gambar)
                    startActivity(intent)
                }

                _rvArticle.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Article().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
