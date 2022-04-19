package com.example.testing.ui.event

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ListAdapter
import com.example.testing.R
//import androidx.lifecycle.ViewModelProvider
//import com.example.testing.ui.event.EventAdapter
import com.example.testing.databinding.FragmentEventBinding
import com.example.testing.ui.user.LoginPage
import com.example.testing.ui.user.SignUpPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EventFragment : Fragment() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var database: DatabaseReference
    private var eventsList: ArrayList<Event> = ArrayList()
    private var _binding: FragmentEventBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEventBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val adapter =  EventAdapter()
        binding.RecycleViewEvent.adapter = adapter

        return root
    }

    override fun onResume() {
        super.onResume()
        try{

            database = FirebaseDatabase.getInstance().getReference("Event")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        eventsList.clear()
                        for (eventsSnapshot in snapshot.children) {
                            val event = eventsSnapshot.getValue(Event::class.java)
                            eventsList.add(event!!)
                        }
                        (binding.RecycleViewEvent.adapter as ListAdapter<Event, EventAdapter.EventViewHolder>?)?.submitList(eventsList)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Storing Firebase Failed", Toast.LENGTH_SHORT).show()
                }
            })}catch(e:Exception){
            Toast.makeText(requireContext(), "No Existing Event Now!", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}