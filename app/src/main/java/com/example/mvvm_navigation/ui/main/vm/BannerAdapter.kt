package com.example.mvvm_navigation.ui.main.vm

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mvvm_navigation.R
import com.example.mvvm_navigation.datacenter.data.BannerItem
import com.example.mvvm_navigation.utils.ReflectViewUtils
import com.example.mvvm_navigation.widget.BannerWidget

class BannerAdapter(bannerClickListener: BannerWidget.BannerClickListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var bannerUrlList: List<BannerItem>? = null
    private var context: Context? = null
    private var isTurning: Boolean = false
    private var bannerClickListener: BannerWidget.BannerClickListener? = null

    init {
        this.bannerClickListener = bannerClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = View.inflate(parent.context, R.layout.item_banner, null)
        //一定要設置不然會出現java.lang.IllegalStateException: Pages must fill the whole ViewPager2 (use match_parent)
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        view.layoutParams = lp
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        if (bannerUrlList!![position].imgUrl?.contains(".mp4")!!) {
            holder.banner_image.visibility = View.GONE
            holder.banner_video.visibility = View.VISIBLE
            holder.banner_video.tag = "VideoView"
            //            ((ViewHolder) holder).banner_video.stopPlayback();
            holder.banner_video.setVideoPath(bannerUrlList!![position].imgUrl)
            holder.banner_video.setOnPreparedListener { mp ->
                mp.start()
            }
            holder.banner_video.setOnErrorListener { mp, what, extra ->
                true
            }
            holder.banner_video.setOnCompletionListener { mp -> mp.start() }
        } else {
//            this.context?.let {
//                Glide.with(it).load(bannerUrlList!![position].imgUrl)
//                    .into(holder.banner_image)
//            }
            Glide.with(holder.banner_image).load(bannerUrlList!![position].bitmap).listener(object :
                RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    holder.banner_image.visibility = View.VISIBLE
                    holder.banner_image.post {
                        val para = holder.banner_image.layoutParams
                        para.height = ReflectViewUtils.getDisplayWidth(context) * bannerUrlList!![position].bitmap?.height!! / bannerUrlList!![position].bitmap?.width!!
                        para.width = ReflectViewUtils.getDisplayWidth(context)
                        holder.banner_image.layoutParams = para
                        holder.banner_image.setImageDrawable(resource)
                    }
                    return false
                }
            }).into(holder.banner_image)
            holder.banner_image.visibility = View.VISIBLE
            holder.banner_video.visibility = View.GONE
            holder.banner_image.setOnClickListener { bannerClickListener?.click(holder.adapterPosition) }
        }
    }

    override fun getItemCount(): Int {
        return if (bannerUrlList != null) bannerUrlList!!.size else 0
    }

    fun setData(banners: List<BannerItem>?) {
        bannerUrlList = banners
        notifyDataSetChanged()
    }

    fun setTuningStatus(isTurning: Boolean) {
        this.isTurning = isTurning
        notifyDataSetChanged()
    }

    private class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var banner_video: VideoView = itemView.findViewById(R.id.banner_video)
        var banner_image: ImageView = itemView.findViewById(R.id.banner_image)

    }

}