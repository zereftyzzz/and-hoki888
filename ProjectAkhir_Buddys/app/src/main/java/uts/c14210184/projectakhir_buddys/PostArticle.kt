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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostArticle.newInstance] factory method to
 * create an instance of this fragment.
 */
class PostArticle : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val db = Firebase.firestore
    lateinit var _etTitle : EditText
    lateinit var _etDesc : EditText
    lateinit var _etAuthor : EditText
    lateinit var _etImage : EditText

    var dataArticle = ArrayList<ArticleData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //        Kembali ke Fragment Article dan upload post ke firebase
        val _btnArticle = view.findViewById<Button>(R.id.btnPost)
        _etTitle = view.findViewById<EditText>(R.id.etTitle)
        _etDesc = view.findViewById<EditText>(R.id.etDesc)
        _etAuthor = view.findViewById<EditText>(R.id.etAuthor)
        _etImage = view.findViewById<EditText>(R.id.etImage)

        _btnArticle.setOnClickListener{
            TambahData(
                _etTitle.text.toString(),
                _etDesc.text.toString(),
                _etAuthor.text.toString(),
                _etImage.text.toString()
            )
            val mArticle = Article()
            val mFragmentManager : FragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mArticle, Article::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }

//    Firebase
//
    fun TambahData(Title : String, Description : String, Author: String, Image: String){
        var View = Random.nextInt(20,900)
        val dataBaru = ArticleData(Title, Description, Author, Image, View)
        db.collection("article")
            .document(Title)
            .set(dataBaru)
            .addOnSuccessListener {
                _etTitle.setText("")
                _etDesc.setText("")
                Toast.makeText(
                    requireContext(), // Use requireContext() here
                    "Data Berhasil disimpan",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                Log.w(
                    "PROJ_DMFIREBASE",
                    "Error adding document",
                    e
                )
            }
    }

    fun ReadData(){
        db.collection("article")
            .get()
            .addOnSuccessListener {
                    result ->
                dataArticle.clear()
                for (document in result){
                    val hasil = ArticleData (
                        document.data.get("title").toString(),
                        document.data.get("description").toString(),
                        document.data.get("author").toString(),
                        document.data.get("image").toString(),
                        document.data.get("view") as Int
                    )
                    dataArticle.add(hasil)
                }
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PostArticle.
         */
        // TODO: Rename and change types and number of parameters
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