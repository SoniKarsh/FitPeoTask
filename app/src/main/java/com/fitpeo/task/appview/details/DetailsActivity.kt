package com.fitpeo.task.appview.details

import android.os.Build
import android.os.Bundle
import com.fitpeo.task.base.BaseActivity
import com.fitpeo.task.databinding.ActivityDetailsBinding
import com.fitpeo.task.model.ResFitPeoModel
import com.fitpeo.task.utils.AppConstants

class DetailsActivity: BaseActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private var details: ResFitPeoModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseArgs()
        setData()
        setSupportActionBar(binding.toolbar)
        setBackArrow()
    }

    private fun setBackArrow(){
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setData() {
        details.run {
            binding.header.transitionName = this?.id.toString()
        }
        binding.details = details
    }

    private fun parseArgs(){
        details = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(AppConstants.DETAILS, ResFitPeoModel::class.java)
        } else {
            intent.getParcelableExtra(AppConstants.DETAILS) as ResFitPeoModel?
        }
    }

}