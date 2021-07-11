package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsPaperDeatilsBinding

class NewsPaperDetailsFragment : Fragment() {

    private var _binding : FragmentNewsPaperDeatilsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsPaperDeatilsBinding.inflate( inflater, container, false)

        val website = arguments?.getString("UrlWebsite")

        binding.newsPaperDetailsWebView.webViewClient = object : WebViewClient() {}
        binding.newsPaperDetailsWebView.loadUrl(website!!)

        return binding.root
    }

}