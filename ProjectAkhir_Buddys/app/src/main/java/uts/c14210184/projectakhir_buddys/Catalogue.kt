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
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Catalogue : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val db = FirebaseFirestore.getInstance()
    private var dataCatalogue = ArrayList<CatalogueData>()
    private lateinit var _rvCatalogue: RecyclerView

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

        readData()
    }

    private fun readData() {
        db.collection("catalogue")
            .get()
            .addOnSuccessListener { result ->
                dataCatalogue.clear()
                for (document in result) {
                    val catalogueData = CatalogueData(
                        document.getString("image") ?: "",
                        document.getString("name") ?: "",
                        document.getString("categories") ?: ""
                    )
                    dataCatalogue.add(catalogueData)
                }

                Log.d("CatalogueFragment", "Data Retrieved: ${dataCatalogue.size}")

                val layoutManager = GridLayoutManager(requireContext(), 2)
                _rvCatalogue.layoutManager = layoutManager

                val adapter = AdapterCatalogue(dataCatalogue) { data ->
                    val intent = Intent(activity, DetCatalogue::class.java)
                    intent.putExtra("kirimData", data)
                    startActivity(intent)
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
}
