package com.example.newsapp.ui.fragments.top_headlines.topheadlinesbottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.newsapp.databinding.FragmentTopHeadLineBottomSheetBinding
import com.example.newsapp.util.Constants.Companion.DEFAULT_CATEGORY
import com.example.newsapp.viewModels.NewsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

class TopHeadLineBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var newsViewModel: NewsViewModel

    private var categoryTypeChip = DEFAULT_CATEGORY
    private var categoryTypeChipId = 0

    private var _binding : FragmentTopHeadLineBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopHeadLineBottomSheetBinding.inflate(inflater, container, false)

        newsViewModel.readCategoryType.asLiveData().observe(viewLifecycleOwner, { value ->
            categoryTypeChip = value.selectedCategoryType
            updateChip(value.selectedCategoryTypeId,binding.categoryChipGroup)
        })

        binding.categoryChipGroup.setOnCheckedChangeListener{ group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedCategoryType = chip.text.toString().lowercase(Locale.ROOT)
            categoryTypeChip = selectedCategoryType
            categoryTypeChipId = selectedChipId
        }

        binding.applyBtn.setOnClickListener {
            newsViewModel.saveCategoryType(
                categoryTypeChip,
                categoryTypeChipId
            )
            val action =
                TopHeadLineBottomSheetFragmentDirections.actionTopHeadLineBottomSheetFragmentToTopHeadLinesFragment(true)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if(chipId != 0){
            try {
                val targetView = chipGroup.findViewById<Chip>(chipId)
                targetView.isChecked = true
                chipGroup.requestChildFocus(targetView,targetView)
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            }catch (e : Exception) {
                Log.d("TopHeadLinesBottomSheet", e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}