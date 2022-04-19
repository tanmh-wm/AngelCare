package com.example.testing.ui.applybenefit

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.testing.ApplicationStatus
import com.example.testing.R
import com.example.testing.databinding.FragmentApplyBenefitBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ApplyBenefitFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var uid:String
    private lateinit var applicationBenefit:ApplyBenefit

    private var _binding: FragmentApplyBenefitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel =
            ViewModelProvider(this).get(ApplyBenefitViewModel::class.java)

        _binding = FragmentApplyBenefitBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.title = "Apply Benefit"

        auth = FirebaseAuth.getInstance()
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
////            reload();
//            auth.signOut()
//            Log.d("Event Posting Activity", "onCreate")
//        }
        uid = auth.currentUser?.uid.toString()



        val btnApply: Button = binding.btnApply
        val btnCheck: Button = binding.btnCheck
        val imgvPlus1: ImageView = binding.img1
        val tfInfo1: TextView = binding.tfInfo1
        val imgvPlus2: ImageView = binding.img2
        val tfInfo2: TextView = binding.tfInfo2
        val imgvPlus3: ImageView = binding.img3
        val tfInfo3: TextView = binding.tfInfo3

        // set gone
        tfInfo1.visibility = View.GONE
        tfInfo2.visibility = View.GONE
        tfInfo3.visibility = View.GONE

        btnApply.setOnClickListener() {
            Navigation.findNavController(root)
                .navigate(R.id.action_applyBenefitFragment_to_benefitsApplication)
        }
        btnCheck.setOnClickListener(){
            Navigation.findNavController(root).navigate(R.id.action_applyBenefitFragment_to_applicationStatus)
        }

        imgvPlus1.setOnClickListener() {
            if (tfInfo1.visibility == View.VISIBLE) {
                imgvPlus1.setImageResource(R.drawable.ic_baseline_add_24)
                tfInfo1.visibility = View.GONE
            } else {
                imgvPlus1.setImageResource(R.drawable.ic_baseline_close_24)
                tfInfo1.visibility = View.VISIBLE
            }
        }

        imgvPlus2.setOnClickListener() {
            if (tfInfo2.visibility == View.VISIBLE) {
                imgvPlus2.setImageResource(R.drawable.ic_baseline_add_24)
                tfInfo2.visibility = View.GONE
            } else {
                imgvPlus2.setImageResource(R.drawable.ic_baseline_close_24)
                tfInfo2.visibility = View.VISIBLE
            }
        }

        imgvPlus3.setOnClickListener() {
            if (tfInfo3.visibility == View.VISIBLE) {
                imgvPlus3.setImageResource(R.drawable.ic_baseline_add_24)
                tfInfo3.visibility = View.GONE
            } else {
                imgvPlus3.setImageResource(R.drawable.ic_baseline_close_24)
                tfInfo3.visibility = View.VISIBLE
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}