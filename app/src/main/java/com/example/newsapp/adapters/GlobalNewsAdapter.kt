package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.GlobalNewsRowLayoutBinding
import com.example.newsapp.models.globalnews.Article
import com.example.newsapp.models.globalnews.GlobalNews
import com.example.newsapp.util.diffutil.apiDataCompareDiffUtil

class GlobalNewsAdapter : RecyclerView.Adapter<GlobalNewsAdapter.MyViewHolder>() {

    private var globalNews = emptyList<Article>()

    class MyViewHolder(private val binding : GlobalNewsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(article: Article){
                binding.article = article
                binding.executePendingBindings()
            }

        companion object{
            fun form(parent: ViewGroup) : MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GlobalNewsRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.form(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentGlobalNews = globalNews[position]
        holder.bind(currentGlobalNews)
    }

    override fun getItemCount(): Int {
        return globalNews.size
    }

    fun setData(newData : GlobalNews){
        val globalNewsDiffUtil = apiDataCompareDiffUtil(globalNews,newData.articles)
        val diffUtilResult = DiffUtil.calculateDiff(globalNewsDiffUtil)
        globalNews = newData.articles
        diffUtilResult.dispatchUpdatesTo(this)
    }
}