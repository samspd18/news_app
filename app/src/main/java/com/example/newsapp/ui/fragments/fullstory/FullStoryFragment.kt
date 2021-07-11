package com.example.newsapp.ui.fragments.fullstory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentFullStoryBinding
import com.example.newsapp.models.topheadlines.Article

class FullStoryFragment : Fragment() {

    private var _binding : FragmentFullStoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFullStoryBinding.inflate(
            inflater, container, false)

        val args = arguments
        val myBundle : Article = args!!.getParcelable<Article>("articleBundle") as Article

        binding.fullStoryWebView.webViewClient = object : WebViewClient() {}
        val websiteUrl : String  = myBundle.url!!
        binding.fullStoryWebView.loadUrl(websiteUrl)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}