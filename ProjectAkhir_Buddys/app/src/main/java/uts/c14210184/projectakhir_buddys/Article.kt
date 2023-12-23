package uts.c14210184.projectakhir_buddys

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        _rvArticle = view.findViewById(R.id.rvArticle)
//        _rvArticle.adapter = AdapterArticle(dataArticle)
//
//        readData()
        _rvArticle = view.findViewById(R.id.rvArticle)
        _rvArticle.layoutManager = LinearLayoutManager(requireContext())

        readData()


    }

//    private fun readData() {
//        db.collection("article")
//            .get()
//            .addOnSuccessListener {
//                    result ->
//                dataArticle.clear()
//                for (document in result) {
//                    val articleData = ArticleData(
//                        document.getString("title") ?: "",
//                        document.getString("description") ?: "",
//                        document.getString("author") ?: "",
//                        document.getString("image") ?: "",
//                        (document.getLong("view") ?: 0).toInt()
//                    )
//                    dataArticle.add(articleData)
//                }
//                _rvArticle.adapter?.notifyDataSetChanged()
//
//                val adapter = AdapterArticle(dataArticle)
//
//                _rvArticle.adapter = AdapterArticle(dataArticle)
//                _rvArticle.layoutManager = LinearLayoutManager(requireContext()) // Add layout manager
//
//
//
//                adapter.setOnItemClickCallback(object : AdapterArticle.OnItemClickCallback {
//                    override fun onItemClicked(data: ArticleData) {
//                        val intent = Intent(requireContext(), DetArticle::class.java)
//                        intent.putExtra("kirimData", data)
//                        startActivity(intent)
//                    }
//
//
//                })
//            }
//            .addOnFailureListener { exception ->
//                Log.w(TAG, "Error getting documents: ", exception)
//                // Handle the failure scenario or log the error
//                Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
//            }
//    }

    private fun readData() {
        db.collection("article")
            .get()
            .addOnSuccessListener { result ->
                dataArticle.clear()
                for (document in result) {
                    val articleData = ArticleData(
                        document.getString("title") ?: "",
                        document.getString("description") ?: "",
                        document.getString("author") ?: "",
                        document.getString("image") ?: "",
                        (document.getLong("view") ?: 0).toInt()
                    )
                    dataArticle.add(articleData)
                }

                val adapter = AdapterArticle(dataArticle)
                _rvArticle.adapter = adapter
                adapter.notifyDataSetChanged()

                adapter.setOnItemClickCallback(object : AdapterArticle.OnItemClickCallback {
                    override fun onItemClicked(data: ArticleData) {
                        val intent = Intent(requireContext(), DetArticle::class.java)
                        intent.putExtra("kirimData", data)
                        startActivity(intent)
                    }
                })
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
                // Handle the failure scenario or log the error
                Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
    }

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
