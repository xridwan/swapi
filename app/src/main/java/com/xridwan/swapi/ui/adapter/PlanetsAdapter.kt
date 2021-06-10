package com.xridwan.swapi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.swapi.data.PlanetsModel
import com.xridwan.swapi.R
import com.xridwan.swapi.databinding.MainItemLayoutBinding

class PlanetsAdapter : RecyclerView.Adapter<PlanetsAdapter.PlanetsViewHolder>() {
    private val planetsModel = ArrayList<PlanetsModel>()

    fun setData(list: MutableList<PlanetsModel>) {
        planetsModel.clear()
        planetsModel.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_item_layout, parent, false)
        return PlanetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanetsViewHolder, position: Int) {
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)

        holder.bind(planetsModel[position])
        holder.itemView.animation = animation
    }

    override fun getItemCount(): Int = planetsModel.size

    inner class PlanetsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MainItemLayoutBinding.bind(itemView)
        fun bind(planetsModel: PlanetsModel) {
            var i = true

            binding.tvName.text = planetsModel.name
            binding.tvBirth.text =
                itemView.context.getString(R.string.label_terrain, planetsModel.terrain)
            binding.tvGender.text =
                itemView.context.getString(R.string.label_gravity, planetsModel.gravity)
            binding.tvHeight.text =
                itemView.context.getString(R.string.label_populations, planetsModel.populations)

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