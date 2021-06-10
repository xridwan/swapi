package com.xridwan.swapi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.xridwan.swapi.R
import com.xridwan.swapi.data.PeopleModel
import com.xridwan.swapi.databinding.MainItemLayoutBinding

class PeopleAdapter : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {
    private val peopleModel = ArrayList<PeopleModel>()

    fun setData(list: MutableList<PeopleModel>) {
        peopleModel.clear()
        peopleModel.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_item_layout, parent, false)
        return PeopleViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val animation =
            AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)

        holder.bind(peopleModel[position])
        holder.itemView.animation = animation
    }

    override fun getItemCount(): Int = peopleModel.size

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MainItemLayoutBinding.bind(itemView)
        fun bind(peopleModel: PeopleModel) {
            var i = true

            binding.tvName.text = peopleModel.name
            binding.tvBirth.text =
                itemView.context.getString(R.string.label_birth, peopleModel.birth)
            binding.tvGender.text =
                itemView.context.getString(R.string.label_gender, peopleModel.gender)
            binding.tvHeight.text =
                itemView.context.getString(R.string.label_Height, peopleModel.height)

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