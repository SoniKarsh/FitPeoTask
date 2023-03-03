package com.fitpeo.task.appview.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.fitpeo.task.appview.viewmodel.MainSharedViewModel
import com.fitpeo.task.databinding.FragmentDetailsBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class DetailsFragment: Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: MainSharedViewModel by activityViewModel()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        setBackArrow()
    }

    private fun setBackArrow(){
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun parseArgs(){
        binding.details = args.details
    }

}