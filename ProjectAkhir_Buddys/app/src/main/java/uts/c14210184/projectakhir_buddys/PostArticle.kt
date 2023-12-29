package uts.c14210184.projectakhir_buddys

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import kotlin.random.Random
import androidx.fragment.app.FragmentManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PostArticle : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    val db = Firebase.firestore
    lateinit var _etTitle : EditText
    lateinit var _etDesc : EditText
    lateinit var _etImage : EditText


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mFragmentManager : FragmentManager = parentFragmentManager
        val mainActivity = activity as? MainActivity
        val userName = mainActivity?.userName

        val _btnArticle = view.findViewById<Button>(R.id.btnPost)
        _etTitle = view.findViewById<EditText>(R.id.etTitle)
        _etDesc = view.findViewById<EditText>(R.id.etDesc)
        _etImage = view.findViewById<EditText>(R.id.etImage)

        //   Upload post ke firebase dan kembali ke Fragment Article
        _btnArticle.setOnClickListener{
//            memasukkan data dari editText
            TambahData(
                _etTitle.text.toString(),
                _etDesc.text.toString(),
                userName.toString(),
                _etImage.text.toString()
            )
            val mArticle = Article()
            val mFragmentManager : FragmentManager = parentFragmentManager
//            Kembali ke Fragment Article
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mArticle, Article::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

//    add article
    fun TambahData(Title : String, Description : String, Author: String, Image: String){
        var View = Random.nextInt(20,900)  // menentukan jumlah view menggunakan function random
        val dataBaru = ArticleData(Author, Description, Image, Title, View) // menambah data baru menggunakan data dr editText
        db.collection("article")
            .document(Title)
            .set(dataBaru)
//            ketika berhasil menambah data
            .addOnSuccessListener {
                _etTitle.setText("")
                _etDesc.setText("")
                Toast.makeText(
                    requireContext(),
                    "Data Berhasil disimpan",
                    Toast.LENGTH_SHORT
                ).show()
            }
//            error handling
            .addOnFailureListener { e ->
                Log.w(
                    "PROJ_DMFIREBASE",
                    "Error adding document",
                    e
                )
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_article, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostArticle().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}