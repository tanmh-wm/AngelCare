package com.example.testing.ui.event

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing.databinding.LayoutSingleEventBinding


class EventAdapter: ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiffCallBack()) {

    class EventViewHolder(var binding: LayoutSingleEventBinding):RecyclerView.ViewHolder(binding.root){

    }

    class EventDiffCallBack(): DiffUtil.ItemCallback<Event>() {
        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.title == newItem.title
        }
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutSingleEventBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.binding.textEventTitle.text = getItem(position).title
        holder.binding.textEventDate.text = getItem(position).date
        holder.binding.textEventLink.text = getItem(position).link
        holder.binding.textEventDesc.text = getItem(position).desc

        holder.binding.btnShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getItem(position).link)
                putExtra(Intent.EXTRA_TITLE, holder.binding.textEventTitle.text.toString())
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            val activity = holder.itemView.context as Activity
            startActivity(activity, shareIntent, null)
        }

        //open browser
        holder.binding.textEventLink.setOnClickListener {
            var url = holder.binding.textEventLink.text.toString()
            if (!url.startsWith("http://") && !url.startsWith("https://")){
                url = "http://$url"}
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            val browseIntent = Intent.createChooser(browserIntent, null)
            val activity = holder.itemView.context as Activity
            startActivity(activity, browseIntent, null)
        }
    }
}