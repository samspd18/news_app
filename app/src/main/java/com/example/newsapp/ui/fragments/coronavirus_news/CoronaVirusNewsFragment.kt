package com.example.newsapp.ui.fragments.coronavirus_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentCoronaVirusNewsBinding

class CoronaVirusNewsFragment : Fragment() {

    private var _binding : FragmentCoronaVirusNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCoronaVirusNewsBinding.inflate(
            inflater, container, false)

        binding.covidWebViewProgressbar.visibility = View.VISIBLE

        binding.covidWebView.webViewClient = object : WebViewClient() {}
        val websiteUrl = "https://www.mygov.in/covid-19"
        binding.covidWebView.loadUrl(websiteUrl)

        binding.covidWebViewProgressbar.visibility = View.GONE

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}