package com.example.newsapp.ui.fragments.top_headlines

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.TopHeadlinesAdapter
import com.example.newsapp.databinding.FragmentTopHeadLinesBinding
import com.example.newsapp.util.NetworkListener
import com.example.newsapp.util.NetworkResult
import com.example.newsapp.util.observeOnce
import com.example.newsapp.viewModels.MainViewModel
import com.example.newsapp.viewModels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TopHeadLinesFragment : Fragment() {

    private val args by navArgs<TopHeadLinesFragmentArgs>()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var newsViewModel : NewsViewModel

    private val mAdapter by lazy { TopHeadlinesAdapter() }
    private var _binding : FragmentTopHeadLinesBinding? = null
    private val binding get() = _binding!!

    private lateinit var networkListener : NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        newsViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTopHeadLinesBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setUpRecycleView()

        newsViewModel.readBackOnline.observe(viewLifecycleOwner,{
            newsViewModel.backOnline = it
        })

        lifecycleScope.launchWhenCreated {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", status.toString())
                    newsViewModel.networkStatus = status
                    newsViewModel.showNetworkStatus()
                    readTopHeadLinesDataBase()
                }
        }

        binding.topHeadLinesFab.setOnClickListener {
            if(newsViewModel.networkStatus){
                findNavController().navigate(R.id.action_topHeadLinesFragment_to_topHeadLineBottomSheetFragment)
            }else{
                showSnackBar()
            }

        }

        binding.topHeadLinesSwipeToRefresh.setOnRefreshListener {
            showShimmerEffect()
            requestApiData()
            hideShimmerEffect()
            binding.topHeadLinesSwipeToRefresh.isRefreshing = false
        }

        return binding.root
    }

    private fun readTopHeadLinesDataBase() {
        Log.d("TopHeadlines", " Request from database")
        mainViewModel.readTopHeadLines.observeOnce(viewLifecycleOwner,{database ->
            if(database.isNotEmpty() && !args.backFromTopHeadLineBottomSheet){
                mAdapter.setData(database[0].topHeadLines)
                hideShimmerEffect()
            }else{
               requestApiData()
            }
        })
    }

    private fun requestApiData(){
        Log.d("TopHeadlines", "requestApiData: Request from api")
        mainViewModel.getTopHeadlines(newsViewModel.applyTopHeadLinesQueries())
        mainViewModel.topHeadLinesResponse.observe(viewLifecycleOwner,{ response ->
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

    private fun loadDataFromCache() {
        lifecycleScope.launch{
            mainViewModel.readTopHeadLines.observe(viewLifecycleOwner,{ database ->
                if(database.isNotEmpty()){
                    mAdapter.setData(database[0].topHeadLines)
                }
            })
        }
    }

    private fun setUpRecycleView(){
        binding.topHeadLinesRecyclerview.adapter = mAdapter
        binding.topHeadLinesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }
    private fun showShimmerEffect(){
        binding.shimmerFrameLayout.startShimmer()
        binding.topHeadLinesRecyclerview.visibility = View.GONE
//        binding.shimmerFrameLayout.visibility = View.VISIBLE
    }
    private fun hideShimmerEffect(){
        binding.shimmerFrameLayout.stopShimmer()
        binding.topHeadLinesRecyclerview.visibility = View.VISIBLE
//        binding.shimmerFrameLayout.visibility = View.GONE
    }

    private fun showSnackBar(){
        Snackbar.make(binding.root, "No Internet Connection",
            Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}