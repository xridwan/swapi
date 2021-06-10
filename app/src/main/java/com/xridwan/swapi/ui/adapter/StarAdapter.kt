package com.xridwan.swapi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.swapi.R
import com.xridwan.swapi.data.StarShipsModel
import com.xridwan.swapi.databinding.MainItemLayoutBinding

class StarAdapter : RecyclerView.Adapter<StarAdapter.StarViewHolder>() {
    private val starshipsModel = ArrayList<StarShipsModel>()

    fun setData(list: MutableList<StarShipsModel>) {
        starshipsModel.clear()
        starshipsModel.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_item_layout, parent, false)
        return StarViewHolder(view)
    }

    override fun onBindViewHolder(holder: StarViewHolder, position: Int) {
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)

        holder.bind(starshipsModel[position])
        holder.itemView.animation = animation
    }

    override fun getItemCount(): Int = starshipsModel.size

    inner class StarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MainItemLayoutBinding.bind(itemView)
        fun bind(starShipsModel: StarShipsModel) {
            var i = true

            binding.tvName.text = starShipsModel.name
            binding.tvBirth.text =
                itemView.context.getString(R.string.label_model, starShipsModel.model)
            binding.tvGender.text =
                itemView.context.getString(R.string.label_Manufacturer, starShipsModel.manufacturer)
            binding.tvHeight.text =
                itemView.context.getString(R.string.label_Consumables, starShipsModel.consumables)

            binding.imgArrow.setOnClickListener {
                val animation =
                    AnimationUtils.loadAnimation(itemView.context, android.R.anim.fade_in)

                if (i) {
                    binding.imgArrow.setImageResource(R.drawable.ic_arrow)
                    binding.tvGender.visibility = View.VISIBLE
                    binding.tvHeight.visibility = View.VISIBLE

                    binding.tvGender.animation = animation
                    binding.tvHeight.animation = animation
                    i = false

                } else {
                    binding.imgArrow.setImageResource(R.drawable.ic_arrow_right)
                    binding.tvGender.visibility = View.GONE
                    binding.tvHeight.visibility = View.GONE
                    i = true
                }
            }
        }
    }
}