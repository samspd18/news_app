package com.example.newsapp.ui.fragments.news_paper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsPaperBinding

class NewsPaperFragment : Fragment() {

    private var _binding : FragmentNewsPaperBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsPaperBinding.inflate(inflater, container, false)

        val nav = findNavController()

        binding.btnEnglish.setOnClickListener {
            val value = "https://www.w3newspapers.com/india/#English"
            val bundle = Bundle()
            bundle.putString("UrlWebsite", value) //arg.putString("KEY","VALUE")
            nav.navigate(R.id.newsPaperDetailsFragment2,bundle)
        }

        binding.btnHindi.setOnClickListener {
            val value = "https://www.w3newspapers.com/india/hindi/"
            val bundle = Bundle()
            bundle.putString("UrlWebsite", value) //arg.putString("KEY","VALUE")
            nav.navigate(R.id.newsPaperDetailsFragment2,bundle)
        }

        binding.btnMarathi.setOnClickListener {
            val value = "https://www.w3newspapers.com/india/marathi/"
            val bundle = Bundle()
            bundle.putString("UrlWebsite", value) //arg.putString("KEY","VALUE")
            nav.navigate(R.id.newsPaperDetailsFragment2,bundle)
        }

        binding.btnPunjabi.setOnClickListener {
            val value = "https://www.w3newspapers.com/india/punjabi/"
            val bundle = Bundle()
            bundle.putString("UrlWebsite", value) //arg.putString("KEY","VALUE")
            nav.navigate(R.id.newsPaperDetailsFragment2,bundle)
        }

        binding.btnTelugu.setOnClickListener {
            val value = "https://www.w3newspapers.com/india/telugu/"
            val bundle = Bundle()
            bundle.putString("UrlWebsite", value) //arg.putString("KEY","VALUE")
            nav.navigate(R.id.newsPaperDetailsFragment2,bundle)
        }

        binding.btnGujarati.setOnClickListener {
            val value = "https://www.w3newspapers.com/india/gujarati/"
            val bundle = Bundle()
            bundle.putString("UrlWebsite", value) //arg.putString("KEY","VALUE")
            nav.navigate(R.id.newsPaperDetailsFragment2,bundle)
        }

        binding.btnOdia.setOnClickListener {
            val value = "https://www.w3newspapers.com/india/oriya/"
            val bundle = Bundle()
            bundle.putString("UrlWebsite", value) //arg.putString("KEY","VALUE")
            nav.navigate(R.id.newsPaperDetailsFragment2,bundle)
        }

        binding.btnAssamese.setOnClickListener {
            val value = "https://www.w3newspapers.com/india/assamese/"
            val bundle = Bundle()
            bundle.putString("UrlWebsite", value) //arg.putString("KEY","VALUE")
            nav.navigate(R.id.newsPaperDetailsFragment2,bundle)
        }

        binding.btnMalayalam.setOnClickListener {
            val value = "https://www.w3newspapers.com/india/malayalam/"
            val bundle = Bundle()
            bundle.putString("UrlWebsite", value) //arg.putString("KEY","VALUE")
            nav.navigate(R.id.newsPaperDetailsFragment2,bundle)
        }

        binding.btnTamil.setOnClickListener {
            val value = "https://www.w3newspapers.com/india/tamil/"
            val bundle = Bundle()
            bundle.putString("UrlWebsite", value) //arg.putString("KEY","VALUE")
            nav.navigate(R.id.newsPaperDetailsFragment2,bundle)
        }

        binding.btnUrdu.setOnClickListener {
            val value = "https://www.w3newspapers.com/india/urdu/"
            val bundle = Bundle()
            bundle.putString("UrlWebsite", value) //arg.putString("KEY","VALUE")
            nav.navigate(R.id.newsPaperDetailsFragment2,bundle)
        }

        return binding.root
    }
}