package com.arch.example.home.image

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.arch.example.R
import com.arch.example.base.BaseFragment
import com.arch.example.databinding.FragmentImageBinding
import com.arch.example.viewmodel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageFragment : BaseFragment<FragmentImageBinding>() {

    private val viewModel: ImageViewModel by viewModels()

    override fun getFragmentLayout(): Int {
        return R.layout.fragment_image
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getRandomImage()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel
    }
}