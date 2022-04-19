package com.example.testing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.databinding.FragmentApplicationStatusBinding
import com.example.testing.ui.applybenefit.ApplyBenefit
import com.example.testing.ui.applybenefit.ApplyBenefitAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class ApplicationStatus : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var userUid: String

    //    private lateinit var uid: String
//    private lateinit var applicationBenefit: ApplyBenefit
    private var applyBenefteList: ArrayList<ApplyBenefit> = ArrayList()
    private lateinit var recyclerViewApplication: RecyclerView
    private lateinit var binding: FragmentApplicationStatusBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentApplicationStatusBinding.inflate(layoutInflater, container, false)

        auth = FirebaseAuth.getInstance()
//        if (auth.currentUser == null) {

//        } else {
            userUid = auth.currentUser?.uid.toString()
//        }

        recyclerViewApplication = binding.applicationRecycleView
        recyclerViewApplication.layoutManager = LinearLayoutManager(activity)
        recyclerViewApplication.setHasFixedSize(true)

        (activity as AppCompatActivity).supportActionBar?.title = "Application Status"
//        auth = FirebaseAuth.getInstance()
//        auth.signInWithEmailAndPassword("tmh99@gmail.com", "789454612").addOnSuccessListener {
//            Toast.makeText(requireContext(), "abc", Toast.LENGTH_LONG).show()
//        }.addOnFailureListener(){
//            Toast.makeText(requireContext(), "fail", Toast.LENGTH_LONG).show()
//        }

//        binding = FragmentApplicationStatusBinding.inflate(layoutInflater)
//        requireActivity().setContentView(binding.root)

//        val recycleview: RecyclerView = view.findViewById(R.id.applicationRecycleView)
//        val applyFormList = listOf(
//            ApplyBenefit("111", "first", "program", "pending"),
//            ApplyBenefit("111", "first", "program", "pending"),
//            ApplyBenefit("111", "first", "program", "pending"),
//            ApplyBenefit("111", "first", "program", "pending"),
//            ApplyBenefit("111", "first", "program", "pending")
//        )
//        val adapter = ApplyBenefitAdapter(applyFormList)
//        recycleview.adapter = adapter
//        recycleview.layoutManager = LinearLayoutManager(requireContext())
//        recycleview.setHasFixedSize(true)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //read data from firebase
        getApplicationBenefitData()
    }

    private fun getApplicationBenefitData() {
        try {
            database = FirebaseDatabase.getInstance().getReference("ApplicationBenefit").child(userUid)
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val applyBenefts = userSnapshot.getValue(ApplyBenefit::class.java)
                            applyBenefteList.add(applyBenefts!!)
                        }
//                        (binding.applicationRecycleView.adapter as androidx.recyclerview.widget.ListAdapter<ApplyBenefit, ApplyBenefitAdapter.ApplyBenefitViewHolder>).submitList(
//                            applyBenefteList
//                        )
                        recyclerViewApplication.adapter = ApplyBenefitAdapter(applyBenefteList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Firebase failed", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

}