package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.TopHeadLinesRowLayoutBinding
import com.example.newsapp.models.topheadlines.Article
import com.example.newsapp.models.topheadlines.TopHeadLines
import com.example.newsapp.util.diffutil.apiDataCompareDiffUtil

class TopHeadlinesAdapter : RecyclerView.Adapter<TopHeadlinesAdapter.MyViewHolder>() {

    private var topHeadLines = emptyList<Article>()

    class MyViewHolder(private val binding : TopHeadLinesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(article: Article){
                binding.article = article
                binding.executePendingBindings()
            }

        companion object{
            fun from(parent: ViewGroup) : MyViewHolder {
                val layoutInflater  = LayoutInflater.from(parent.context)
                val binding = TopHeadLinesRowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentNews = topHeadLines[position]
        holder.bind(currentNews)
    }

    override fun getItemCount(): Int {
        return topHeadLines.size
    }

    fun setData(newData : TopHeadLines){
        val topHeadLinesDiffUtil = apiDataCompareDiffUtil(topHeadLines,newData.articles)
        val diffUtilResult = DiffUtil.calculateDiff(topHeadLinesDiffUtil)
        topHeadLines = newData.articles
        diffUtilResult.dispatchUpdatesTo(this)
    }
}