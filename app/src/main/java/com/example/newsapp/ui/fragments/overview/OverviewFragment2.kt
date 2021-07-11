package com.example.newsapp.ui.fragments.overview

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import coil.load
import com.example.newsapp.R
import com.example.newsapp.bindingadapter.TopHeadLinesRowBinding
import com.example.newsapp.databinding.FragmentOverview2Binding
import com.example.newsapp.databinding.FragmentOverviewBinding
import com.example.newsapp.models.topheadlines.Article

class OverviewFragment2 : Fragment() {

    private var _binding : FragmentOverview2Binding? = null
    private val binding get() = _binding!!

    private var news = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentOverview2Binding.inflate(inflater, container, false)

        val args = arguments
        val myBundle : com.example.newsapp.models.globalnews.Article = args?.getParcelable<com.example.newsapp.models.globalnews
        .Article>("articleBundle") as com.example.newsapp.models.globalnews.Article

        setHasOptionsMenu(true)
        news = myBundle.url!!

        binding.mainImageView2.load(myBundle.urlToImage)
        binding.publishedAt2.text = myBundle.publishedAt
        binding.descriptionTextView2.text = myBundle.description
        binding.titleTextView2.text = myBundle.title
        binding.contentTextView2.text = myBundle.content

        TopHeadLinesRowBinding.parseHtml(binding.contentTextView2,myBundle.content)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overview_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share_overview_menu){
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, news)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}