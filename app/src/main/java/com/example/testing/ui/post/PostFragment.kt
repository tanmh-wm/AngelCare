package com.example.testing.ui.post

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import com.example.testing.R
//import com.example.testing.databinding.ActivityMainBinding
import com.example.testing.databinding.FragmentPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*



class PostFragment : Fragment() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration


    private var title=""
    private var desc=""
    private var date=""
    private var link=""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(PostViewModel::class.java)

        _binding = FragmentPostBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.title = "Posting Event"

        //choose date
        binding.btnEventDate.setOnClickListener{
            val now = Calendar.getInstance()
            val datePicker = DatePickerDialog( this.requireContext(), { _, year, month, dayOfMonth ->
                val month = month + 1
                val msg = "$dayOfMonth/$month/$year"

                binding.txtEventDate.text = msg
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datePicker.datePicker.minDate = now.timeInMillis
            datePicker.show()
        }


        //Save data
        binding.btnPost.setOnClickListener{
            title = binding.txtEventTitle.text.toString().trim()
            desc = binding.txtEventDesc.text.toString().trim()
            date = binding.txtEventDate.text.toString().trim()
            link = binding.txtEventLink.text.toString().trim()

            if (binding.txtEventTitle.text.isEmpty()) {
                binding.txtEventTitle.error = "Please Insert Title"
                return@setOnClickListener
            } else if (binding.txtEventTitle.text.contains("$")) {
                binding.txtEventTitle.error = "Cannot Contain Special Character"
                return@setOnClickListener
            }
            if (binding.txtEventDate.text == "dd/mm/yyyy") {
                binding.txtEventDate.error = "Please Choose A Date"
                return@setOnClickListener
            }
            if(!URLUtil.isValidUrl(binding.txtEventLink.text.toString())){
                binding.txtEventLink.error = "Please insert a valid link!"
                return@setOnClickListener
            }

            val newEvent = Posting(title,desc,date,link)
            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val header = formatter.format(now)
            val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("Event")

                database.child(header).setValue(newEvent).addOnSuccessListener {
                    binding.txtEventTitle.text.clear()
                    binding.txtEventDesc.text.clear()
                    binding.txtEventDate.text = "dd/mm/yyyy"
                    binding.txtEventLink.text.clear()
                    Log.d("Event Posting Activity", "Successfully to save to firebase")
                    Toast.makeText(
                        this.requireContext(),
                        "Event Added. Please check at event posting",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnFailureListener {
                    Log.d("Event Posting Activity", "Failed to save to firebase")
                }


        }
        return root
    }
}