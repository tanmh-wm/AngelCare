package com.example.testing.ui.my_account

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.testing.databinding.FragmentMyAccountBinding
import com.example.testing.ui.user.LoginPage
import com.google.firebase.ktx.Firebase


class MyAccountFragment : Fragment() {

    private var _binding: FragmentMyAccountBinding? = null


    private lateinit var actionBar: ActionBar

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var database: DatabaseReference
    //rivate var eventsList: ArrayList<User> = ArrayList()

    private  lateinit var uid: String


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!






    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(MyAccountViewModel::class.java)

        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.title = "My Account"

          val reference=FirebaseDatabase.getInstance().getReference("User")
        reference.child(auth.uid!!)
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val email= "${snapshot.child("email").value}"
                    val name="${snapshot.child("name").value}"
                    val phone="${snapshot.child("phone").value}"
                    val okuid="${snapshot.child("okuId").value}"

                    binding.ProfileUsername.setText(name)

                    binding.ProfilePhone.setText(phone)

                    binding.profileEmail.setText(email)

                    binding.okuId.setText(okuid)



                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        binding.buttonLogout.setOnClickListener {
            val intent = Intent(this@MyAccountFragment.requireContext(), LoginPage::class.java)
            startActivity(intent)



        }


        return root
    }



    }



