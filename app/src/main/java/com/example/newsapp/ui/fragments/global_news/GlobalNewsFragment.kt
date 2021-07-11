package com.example.newsapp.ui.fragments.global_news

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.GlobalNewsAdapter
import com.example.newsapp.databinding.FragmentGlobalNewsBinding
import com.example.newsapp.ui.fragments.top_headlines.TopHeadLinesFragmentArgs
import com.example.newsapp.util.NetworkResult
import com.example.newsapp.util.observeOnce
import com.example.newsapp.viewModels.MainViewModel
import com.example.newsapp.viewModels.NewsViewModel
import kotlinx.coroutines.launch

class GlobalNewsFragment : Fragment(), SearchView.OnQueryTextListener{

    private lateinit var mainViewModel: MainViewModel
    private lateinit var newsViewModel: NewsViewModel

    private val mAdapter by lazy{ GlobalNewsAdapter() }
    private var _binding : FragmentGlobalNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        newsViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =  FragmentGlobalNewsBinding.inflate(inflater, container, false)

        setUpRecycleView()
        readGlobalNewsDataBase()

        setHasOptionsMenu(true)

        binding.globalNewsSwipeToRefresh.setOnRefreshListener {
            showShimmerEffect()
            requestApiData()
            hideShimmerEffect()
            binding.globalNewsSwipeToRefresh.isRefreshing = false
        }

        return binding.root
    }

    private fun readGlobalNewsDataBase() {
        Log.d("GlobalNews", " Request from database")
        mainViewModel.readGlobalNews.observeOnce(viewLifecycleOwner,{database ->
            if(database.isNotEmpty()){
                mAdapter.setData(database[0].globalNews)
                hideShimmerEffect()
            }else if(database.isNullOrEmpty()){
                requestApiData()
            }
        })
    }


    private fun requestApiData(){
        Log.d("GlobalNews", " Request from api")
        mainViewModel.getGlobalNews(newsViewModel.applyGlobalNewsQueries())
        mainViewModel.globalNewsResponse.observe(viewLifecycleOwner,{ response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireContext(),response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun setUpRecycleView(){
        binding.globalNewsRecyclerview.adapter = mAdapter
        binding.globalNewsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.global_news_menu_xml,menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){

            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


    private fun searchApiData(query: String) {
        mainViewModel.getSearchNews(newsViewModel.applySearchQuery(query))
        mainViewModel.searchNewsResponse.observe(viewLifecycleOwner,{ response->
            when(response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val news = response.data
                    news?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readGlobalNews.observe(viewLifecycleOwner,{database ->
                if(database.isNotEmpty()){
                    mAdapter.setData(database[0].globalNews)
                }
            })
        }
    }

    private fun showShimmerEffect(){
        binding.globalNewsShimmerFrameLayout.startShimmer()
        binding.globalNewsRecyclerview.visibility = View.GONE
        binding.globalNewsShimmerFrameLayout.visibility = View.VISIBLE
    }
    private fun hideShimmerEffect(){
        binding.globalNewsShimmerFrameLayout.stopShimmer()
        binding.globalNewsRecyclerview.visibility = View.VISIBLE
        binding.globalNewsShimmerFrameLayout.visibility = View.GONE
    }

}