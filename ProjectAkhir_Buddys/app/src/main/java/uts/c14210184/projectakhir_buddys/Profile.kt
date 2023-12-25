package uts.c14210184.projectakhir_buddys

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val db = FirebaseFirestore.getInstance()
    private var dataArticle = ArrayList<ArticleData>()
    private lateinit var _rvMyPost: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val mFragmentManager : FragmentManager = parentFragmentManager

    val userName = arguments?.getString("userName")

    val _tvName = view.findViewById<TextView>(R.id.tvName)
    val _ivImage = view.findViewById<ImageView>(R.id.ivImage)

    val profileImage = arguments?.getString("profileImage")
    var profileName = arguments?.getString("profileName")

    // Set "Welcome, $userName!" if profileName is null or empty (first time use)
    if (profileName.isNullOrEmpty()) {
        _tvName.text = "$userName"
    } else {
        _tvName.text = profileName // Set profileName if available (after edit)
    }

    Picasso.get().load(profileImage).into(_ivImage)

    // Ke Fragment EditProfile
    val _btnEdit = view.findViewById<Button>(R.id.btnEdit)
    _btnEdit.setOnClickListener{
        val mEdit = EditProfile()
        // Pass profileName to EditProfile fragment
        val bundle = Bundle().apply {
            putString("profileName", profileName)
        }
        mEdit.arguments = bundle

        mFragmentManager.beginTransaction().apply {
            replace(R.id.frameContainer, mEdit, EditProfile::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }

    _rvMyPost = view.findViewById(R.id.rvMyPost)
    _rvMyPost.layoutManager = LinearLayoutManager(requireContext())

    readData()
}
    private fun readData() {
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
                _rvMyPost.layoutManager = layoutManager

                val adapter = AdapterArticle(dataArticle) { data ->
                    val intent = Intent(activity, DetMyPost::class.java)
                    intent.putExtra("kirimData", data)
                    startActivity(intent)
                }

                _rvMyPost.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
            }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}