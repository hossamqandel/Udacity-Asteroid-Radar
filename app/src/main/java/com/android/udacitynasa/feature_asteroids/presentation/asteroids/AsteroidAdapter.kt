package com.android.udacitynasa.feature_asteroids.presentation.asteroids

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.udacitynasa.R
import com.android.udacitynasa.feature_asteroids.domain.model.Asteroid
import com.android.udacitynasa.utils.IClickHelper
import com.android.udacitynasa.utils.setSvgColor

class AsteroidAdapter : RecyclerView.Adapter<AsteroidAdapter.MyViewHolder>() {

    private var mAsteroids: List<Asteroid> = emptyList()
    private var mIClickHelper: IClickHelper? = null
    private var mContext: Context? = null

    fun setAsteroids(asteroids: List<Asteroid>){
        this.mAsteroids = asteroids
        if (asteroids.isNotEmpty()){
            notifyItemChanged(asteroids.size - 1)
        }
    }

    fun setOnItemClick(mIClickHelper: IClickHelper){
        this.mIClickHelper = mIClickHelper
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asteroid, parent, false)
        return MyViewHolder(view)

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tryGet = mAsteroids[position]

        try {
            holder.closeApproachDate.text = tryGet.closeApproachDate
            holder.name.text = tryGet.codename


            if (tryGet.isPotentiallyHazardous){
                holder.isHazardous.contentDescription = mContext?.getString(R.string.potentially_hazardous)
                holder.isHazardous.setSvgColor(R.color.light_red)
            } else {
                holder.isHazardous.contentDescription = mContext?.getString(R.string.not_hazardous)
                holder.isHazardous.setSvgColor(R.color.white)
            }
        }catch (e: Exception){
            Log.e(TAG, "onBindViewHolder: ${e.localizedMessage ?: e.toString()}", )
        }

    }

    override fun getItemCount(): Int = if (mAsteroids == null) 0 else mAsteroids.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val closeApproachDate: TextView = itemView.findViewById(R.id.tv_closedApproachDate)
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val isHazardous: ImageView = itemView.findViewById(R.id.iv_isHazardous)
        val mContext = this@AsteroidAdapter


        init {
            itemView.setOnClickListener {
                mIClickHelper?.onItemClick(mAsteroids[layoutPosition])
            }
        }
    }


}