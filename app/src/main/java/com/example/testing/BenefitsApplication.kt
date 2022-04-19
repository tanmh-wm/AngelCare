package com.example.testing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.testing.ui.applybenefit.ApplyBenefit
import com.example.testing.ui.user.LoginPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class BenefitsApplication : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var userUid: String
    private lateinit var applicationBenefit: ApplyBenefit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_benefits_application, container, false)

        // set title
        (activity as AppCompatActivity).supportActionBar?.title = "Apply Benefit"

        auth = FirebaseAuth.getInstance()
//        if (auth.currentUser == null) {
//            val intent = Intent(this@BenefitsApplication.requireContext(), LoginPage::class.java)
//            startActivity(intent)
//        } else {
        userUid = auth.currentUser?.uid.toString()
//        }

        val btnSubmit: Button = view.findViewById(R.id.btnSubmit)
        val tfFullName: TextView = view.findViewById(R.id.tfFullName)
        val tfOKUid: TextView = view.findViewById(R.id.tfOKUid)
        val spinBenefitProgram: Spinner = view.findViewById(R.id.spinBenefitProgram)

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.applyType,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinBenefitProgram.adapter = adapter
        }

        btnSubmit.setOnClickListener() {
            if (tfFullName.text.isEmpty()) {
                tfFullName.error = "Please enter your name"
                return@setOnClickListener
            }
            if (tfOKUid.text.isEmpty()) {
                tfOKUid.error = "Please enter OKU id"
                return@setOnClickListener
            }
            if (tfOKUid.text.length != 6){
                tfOKUid.error = "OKU id must in 6 digits"
                return@setOnClickListener
            }
            showAlertDialog()

            val newEvent = ApplyBenefit(
                tfFullName.text.toString(),
                tfOKUid.text.toString(),
                spinBenefitProgram.selectedItem.toString(),
                "Pending"
            )
            insertData(newEvent)

            Navigation.findNavController(view)
                .navigate(R.id.action_benefitsApplication_to_applyBenefitFragment)
        }



        return view
    }

    private fun showAlertDialog() {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Message")
        alertDialog.setMessage("Submit Successfully")
        alertDialog.setPositiveButton(
            "OK"
        ) { _, _ ->
            Toast.makeText(requireContext(), "Alert dialog closed.", Toast.LENGTH_LONG).show()
        }
//        alertDialog.setNegativeButton(
//            "No"
//        ) { _, _ -> }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun insertData(newEvent: ApplyBenefit) {
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val header = formatter.format(now)
        val database: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("ApplicationBenefit")
        database.child(userUid).child(header).setValue(newEvent).addOnSuccessListener {
            Log.d("Apply Benefit Activity", "Successfully to save to firebase")
        }.addOnFailureListener {
            Log.d("Apply Benefit Activity", "Failed to save to firebase")
        }

    }

}