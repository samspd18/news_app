package com.example.newsapp.bindingadapter

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.newsapp.R
import com.example.newsapp.models.topheadlines.Article
import com.example.newsapp.ui.fragments.global_news.GlobalNewsFragmentDirections
import com.example.newsapp.ui.fragments.top_headlines.TopHeadLinesFragmentDirections
import org.jsoup.Jsoup

class TopHeadLinesRowBinding {

    companion object{

        @BindingAdapter("onTopHeadLinesClickListener")
        @JvmStatic
        fun onTopHeadLinesClickListener(topHeadLinesRowLayout: ConstraintLayout, article : Article?) {
            Log.d("onNewsClickListener", "called")
            topHeadLinesRowLayout.setOnClickListener {
                try {
                    val action =
                        TopHeadLinesFragmentDirections.actionTopHeadLinesFragmentToDetailsActivity(article!!)
                    topHeadLinesRowLayout.findNavController().navigate(action)
                }catch (e : Exception) {
                    Log.d("onNewsClickListener",e.toString())
                }
            }
        }

        @BindingAdapter("onGlobalNewsClickListener")
        @JvmStatic
        fun onGlobalNewsClickListener(globalNewsRowLayout: ConstraintLayout,article: com.example.newsapp.models.globalnews.Article) {
            Log.d("onNewsClickListener", "called")
            globalNewsRowLayout.setOnClickListener {
                try {
                    val action =
                        GlobalNewsFragmentDirections.actionGlobalNewsFragmentToDeatilsActivity2(article)
                    globalNewsRowLayout.findNavController().navigate(action)
                }catch (e : Exception) {
                    Log.d("onNewsClickListener",e.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView?,imageUrl: String? = null) {

            if(imageUrl == null){
                imageView?.load(R.drawable.ic_error_place_holder){
                    crossfade(600)
                }
            }else{
                imageView?.load(imageUrl) {
                    crossfade(600)
                    error(R.drawable.ic_error_place_holder)
                }
            }
        }
        @BindingAdapter("[arseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView,description : String?) {
            if(description != null) {
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }
    }
}