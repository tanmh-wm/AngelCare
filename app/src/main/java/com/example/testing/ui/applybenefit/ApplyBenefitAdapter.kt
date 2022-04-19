package com.example.testing.ui.applybenefit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.R
import com.example.testing.databinding.ApplicationBinding

//class ApplyBenefitAdapter :
//    ListAdapter<ApplyBenefit, ApplyBenefitAdapter.ApplyBenefitViewHolder>(ApplyBenefitDiffCallBack()) {
//
//    class ApplyBenefitViewHolder(var binding: ApplicationBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//    }
//
//    class ApplyBenefitDiffCallBack() : DiffUtil.ItemCallback<ApplyBenefit>() {
//        override fun areContentsTheSame(oldItem: ApplyBenefit, newItem: ApplyBenefit): Boolean {
//            return oldItem.okuId == newItem.okuId
//        }
//
//        override fun areItemsTheSame(oldItem: ApplyBenefit, newItem: ApplyBenefit): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ApplyBenefitAdapter.ApplyBenefitViewHolder {
//        return ApplyBenefitAdapter.ApplyBenefitViewHolder(
//            ApplicationBinding.inflate(
//                LayoutInflater.from(
//                    parent.context
//                ), parent, false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(
//        holder: ApplyBenefitAdapter.ApplyBenefitViewHolder,
//        position: Int
//    ) {
//        holder.binding.tfName.text = getItem(position).name
//        holder.binding.tfOKU.text = getItem(position).okuId
//        holder.binding.tfProgram.text = getItem(position).applicationProgram
//        holder.binding.tfStatus.text = getItem(position).status
//
////        holder.binding.btnShare.setOnClickListener {
////            val sendIntent: Intent = Intent().apply {
////                action = Intent.ACTION_SEND
////                putExtra(Intent.EXTRA_TEXT, getItem(position).link)
////                putExtra(Intent.EXTRA_TITLE, holder.binding.textEventTitle.text.toString())
////                type = "text/plain"
////            }
////            val shareIntent = Intent.createChooser(sendIntent, null)
////            val activity = holder.itemView.context as Activity
////            ContextCompat.startActivity(activity, shareIntent, null)
////        }
//
//
////        //open browser
////        holder.binding.textEventLink.setOnClickListener {
////            var url = holder.binding.textEventLink.text.toString()
////            if (!url.startsWith("http://") && !url.startsWith("https://")) {
////                url = "http://$url"
////            }
////            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
////            val browseIntent = Intent.createChooser(browserIntent, null)
////            val activity = holder.itemView.context as Activity
////            ContextCompat.startActivity(activity, browseIntent, null)
////        }
//    }
//}


class ApplyBenefitAdapter(private val ApplyBenefitlist: ArrayList<ApplyBenefit>) :
    RecyclerView.Adapter<ApplyBenefitAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tfName: TextView = itemView.findViewById(R.id.tfName)
        val tfOKU: TextView = itemView.findViewById(R.id.tfOKU)
        val tfProgram: TextView = itemView.findViewById(R.id.tfProgram)
        val tfStatus: TextView = itemView.findViewById(R.id.tfStatus)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.application, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentApplyBenefit = ApplyBenefitlist[position]
        holder.tfName.text = currentApplyBenefit.name
        holder.tfOKU.text = currentApplyBenefit.okuId
        holder.tfProgram.text = currentApplyBenefit.applicationProgram
        holder.tfStatus.text = currentApplyBenefit.status
    }

    override fun getItemCount(): Int {
        return ApplyBenefitlist.size
    }
}