package uts.c14210184.projectakhir_buddys

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentManager

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditProfile : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mFragmentManager : FragmentManager = parentFragmentManager
        val mainActivity = activity as? MainActivity
        val _etProfileImage = view.findViewById<EditText>(R.id.etProfileImage)
        val _etProfileName = view.findViewById<EditText>(R.id.etProfileName)



        //        Ke Fragment Profile
        val _btnSave = view.findViewById<Button>(R.id.btnSaveEdit)
        _btnSave.setOnClickListener{
            val mProfile = Profile()
            val bundle = Bundle()

            val profileImageText = _etProfileImage.text.toString().trim()
            val profileNameText = _etProfileName.text.toString().trim()
            var admin = false
            if (profileNameText.toString().toUpperCase() == "ADMIN"){
                admin = true
            }


            if (profileImageText.isNotEmpty() && profileNameText.isNotEmpty()) {
                mProfile.arguments = bundle
                mainActivity?.setUserData(profileNameText,profileImageText,admin)
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frameContainer, mProfile, Profile::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            } else {
                // Handle the case where either or both fields are empty
                if (profileImageText.isEmpty()) {
                    _etProfileImage.error = "Please enter profile image"
                }
                if (profileNameText.isEmpty()) {
                    _etProfileName.error = "Please enter profile name"
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}