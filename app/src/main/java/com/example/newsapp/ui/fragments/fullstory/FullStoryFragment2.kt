package com.example.newsapp.ui.fragments.fullstory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentFullStory2Binding
import com.example.newsapp.databinding.FragmentFullStoryBinding

class FullStoryFragment2 : Fragment() {

    private var _binding: FragmentFullStory2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFullStory2Binding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: com.example.newsapp.models.globalnews.Article =
            args?.getParcelable<com.example.newsapp.models.globalnews
            .Article>("articleBundle") as com.example.newsapp.models.globalnews.Article

        binding.fullStoryWebView2.webViewClient = object : WebViewClient() {}
        val websiteUrl: String = myBundle.url!!
        binding.fullStoryWebView2.loadUrl(websiteUrl)

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}