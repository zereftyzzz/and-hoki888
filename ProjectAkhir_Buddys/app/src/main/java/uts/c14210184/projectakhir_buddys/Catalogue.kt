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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Catalogue.newInstance] factory method to
 * create an instance of this fragment.
 */
class Catalogue : Fragment() {
    // TODO: Rename and change types of parameters
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogue, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
        _rvCatalogue = view.findViewById(R.id.rvCatalogue)
        _rvCatalogue.layoutManager = LinearLayoutManager(requireContext())

        readData()

        val layoutManager = GridLayoutManager(requireContext(), 2) // 2 columns
        _rvCatalogue.layoutManager = layoutManager
        val adapter = AdapterCatalogue(dataCatalogue)
        _rvCatalogue.adapter = adapter

        adapter.setOnItemClickCallback(object : AdapterCatalogue.OnItemClickCallback {
            override fun onItemClicked(data: CatalogueData) {
                val intent = Intent(requireContext(), DetCatalogue::class.java)
                intent.putExtra("kirimData", data)
                startActivity(intent)
            }
        })
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
                        document.getString("categories") ?: "",
                    )
                    dataCatalogue.add(catalogueData)
                }

                Log.d("CatalogueFragment", "Data Retrieved: ${dataCatalogue.size}") // Log the size of the data
                val adapter = AdapterCatalogue(dataCatalogue)
                _rvCatalogue.adapter = adapter
                adapter.notifyDataSetChanged()

//                adapter.setOnItemClickCallback(object : AdapterCatalogue.OnItemClickCallback {
//                    override fun onItemClicked(data: CatalogueData) {
//                        val intent = Intent(requireContext(), DetCatalogue::class.java)
//                        intent.putExtra("kirimData", data)
//                        startActivity(intent)
//                    }
//                })
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                // Handle the failure scenario or log the error
                Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Catalogue.
         */
        // TODO: Rename and change types and number of parameters
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

private fun Intent.putExtra(s: String, data: CatalogueData) {

}
