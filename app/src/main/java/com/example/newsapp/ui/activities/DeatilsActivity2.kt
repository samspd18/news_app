package com.example.newsapp.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.newsapp.adapters.PagerAdapter
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityDeatils2Binding
import com.example.newsapp.ui.fragments.fullstory.FullStoryFragment2
import com.example.newsapp.ui.fragments.overview.OverviewFragment2
import com.google.android.material.tabs.TabLayoutMediator

class DeatilsActivity2 : AppCompatActivity() {

    private lateinit var binding  : ActivityDeatils2Binding
    private val args by navArgs<DeatilsActivity2Args>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeatils2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar2)
        binding.toolbar2.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment2())
        fragments.add(FullStoryFragment2())

        val title = ArrayList<String>()
        title.add("Overview")
        title.add("Full Story")

        val articleBundle = Bundle()
        articleBundle.putParcelable("articleBundle",args.article)

        val pagerAdapter = PagerAdapter(
            articleBundle,
            fragments,
            this
        )

        binding.viewPager2.apply {
            adapter = pagerAdapter
        }
        TabLayoutMediator(binding.tabLayOut2,binding.viewPager2) {tab,position ->
            tab.text = title[position]
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}