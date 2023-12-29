// Catalogue.kt

package uts.c14210184.projectakhir_buddys

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Catalogue : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val db = FirebaseFirestore.getInstance()
    private var dataCatalogue = ArrayList<CatalogueData>()
    private lateinit var _rvCatalogue: RecyclerView
    val db2 = Firebase.firestore


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
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _rvCatalogue = view.findViewById(R.id.rvCatalogue)
        _rvCatalogue.layoutManager = LinearLayoutManager(requireContext())
        val _ivTambah = view.findViewById<ImageView>(R.id.ivTambah)
        val _ivLoved = view.findViewById<ImageView>(R.id.ivLoved)
        val _ivLoveBTN = view.findViewById<ImageView>(R.id.ivLoveBTN)


        val mainActivity = activity as? MainActivity
        val admin = mainActivity?.admin
        val username = mainActivity?.userName
        val gambar = mainActivity?.defaultImageUrl

        if(admin == false){
            _ivTambah.visibility = View.INVISIBLE
        }
        //ivLoved == page fav
        _ivLoved.setOnClickListener {
            val intent = Intent(activity, CatalogueLoved::class.java)
            val article = false
            intent.putExtra("article_back",article)
            intent.putExtra("userName", username)
            intent.putExtra("Gambar", gambar)
            startActivity(intent)
        }
        //ivLoveBTN == button
//        _ivLoveBTN.setOnClickListener {
//            println("TEST")
//        }


        _ivTambah.setOnClickListener {
            val intent = Intent(activity, PostCatalogue::class.java)
            val article = false
            intent.putExtra("article_back",article)
            intent.putExtra("userName", username)
            intent.putExtra("Gambar", gambar)
            startActivity(intent)
        }

        readData(admin,username,gambar)
    }



    private fun readData(admin:Boolean?, username:String?, gambar:String?) {
        db.collection("catalogue")
            .get()
            .addOnSuccessListener { result ->
                dataCatalogue.clear()
                for (document in result) {
                    val catalogueData = CatalogueData(
                        document.getString("image") ?: "",
                        document.getString("name") ?: "",
                        document.getString("categories") ?: "",
                        document.getString("desc") ?: "",
                        document.getBoolean("love") ?: false
                    )
                    dataCatalogue.add(catalogueData)
                }

                Log.d("CatalogueFragment", "Data Retrieved: ${dataCatalogue.size}")

                val layoutManager = GridLayoutManager(requireContext(), 2)
                _rvCatalogue.layoutManager = layoutManager

                val adapter: AdapterCatalogue =
                    if (admin == true) {
                    AdapterCatalogue(dataCatalogue) { data ->
                        val intent = Intent(activity, DetCatalogue::class.java)
                        intent.putExtra("userName", username)
                        intent.putExtra("kirimData", data)
                        intent.putExtra("Gambar", gambar)

                        startActivity(intent)
                    }
                } else {
                    AdapterCatalogue(dataCatalogue) { data ->
                        val intent = Intent(activity, DetCatalogue2::class.java)
                        intent.putExtra("userName", username)
                        intent.putExtra("kirimData", data)
                        intent.putExtra("Gambar", gambar)

                        startActivity(intent)
                    }
                }

                _rvCatalogue.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Catalogue().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
//    fun TambahData(Name: String, Description: String, Categories: String, Image: String, Loved: String, Username:String) {
//        val dataBaru = LovedData(Image, Name, Categories, Description, Loved, Username)
//
//        db2.collection("loved")
//            .document(Name)
//            .set(dataBaru)
//            .addOnSuccessListener {
////                Toast.makeText(
////                    this,
////                    "Data Berhasil disimpan",
////                    Toast.LENGTH_SHORT
////                ).show()
////                val intent = Intent(this, MainActivity::class.java)
////                intent.putExtra("userName", username)
////                intent.putExtra("kirimData", dataBaru)
////                startActivity(intent)
//            }
//            .addOnFailureListener { e ->
//                Log.w(
//                    "PROJ_DMFIREBASE",
//                    "Error adding document",
//                    e
//                )
//            }
//    }

}



