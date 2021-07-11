package com.example.newsapp.ui.fragments.overview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import coil.load
import com.example.newsapp.R
import com.example.newsapp.bindingadapter.TopHeadLinesRowBinding
import com.example.newsapp.databinding.FragmentOverviewBinding
import com.example.newsapp.models.topheadlines.Article
import java.util.*

class OverviewFragment : Fragment() {

    private var _binding : FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private var news = ""

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentOverviewBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        val args = arguments
        val myBundle : Article = args!!.getParcelable<Article>("articleBundle") as Article

        news = myBundle.url!!

        binding.mainImageView.load(myBundle.urlToImage)
        binding.publishedAt.text = myBundle.publishedAt
        binding.descriptionTextView.text = myBundle.description
        binding.titleTextView.text = myBundle.title
        binding.contentTextView.text = myBundle.content


        TopHeadLinesRowBinding.parseHtml(binding.contentTextView,myBundle.content)

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