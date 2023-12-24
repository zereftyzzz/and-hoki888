package uts.c14210184.projectakhir_buddys

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.squareup.picasso.Picasso


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val mFragmentManager : FragmentManager = parentFragmentManager
//
//        val userName = arguments?.getString("userName")
//
//        val _tvName = view.findViewById<TextView>(R.id.tvName)
//        val _ivImage = view.findViewById<ImageView>(R.id.ivImage)
//
//        val profileImage = arguments?.getString("profileImage")
//        val profileName = arguments?.getString("profileName")
//
//        Picasso.get().load(profileImage).into(_ivImage)
//        _tvName.text = "$userName!"
//        _tvName.text = profileName
//
//        //        Ke Fragment EditProfile
//        val _btnEdit = view.findViewById<Button>(R.id.btnEdit)
//        _btnEdit.setOnClickListener{
//            val mEdit = EditProfile()
//            mFragmentManager.beginTransaction().apply {
//                replace(R.id.frameContainer, mEdit, EditProfile::class.java.simpleName)
//                addToBackStack(null)
//                commit()
//            }
//        }
//    }
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
}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
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