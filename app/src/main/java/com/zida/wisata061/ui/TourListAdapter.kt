package com.zida.wisata061.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zida.wisata061.R
import com.zida.wisata061.model.Tour

class TourListAdapter(
    private val onItemClickListener: (Tour) -> Unit
): ListAdapter<Tour, TourListAdapter.TourViewModel>(WORDS_COMPARATOR){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TourViewModel {
        return TourViewModel.create(parent)
    }

    override fun onBindViewHolder(holder: TourViewModel, position: Int) {
        val tour = getItem(position)
        holder.bind(tour)
        holder.itemView.setOnClickListener {
            onItemClickListener(tour)
        }
    }

    class TourViewModel(itemview: View) : RecyclerView.ViewHolder(itemview){
        private val nametextView: TextView = itemview.findViewById(R.id.nametextView)
        private val addresstextView: TextView = itemview.findViewById(R.id.addresstextView)
        private val streettextView: TextView = itemview.findViewById(R.id.streettextView)

        fun bind(tour: Tour?) {
            nametextView.text=tour?.name
            addresstextView.text=tour?.address
            streettextView.text=tour?.street

        }

        companion object {
            fun create(parent: ViewGroup): TourListAdapter.TourViewModel {
                val view: View =LayoutInflater.from(parent.context)
                    .inflate(R.layout.item,parent,false)
                return TourViewModel(view)
            }
        }

    }
    companion object{
        private val WORDS_COMPARATOR= object :DiffUtil.ItemCallback<Tour>(){
            override fun areItemsTheSame(oldItem: Tour, newItem: Tour): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Tour, newItem: Tour): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}